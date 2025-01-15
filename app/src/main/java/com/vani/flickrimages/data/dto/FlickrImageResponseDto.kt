package com.vani.flickrimages.data.dto

data class FlickrImageResponseDto(
    val description: String? = null,
    val generator: String? = null,
    val items: List<ImageItem>,
    val link: String? = null,
    val modified: String? = null,
    val title: String? = null
)