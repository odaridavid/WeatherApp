package com.github.odaridavid.weatherapp.designsystem.organism

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.LargeDisplay
import com.github.odaridavid.weatherapp.designsystem.molecule.MediumBody
import com.github.odaridavid.weatherapp.designsystem.molecule.MediumLabel
import com.github.odaridavid.weatherapp.designsystem.molecule.PositiveButton

// TODO These components still feel meh. They could be better or maybe they are too specfic
@Composable
fun Temperature(text: String) {
    MediumBody(
        text = text,
        modifier = Modifier.padding(WeatherAppTheme.dimens.extraSmall)
    )
}

@Composable
fun ForecastedTime(text: String, modifier: Modifier = Modifier) {
    MediumBody(
        text = text,
        modifier = modifier.padding(WeatherAppTheme.dimens.extraSmall)
    )
}

@Composable
fun VersionInfoText(versionInfo: String, modifier: Modifier) {
    MediumLabel(
        text = versionInfo,
        modifier = modifier
            .fillMaxWidth()
            .padding(WeatherAppTheme.dimens.medium),
        textAlign = TextAlign.Center
    )
}

@Composable
fun TemperatureHeadline(temperature: String, modifier: Modifier = Modifier) {
    LargeDisplay(
        text = temperature,
        modifier = modifier
            .padding(horizontal = WeatherAppTheme.dimens.medium)
            .padding(vertical = WeatherAppTheme.dimens.small)
    )
}

@Composable
fun ActionErrorMessage(
    @StringRes errorMessageId: Int,
    modifier: Modifier,
    onTryAgainClicked: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        MediumBody(
            text = stringResource(id = errorMessageId),
            textAlign = TextAlign.Center,
            modifier = modifier.align(Alignment.CenterHorizontally),
        )
        PositiveButton(
            text = stringResource(id = R.string.home_error_try_again),
            onClick = { onTryAgainClicked() },
            modifier = Modifier
                .padding(WeatherAppTheme.dimens.medium)
                .align(Alignment.CenterHorizontally)
        )
    }
}
