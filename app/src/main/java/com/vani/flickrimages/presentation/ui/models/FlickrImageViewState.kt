package com.vani.flickrimages.presentation.ui.models

import com.vani.flickrimages.domain.models.FlickrImage
import com.vani.flickrimages.domain.models.FlickrImageItem

sealed class FlickrImageViewState {
    data object Init : FlickrImageViewState()
    data object OnLoading : FlickrImageViewState()
    data class OnSuccess(val data: FlickrImage) : FlickrImageViewState()
    data class OnError( val message : String) : FlickrImageViewState()
}