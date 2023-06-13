package com.github.odaridavid.weatherapp.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun WeatherIcon(iconUrl: String, contentDescription: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = iconUrl,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}
