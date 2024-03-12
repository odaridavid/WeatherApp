package com.github.odaridavid.weatherapp.designsystem.templates

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.MediumBody

@Composable
fun InfoScreen(@StringRes message: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WeatherAppTheme.dimens.medium)
    ) {
        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.HALF))
        MediumBody(
            text = stringResource(message),
            modifier = Modifier
                .padding(WeatherAppTheme.dimens.medium)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.HALF))
    }
}

@Composable
fun RequiresPermissionsScreen() {
    InfoScreen(message = R.string.location_no_permission_screen_description)
}

@Composable
fun EnableLocationSettingScreen() {
    InfoScreen(message = R.string.location_settings_not_enabled)
}
