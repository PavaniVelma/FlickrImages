package com.vani.flickrimages.data.di

import com.vani.flickrimages.data.FlickrImageRepoImpl
import com.vani.flickrimages.domain.FlickrImageIRepo
import com.vani.flickrimages.domain.usecases.FlickrImageUseCase
import com.vani.flickrimages.domain.usecases.FlickrImageUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoriesModuleInjector {

    @Binds
    abstract fun bindFlickrImageRepository(
        repoImpl: FlickrImageRepoImpl
    ): FlickrImageIRepo

    @Binds
    abstract fun bindFlickrImageUseCase(
        useCaseImpl: FlickrImageUseCaseImpl
    ): FlickrImageUseCase
}