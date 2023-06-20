package com.github.odaridavid.weatherapp.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WeatherIcon(iconUrl: String, contentDescription: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = iconUrl,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}

@Composable
fun IconWithAction(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier,
    onClicked: () -> Unit
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .defaultMinSize(40.dp)
            .clickable { onClicked() }
    )
}
