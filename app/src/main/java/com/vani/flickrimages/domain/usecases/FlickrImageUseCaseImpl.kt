package com.vani.flickrimages.domain.usecases

import com.vani.flickrimages.domain.AsyncOperations
import com.vani.flickrimages.domain.FlickrImageIRepo
import com.vani.flickrimages.domain.mappers.FlickrImageMapper
import com.vani.flickrimages.domain.models.FlickrImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FlickrImageUseCaseImpl @Inject constructor(
    private val repo:FlickrImageIRepo,
    private val flickrImageMapper: FlickrImageMapper
): FlickrImageUseCase {

    override suspend fun invoke(
        tags: String
    ): Flow<AsyncOperations<FlickrImage, String>> {
        return flow {
            emit(AsyncOperations.Loading)
            val result =  try {
                val response = repo.getFlickrImages(tags)
               if(response.isSuccessful){
                    val flickrImageResponse =  response.body()
                   flickrImageResponse?.let {
                       AsyncOperations.Success(flickrImageMapper.toModel(flickrImageResponse))
                    } ?: AsyncOperations.Error("Something went wrong")

                } else {
                    AsyncOperations.Error("Something went wrong")
                }
                }catch (e:Exception){
                    AsyncOperations.Error(e.message.toString())
                }

            emit(result)
        }
    }
}