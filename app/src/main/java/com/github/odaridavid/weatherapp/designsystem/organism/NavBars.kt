package com.github.odaridavid.weatherapp.designsystem.organism

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.ActionIcon
import com.github.odaridavid.weatherapp.designsystem.molecule.MediumHeadline

// TODO Create a navbar factory when more screens are added
@Composable
fun HomeTopBar(cityName: String, onSettingClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(WeatherAppTheme.dimens.medium)
            .fillMaxWidth()
    ) {
        MediumHeadline(text = cityName)
        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.FULL))
        ActionIcon(
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = stringResource(R.string.home_content_description_setting_icon),
            modifier = Modifier.padding(WeatherAppTheme.dimens.small),
            onClicked = { onSettingClicked() }
        )
    }
}

@Composable
fun TopNavigationBar(onBackButtonClicked: () -> Unit, title: String) {
    Row(modifier = Modifier.padding(WeatherAppTheme.dimens.medium)) {
        ActionIcon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = stringResource(R.string.back_button_content_description_icon),
            modifier = Modifier.padding(WeatherAppTheme.dimens.small),
            onClicked = { onBackButtonClicked() }
        )

        MediumHeadline(
            text = title,
            modifier = Modifier.align(Alignment.CenterVertically),
            textAlign = TextAlign.Center,
        )
    }
}
