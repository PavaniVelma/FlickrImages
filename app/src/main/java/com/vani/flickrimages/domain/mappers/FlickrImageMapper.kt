package com.vani.flickrimages.domain.mappers

import com.vani.flickrimages.data.dto.FlickrImageResponseDto
import com.vani.flickrimages.domain.models.FlickrImage
import com.vani.flickrimages.domain.models.FlickrImageItem
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class FlickrImageMapper @Inject constructor():
    Mapper<FlickrImageResponseDto, FlickrImage> {

        override fun toModel(dto: FlickrImageResponseDto): FlickrImage {

            val imageItems = dto.items.map {
                FlickrImageItem(
                    author = it.author?:"",
                    description = it.description?: "",
                    mediaLink = it.media?.m?:"" ,
                    published = formatDate(it.published) ?: "",
                    tags = it.tags ?:"",
                    title = it.title ?: ""
                )
            }

            return FlickrImage(
                items = imageItems
            )
    }

    override fun fromModel(model: FlickrImage): FlickrImageResponseDto {
        TODO("Not yet implemented")
    }

    private fun formatDate(date: String?): String? {
        return date?.let { input ->
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("EST")
            val outputFormat = SimpleDateFormat("h:mm a, MMM dd, yyyy", Locale.getDefault())

            inputFormat.parse(input)?.let { outputFormat.format(it) }
        }
    }
}