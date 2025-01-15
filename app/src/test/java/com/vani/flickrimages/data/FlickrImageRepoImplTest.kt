package com.vani.flickrimages.data

import com.vani.flickrimages.data.dto.FlickrImageResponseDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

import org.junit.Test
import retrofit2.Response

class FlickrImageRepoImplTest {
    private val mockApi = mockk<FlickrImageApi>(relaxed = true)
    private val target = FlickrImageRepoImpl(mockApi)


    @Test
    fun getFlickrImages() = runTest {
        coEvery { mockApi.getImages(tags = any()) } returns Response.success(mockk<FlickrImageResponseDto>())
        val response = target.getFlickrImages("Birds")

        coVerify { mockApi.getImages(tags = "Birds") }
        assertTrue(response.isSuccessful)
        assertTrue(response.body() is FlickrImageResponseDto)

        coEvery { mockApi.getImages(tags = any()) } returns Response.error(404, "".toResponseBody())
        val response2 = target.getFlickrImages(tags = "Birds")
        coVerify { mockApi.getImages(tags = "Birds") }
        assertFalse(response2.isSuccessful)
    }
}