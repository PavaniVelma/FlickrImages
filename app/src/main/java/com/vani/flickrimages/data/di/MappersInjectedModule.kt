package com.vani.flickrimages.data.di

import com.vani.flickrimages.data.dto.FlickrImageResponseDto
import com.vani.flickrimages.domain.mappers.FlickrImageMapper
import com.vani.flickrimages.domain.mappers.Mapper
import com.vani.flickrimages.domain.models.FlickrImage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class MappersInjectedModule {


    @Binds
    abstract fun bindsFlickrImageMapper(
        mapper : FlickrImageMapper
    ): Mapper<FlickrImageResponseDto, FlickrImage>
}