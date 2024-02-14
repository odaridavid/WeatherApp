package com.github.odaridavid.weatherapp.designsystem

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.core.model.Temperature

@Composable
fun ErrorTextWithAction(
    @StringRes errorMessageId: Int, modifier: Modifier, onTryAgainClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = errorMessageId),
            textAlign = TextAlign.Center,
            modifier = modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.body1
        )
        Button(
            onClick = { onTryAgainClicked() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.home_error_try_again),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Headline(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.h2
    )
}

@Composable
fun TemperatureHeadline(temperature: String, modifier: Modifier = Modifier) {
    Headline(
        text = temperature,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp)
    )
}

@Composable
fun Subtitle(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle1,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        color = color
    )
}

@Composable
fun Subtitle2(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle2,
        modifier = modifier,
        color = color,
        textAlign = textAlign
    )
}

@Composable
fun Body(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(
        text = text,
        style = MaterialTheme.typography.body2,
        color = color,
        modifier = modifier
    )
}

@Composable
fun Temperature(text: String) {
    Body(
        text = text,
        modifier = Modifier.padding(4.dp)
    )
}

@Composable
fun ForecastedTime(text: String, modifier: Modifier = Modifier) {
    Body(
        text = text,
        modifier = modifier.padding(4.dp)
    )
}

@Composable
fun MenuHeadline(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.h5,
        textAlign = TextAlign.Center,
        color = color
    )
}

@Composable
fun VersionInfoText(versionInfo: String, modifier: Modifier) {
    Subtitle2(
        text = versionInfo,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}
