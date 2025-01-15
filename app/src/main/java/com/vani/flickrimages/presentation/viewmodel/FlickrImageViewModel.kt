package com.vani.flickrimages.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vani.flickrimages.domain.AsyncOperations
import com.vani.flickrimages.domain.models.FlickrImage
import com.vani.flickrimages.domain.models.FlickrImageItem
import com.vani.flickrimages.domain.usecases.FlickrImageUseCase
import com.vani.flickrimages.presentation.ui.models.FlickrImageViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlickrImageViewModel @Inject constructor(private val flickrImageUseCase: FlickrImageUseCase): ViewModel() {

    private val _viewState = MutableStateFlow<FlickrImageViewState>(FlickrImageViewState.Init)
    val viewState : Flow<FlickrImageViewState> = _viewState.asStateFlow()
    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query

    init {
        viewModelScope.launch {
            _query
                .debounce(1000)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotBlank() && query.isNotEmpty()) {
                        getFlickrImages(query.trim())
                    }
                }
        }
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

    var selectedFlickrImage : FlickrImageItem? = null

    private suspend fun getFlickrImages(tag: String){
        flickrImageUseCase.invoke(
            tags = tag
        ).collectLatest {
            when(it) {
                is AsyncOperations.Loading ->   { _viewState.value = FlickrImageViewState.OnLoading}
                is AsyncOperations.Success<FlickrImage> -> _viewState.value = FlickrImageViewState.OnSuccess(it.data)
                is AsyncOperations.Error<String> -> _viewState.value = FlickrImageViewState.OnError(it.message)
            }
        }
    }
}