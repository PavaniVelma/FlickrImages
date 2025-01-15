package com.vani.flickrimages.domain.models

data class FlickrImageItem(
    val author: String,
    val description: String,
    val mediaLink: String,
    val published: String,
    val tags: String,
    val title: String
)