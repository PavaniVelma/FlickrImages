package com.vani.flickrimages.presentation.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vani.flickrimages.presentation.viewmodel.FlickrImageViewModel
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FlickrImageScreenKtTest{

    val viewModel = mockk<FlickrImageViewModel>(relaxed = true)
    val navController = mockk<NavController>(relaxed = true)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test

}