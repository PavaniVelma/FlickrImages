package com.vani.flickrimages.presentation.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.vani.flickrimages.R
import com.vani.flickrimages.domain.models.FlickrImageItem
import com.vani.flickrimages.presentation.ui.models.FlickrImageViewState
import com.vani.flickrimages.presentation.ui.navigation.FlickrImageRoutes
import com.vani.flickrimages.presentation.ui.theme.LocalSpacing
import com.vani.flickrimages.presentation.ui.theme.Typography
import com.vani.flickrimages.presentation.viewmodel.FlickrImageViewModel

@Composable
fun FlickrImageScreen(navController: NavController,viewModel: FlickrImageViewModel){
    FlickerImagesSearch(viewModel, navController)
}

@Composable
fun FlickrImages(flickrImage: FlickrImageItem, onClick:(FlickrImageItem) ->Unit){
    val spacing = LocalSpacing.current

    Card(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .clickable {
            onClick.invoke(flickrImage)
        },
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
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                color = Color.Black.copy(alpha = 0.2f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start =spacing.medium, top = spacing.small, bottom = spacing.small)
                        .fillMaxWidth(),
                    text = flickrImage.title,
                    color = Color.White,
                    fontWeight = Typography.bodyLarge.fontWeight,
                    maxLines = 1,
                    textAlign = TextAlign.Left
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickerImagesSearch(viewModel: FlickrImageViewModel, navController: NavController) {
    val query = viewModel.query.collectAsState()
    val context = LocalContext.current
    val result = viewModel.viewState.collectAsState(FlickrImageViewState.Init)
    val spacing = LocalSpacing.current
    val focusManager = LocalFocusManager.current

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium),
        query = query.value,
        onQueryChange = { viewModel.updateQuery(it) },
        onSearch ={ viewModel.updateQuery(it) },
        active = result.value !is FlickrImageViewState.Init,
        onActiveChange = {},
        placeholder = { Text(text = context.getString(R.string.search_hint))}
    ) {
        val keyboard = LocalSoftwareKeyboardController.current

        when(result.value){

            is FlickrImageViewState.OnLoading -> {
                keyboard?.hide()
                focusManager.clearFocus()
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(spacing.large)
                    )
                }
            }

            is FlickrImageViewState.OnSuccess -> {
                Log.i("OnSuccess", "${(result.value as FlickrImageViewState.OnSuccess).data.items.size}")

                Spacer(modifier = Modifier.padding(top = spacing.xLarge))
                val items = (result.value as? FlickrImageViewState.OnSuccess)?.data?.items
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = PaddingValues(spacing.medium),
                    verticalItemSpacing = spacing.medium,
                    horizontalArrangement = Arrangement.spacedBy(spacing.medium)
                ) {
                    items?.let { item ->
                        items(item){
                            FlickrImages(it){ selectedItem ->
                                viewModel.selectedFlickrImage = selectedItem
                                navController.navigate(FlickrImageRoutes.FlickrImageDetails.route)
                            }
                        }
                    }
                }

            }

            is FlickrImageViewState.OnError -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = context.getString(R.string.error_message),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = Typography.titleLarge.fontSize
                    )
                }
            }

            FlickrImageViewState.Init -> {

            }
        }
    }
}