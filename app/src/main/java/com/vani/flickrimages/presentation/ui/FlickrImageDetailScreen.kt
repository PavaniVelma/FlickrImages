package com.vani.flickrimages.presentation.ui

import android.content.res.Configuration
import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.vani.flickrimages.R
import com.vani.flickrimages.domain.models.FlickrImageItem
import com.vani.flickrimages.presentation.ui.theme.LocalSpacing
import com.vani.flickrimages.presentation.ui.theme.Typography
import com.vani.flickrimages.presentation.viewmodel.FlickrImageViewModel

@Composable
fun FlickImageDetailScreen(viewModel: FlickrImageViewModel){
    val spacing = LocalSpacing.current

    viewModel.selectedFlickrImage?.let { flickrImage ->
        ImageDetails { modifier ->
            ImageDetailsCard(modifier, flickrImage)
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
fun ImageDetailsCard(modifier: Modifier, flickrImage: FlickrImageItem) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    Card(
        modifier = modifier
            .testTag(context.getString(R.string.details_card_test_tag)),
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
}

@Composable
fun ImageDetailsContent(flickrImage: FlickrImageItem) {
    val spacing = LocalSpacing.current
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
            text = "Description : ${Html.fromHtml(flickrImage.description).trim()}",
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