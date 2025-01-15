package com.vani.flickrimages.domain.usecases

import com.vani.flickrimages.data.dto.FlickrImageResponseDto
import com.vani.flickrimages.data.dto.Item
import com.vani.flickrimages.domain.AsyncOperations
import com.vani.flickrimages.domain.FlickrImageIRepo
import com.vani.flickrimages.domain.mappers.FlickrImageMapper
import com.vani.flickrimages.domain.models.FlickrImage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

class FlickrImageUseCaseImplTest{

    private val mockRepo = mockk<FlickrImageIRepo>(relaxed = true)

    private val target = FlickrImageUseCaseImpl(mockRepo, FlickrImageMapper())
    private val flickrImageResponseDto = FlickrImageResponseDto(
        items = listOf(Item(
            author = "nobody@flickr.com (\"Gary K. Mann\")",
            title = "ZACK THE DOBERMANN",
            published = "2025-01-13T21:54:53Z",
            description = "<p><a href=\\\"https:\\/\\/www.flickr.com\\/people\\/51395952@N08\\/\\\">Gary K. Mann<\\/a> posted a photo:<\\/p> <p><a href=\\\"https:\\/\\/www.flickr.com\\/photos\\/51395952@N08\\/54264728943\\/\\\" title=\\\"ZACK THE DOBERMANN\\\"><img src=\\\"https:\\/\\/live.staticflickr.com\\/65535\\/54264728943_20809cdd00_m.jpg\\\" width=\\\"240\\\" height=\\\"192\\\" alt=\\\"ZACK THE DOBERMANN\\\" \\/><\\/a><\\/p> <p>ZACK THE DOBERMANN MET MERLIN THE BORDER TERRIER ON A WALK.<\\/p>"
        ))
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test invoke - Success`() = runTest {

        coEvery {
            mockRepo.getFlickrImages(any())
        } returns Response.success(flickrImageResponseDto)

        val result = ArrayList<AsyncOperations<FlickrImage, String>>()

        target.invoke("bird").take(2).collect {
            result.add(it)
        }

        advanceUntilIdle()

        coVerify {
            mockRepo.getFlickrImages( "bird")
        }

        assertTrue(result.size == 2)
        assertTrue(result.first() is AsyncOperations.Loading)
        assertTrue(result.last() is AsyncOperations.Success)
        assertTrue((result.last() as AsyncOperations.Success).data.items.size == 1)
        assertTrue((result.last() as AsyncOperations.Success).data.items.first().published == "9:54 PM, Jan 13, 2025")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test invoke - failure`() = runTest {

        coEvery {
            mockRepo.getFlickrImages(any())
        } returns Response.error(401, "".toResponseBody())

        val result = ArrayList<AsyncOperations<FlickrImage, String>>()

        target.invoke("bird").take(2).collect {
            result.add(it)
        }

        advanceUntilIdle()

        coVerify {
            mockRepo.getFlickrImages("bird")
        }

        assertTrue(result.size == 2)
        assertTrue(result.first() is AsyncOperations.Loading)
        assertTrue(result.last() is AsyncOperations.Error)
    }


}