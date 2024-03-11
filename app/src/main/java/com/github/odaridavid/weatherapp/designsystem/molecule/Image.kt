package com.github.odaridavid.weatherapp.designsystem.molecule

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

// todo check with 48
private val ICON_SIZE = 40.dp

@Composable
fun RemoteImage(
    url: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}

@Composable
fun ActionIcon(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .defaultMinSize(ICON_SIZE)
            .clickable { onClicked() }
    )
}
