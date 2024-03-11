package com.github.odaridavid.weatherapp.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.github.odaridavid.weatherapp.designsystem.atom.DarkColorPalette
import com.github.odaridavid.weatherapp.designsystem.atom.Dimensions
import com.github.odaridavid.weatherapp.designsystem.atom.LightColorPalette
import com.github.odaridavid.weatherapp.designsystem.atom.LocalDimens
import com.github.odaridavid.weatherapp.designsystem.atom.LocalWeight
import com.github.odaridavid.weatherapp.designsystem.atom.Weight
import com.github.odaridavid.weatherapp.designsystem.atom.shapes
import com.github.odaridavid.weatherapp.designsystem.atom.typography

// TODO Debug menu or app to preview design system components
@Composable
fun WeatherAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

object WeatherAppTheme {
    val dimens: Dimensions
        @Composable
        get() = LocalDimens.current

    val weight: Weight
        @Composable
        get() = LocalWeight.current
}
