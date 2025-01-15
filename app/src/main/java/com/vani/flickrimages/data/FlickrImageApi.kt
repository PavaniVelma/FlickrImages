package com.vani.flickrimages.data

import com.vani.flickrimages.data.dto.FlickrImageResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrImageApi {
    @GET("/services/feeds/photos_public.gne")
    suspend fun getImages(
        @Query("format") format : String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("tags") tags: String
    ):Response<FlickrImageResponseDto>
}