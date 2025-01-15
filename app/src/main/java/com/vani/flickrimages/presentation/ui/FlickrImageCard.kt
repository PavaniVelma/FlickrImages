package com.vani.flickrimages.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.vani.flickrimages.domain.models.FlickrImageItem
import com.vani.flickrimages.presentation.ui.theme.LocalSpacing

@Composable
fun FlickrImageCard(modifier: Modifier, flickrImage: FlickrImageItem, content: @Composable BoxScope.() -> Unit = {}){
    val spacing = LocalSpacing.current

    Card(modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = spacing.medium),
        shape = RoundedCornerShape(spacing.medium)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(model = flickrImage.mediaLink),
                contentDescription = flickrImage.title,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.xSmall)
                    .clip(RoundedCornerShape(spacing.medium)),
                contentScale = ContentScale.Crop
            )
            content()
        }
    }

}