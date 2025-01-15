package com.vani.flickrimages.domain

import com.vani.flickrimages.data.dto.FlickrImageResponseDto
import retrofit2.Response

interface FlickrImageIRepo {

    suspend fun getFlickrImages(tags: String):Response<FlickrImageResponseDto>
}