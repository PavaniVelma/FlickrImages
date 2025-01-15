package com.vani.flickrimages.presentation.ui

import android.content.res.Configuration
import android.text.Html
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vani.flickrimages.domain.models.FlickrImageItem
import com.vani.flickrimages.presentation.ui.theme.LocalSpacing
import com.vani.flickrimages.presentation.ui.theme.Typography
import com.vani.flickrimages.presentation.viewmodel.FlickrImageViewModel

@Composable
fun FlickImageDetailScreen(viewModel: FlickrImageViewModel){
    val spacing = LocalSpacing.current

    viewModel.selectedFlickrImage?.let { flickrImage ->
        ImageDetails { modifier ->
            FlickrImageCard(modifier, flickrImage)
            Spacer(modifier = Modifier.padding(top = spacing.medium))
            ImageDetailsContent(flickrImage)
        }
    }
}

@Composable
fun ImageDetails(content: @Composable (modifier: Modifier) -> Unit){
    val spacing = LocalSpacing.current
    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier = Modifier
                .padding(spacing.medium)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start
        ) {

            content(Modifier.fillMaxWidth().aspectRatio(1f))
        }
    } else {
        Row(
            modifier = Modifier
            .padding(spacing.medium)
        ) {
            content(Modifier.size(500.dp))
        }
    }
}


@Composable
fun ImageDetailsContent(flickrImage: FlickrImageItem) {
    val spacing = LocalSpacing.current

    // Parse Html to text
    val description = Html.fromHtml(flickrImage.description, 0).trim()
    Column {
        Text(
            text = "Title : ${flickrImage.title}",
            color = Color.Black,
            fontSize = Typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.padding(spacing.small)
        )

        Text(
            text = "Description : $description",
            color = Color.Black,
            fontSize = Typography.bodyLarge.fontSize,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(spacing.small)
        )

        Text(
            text = "Author : ${flickrImage.author}",
            color = Color.Black,
            fontSize = Typography.bodyLarge.fontSize,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(spacing.small)
        )

        Text(
            text = "Date : ${flickrImage.published}",
            color = Color.Black,
            fontSize = Typography.bodyLarge.fontSize,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.padding(spacing.small)
        )
    }
}