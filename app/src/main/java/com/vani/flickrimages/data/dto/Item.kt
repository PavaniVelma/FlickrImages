package com.vani.flickrimages.data.dto

import com.google.gson.annotations.SerializedName

data class Item(
    val author: String? = null,
    @SerializedName("author_id")
    val authorId: String? = null,
    @SerializedName("date_taken")
    val dateTaken: String? = null,
    val description: String? = null,
    val link: String? = null,
    val media: Media? = null,
    val published: String? = null,
    val tags: String? = null,
    val title: String? = null
)
