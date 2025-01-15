package com.vani.flickrimages.presentation.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalSpacing = compositionLocalOf { Spacing() }

data class Spacing(
    val xSmall: Dp = 2.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 12.dp,
    val xLarge: Dp = 16.dp,
    val xxLarge: Dp = 24.dp,
)