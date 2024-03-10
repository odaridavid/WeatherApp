package com.github.odaridavid.weatherapp.designsystem.atom

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

object Dimensions {
    val none = 0.dp
    val extraSmall = 4.dp
    val small = 8.dp
    val medium = 16.dp
    val large = 24.dp
    val extraLarge = 32.dp
}

object Weight{
  val none = 0f
  val half = 0.5f
  val full = 1f
}

val LocalDimens = staticCompositionLocalOf { Dimensions }
val LocalWeight = staticCompositionLocalOf { Weight }