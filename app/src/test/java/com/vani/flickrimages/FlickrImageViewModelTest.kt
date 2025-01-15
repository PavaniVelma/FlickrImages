package com.vani.flickrimages

import com.vani.flickrimages.domain.AsyncOperations
import com.vani.flickrimages.domain.models.FlickrImage
import com.vani.flickrimages.domain.models.FlickrImageItem
import com.vani.flickrimages.domain.usecases.FlickrImageUseCase
import com.vani.flickrimages.presentation.ui.models.FlickrImageViewState
import com.vani.flickrimages.presentation.viewmodel.FlickrImageViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FlickrImageViewModelTest{

    private lateinit var viewModel: FlickrImageViewModel
    private val useCase: FlickrImageUseCase = mockk(relaxed = true)
    private val dispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `test updateQuery and useCase returns success`() = runTest{
        viewModel = FlickrImageViewModel(useCase)
        val flow = flow<AsyncOperations<FlickrImage, String>>{
            emit(AsyncOperations.Success(FlickrImage(listOf(FlickrImageItem(title = "Test", description = "", author = "", mediaLink = "", tags = "", published = "")))))
        }
        coEvery { useCase.invoke( any()) } returns flow
        viewModel.updateQuery("apple")
        advanceUntilIdle()
        assertEquals(viewModel.query.value, "apple")
        coVerify { useCase.invoke("apple") }
        val result = viewModel.viewState.take(1).toList()
        assertTrue(result.first() is FlickrImageViewState.OnSuccess)
        assertTrue((result.first() as FlickrImageViewState.OnSuccess).data.items.size ==1)
        assertTrue((result.first() as FlickrImageViewState.OnSuccess).data.items.first().title =="Test")
    }

    @Test
    fun `test updateQuery and useCase returns failure`() =runTest{
        viewModel = FlickrImageViewModel(useCase)

        val flow = flow<AsyncOperations<FlickrImage, String>>{
            emit(AsyncOperations.Error(""))
        }
        coEvery { useCase.invoke(any()) } returns flow
        viewModel.updateQuery("apple")
        advanceUntilIdle()
        assertEquals(viewModel.query.value, "apple")
        coVerify { useCase.invoke("apple") }
        val result = viewModel.viewState.take(1).toList()
        assertTrue(result.first() is FlickrImageViewState.OnError)
    }


}