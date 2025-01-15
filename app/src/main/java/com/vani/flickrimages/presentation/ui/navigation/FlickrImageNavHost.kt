package com.vani.flickrimages.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vani.flickrimages.presentation.ui.FlickImageDetailScreen
import com.vani.flickrimages.presentation.ui.FlickrImageScreen
import com.vani.flickrimages.presentation.viewmodel.FlickrImageViewModel


@Composable
fun FlickrImageNavHost(){

    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = FlickrImageRoutes.FlickrImageGrid.route) {
        
        composable(FlickrImageRoutes.FlickrImageGrid.route){
            val currentBackStack = remember {
                navController.currentBackStackEntry
            }
            currentBackStack?.let {
                val viewModel: FlickrImageViewModel = hiltViewModel(viewModelStoreOwner = it)
                FlickrImageScreen(navController = navController, viewModel)
            }
        }

        composable(FlickrImageRoutes.FlickrImageDetails.route){
            val flickerImagesScreen = remember {
                navController.getBackStackEntry(FlickrImageRoutes.FlickrImageGrid.route)
            }
            flickerImagesScreen.let {
                val viewModel: FlickrImageViewModel = hiltViewModel(viewModelStoreOwner = flickerImagesScreen)
                FlickImageDetailScreen(viewModel)
            }

        }
        
    }
}

enum class FlickrImageRoutes(val route:String){
    FlickrImageGrid("FlickrImageScreenRoute"),
    FlickrImageDetails("FlickrImageDetailScreen")
}
