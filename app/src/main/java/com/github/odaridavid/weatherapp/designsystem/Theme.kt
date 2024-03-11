package com.github.odaridavid.weatherapp.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.github.odaridavid.weatherapp.designsystem.atom.Dimensions
import com.github.odaridavid.weatherapp.designsystem.atom.LocalDimens
import com.github.odaridavid.weatherapp.designsystem.atom.LocalWeight
import com.github.odaridavid.weatherapp.designsystem.atom.Weight
import com.github.odaridavid.weatherapp.designsystem.atom.pink200
import com.github.odaridavid.weatherapp.designsystem.atom.pink500
import com.github.odaridavid.weatherapp.designsystem.atom.pinkDarkPrimary
import com.github.odaridavid.weatherapp.designsystem.atom.shapes
import com.github.odaridavid.weatherapp.designsystem.atom.typography

// TODO Debug menu or app to preview design system components
private val LightColorPalette = lightColorScheme(
    primary = pink500,
    secondary = pink500,
    onPrimary = Color.Black,
    onSecondary = Color.Black
)

private val DarkColorPalette = darkColorScheme(
    primary = pink200,
    secondary = pink200,
    surface = pinkDarkPrimary
)

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
