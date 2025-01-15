package com.vani.flickrimages.presentation.ui

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.vani.flickrimages.R
import com.vani.flickrimages.presentation.ui.models.FlickrImageViewState
import com.vani.flickrimages.presentation.ui.navigation.FlickrImageRoutes
import com.vani.flickrimages.presentation.ui.theme.LocalSpacing
import com.vani.flickrimages.presentation.ui.theme.Typography
import com.vani.flickrimages.presentation.viewmodel.FlickrImageViewModel

@Composable
fun FlickrImageScreen(navController: NavController,viewModel: FlickrImageViewModel){
    FlickerImagesSearch(viewModel, navController)
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
            .padding(spacing.medium)
            .testTag(context.getString(R.string.search_bar_test_tag)),
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
                        modifier = Modifier
                            .padding(spacing.large)
                            .testTag(context.getString(R.string.progress_bar_test_tag)),
                    )
                }
            }

            is FlickrImageViewState.OnSuccess -> {
                Log.i("OnSuccess", "${(result.value as FlickrImageViewState.OnSuccess).data.items.size}")

                Spacer(modifier = Modifier.padding(top = spacing.xLarge))
                val items = (result.value as? FlickrImageViewState.OnSuccess)?.data?.items
                val configuration = LocalConfiguration.current

                LazyVerticalStaggeredGrid(
                    modifier = Modifier.testTag(context.getString(R.string.grid_test_tag)),
                    columns = StaggeredGridCells.Fixed(if(configuration.orientation == ORIENTATION_LANDSCAPE) 3 else 2),
                    contentPadding = PaddingValues(spacing.medium),
                    verticalItemSpacing = spacing.medium,
                    horizontalArrangement = Arrangement.spacedBy(spacing.medium)
                ) {
                    items?.let { item ->
                        items(item){
                            val testTag = String.format(context.getString(R.string.image_item_test_tag), items.indexOf(it).toString())
                            FlickrImageCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clickable {
                                        viewModel.selectedFlickrImage = it
                                        navController.navigate(FlickrImageRoutes.FlickrImageDetails.route)
                                    }
                                    .testTag(testTag),
                                flickrImage = it
                            ){
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
                                        text = it.title,
                                        color = Color.White,
                                        fontWeight = Typography.bodyLarge.fontWeight,
                                        maxLines = 1,
                                        textAlign = TextAlign.Left
                                    )
                                }
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