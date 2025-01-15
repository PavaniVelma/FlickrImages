package com.vani.flickrimages.domain.usecases

import com.vani.flickrimages.domain.AsyncOperations
import com.vani.flickrimages.domain.models.FlickrImage
import kotlinx.coroutines.flow.Flow

interface FlickrImageUseCase {

    suspend fun invoke(tags:String): Flow<AsyncOperations<FlickrImage, String>>
}