package com.vani.flickrimages.data

import com.vani.flickrimages.data.dto.FlickrImageResponseDto
import com.vani.flickrimages.domain.FlickrImageIRepo
import retrofit2.Response
import javax.inject.Inject

class FlickrImageRepoImpl @Inject constructor(private val api: FlickrImageApi): FlickrImageIRepo {

    override suspend fun getFlickrImages(
        tags: String
    ): Response<FlickrImageResponseDto> {

        return  api.getImages(tags =tags)
    }
}