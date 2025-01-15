package com.vani.flickrimages.presentation.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vani.flickrimages.presentation.viewmodel.FlickrImageViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FlickrImageScreenKtTest{

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testOnInputSuccessBehavior() {
        hiltRule.inject()

        with(composeTestRule) {
            onNodeWithTag("search_bar_test_tag").assertIsDisplayed()
            onNodeWithTag("search_bar_test_tag").onChild().performTextInput("Apple")
            waitUntil {
                onAllNodesWithTag("progress_bar_test_tag").fetchSemanticsNodes().isNotEmpty()
            }

            onNodeWithTag("progress_bar_test_tag").assertIsDisplayed()

            waitUntil {
                onAllNodesWithTag("grid_test_tag").fetchSemanticsNodes().isNotEmpty()
            }

            onNodeWithTag("grid_test_tag").assertExists()
        }

    }


}