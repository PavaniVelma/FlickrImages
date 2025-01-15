package com.vani.flickrimages.presentation.ui

import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.rememberAsyncImagePainter
import com.vani.flickrimages.domain.models.FlickrImageItem
import com.vani.flickrimages.presentation.ui.theme.LocalSpacing
import com.vani.flickrimages.presentation.ui.theme.Typography
import com.vani.flickrimages.presentation.viewmodel.FlickrImageViewModel

@Composable
fun FlickImageDetailScreen(viewModel: FlickrImageViewModel){

    viewModel.selectedFlickrImage?.let {
        ImageDetails(it)
    }
}

@Composable
fun ImageDetails(flickrImage: FlickrImageItem){
    val description = HtmlCompat.fromHtml(flickrImage.description, 0)
    val spacing = LocalSpacing.current

    Column(modifier = Modifier.padding(spacing.medium),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.Start) {
        Card(
            modifier = Modifier.size(500.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = spacing.medium),
            shape = RoundedCornerShape(spacing.medium)
        ) {
            Image(
                painter = rememberAsyncImagePainter(flickrImage.mediaLink),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.xSmall)
                    .clip(RoundedCornerShape(spacing.medium)),
                contentScale = ContentScale.FillBounds
            )
        }

        Spacer(modifier = Modifier.padding(top = spacing.medium))

        Text(text = "Title : ${flickrImage.title}",
            color = Color.Black,
            fontSize = Typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.padding(spacing.small))

        Text(text = "Description : ${Html.fromHtml(flickrImage.description).trim()}",
            color = Color.Black,
            fontSize = Typography.bodyLarge.fontSize,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(spacing.small))

        Text(text = "Author : ${flickrImage.author}",
            color = Color.Black,
            fontSize = Typography.bodyLarge.fontSize,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(spacing.small))

        Text(text = "Date : ${flickrImage.published}",
            color = Color.Black,
            fontSize = Typography.bodyLarge.fontSize,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.padding(spacing.small)
            )
    }
}