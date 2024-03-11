package com.github.odaridavid.weatherapp.designsystem.organism

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.IconWithAction
import com.github.odaridavid.weatherapp.designsystem.molecule.MenuHeadline

private val ICON_SIZE = 40.dp

@Composable
fun HomeTopBar(cityName: String, onSettingClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(WeatherAppTheme.dimens.medium)
            .fillMaxWidth()
    ) {
        Text(
            text = cityName,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.FULL))
        Image(
            painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.ic_settings_dark else R.drawable.ic_settings),
            contentDescription = stringResource(R.string.home_content_description_setting_icon),
            modifier = Modifier
                .defaultMinSize(ICON_SIZE)
                .clickable { onSettingClicked() }
                .padding(WeatherAppTheme.dimens.small)
        )
    }
}

@Composable
fun TopNavigationBar(onBackButtonClicked: () -> Unit, title: String) {
    Row(modifier = Modifier.padding(WeatherAppTheme.dimens.medium)) {
        IconWithAction(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = stringResource(R.string.back_button_content_description_icon),
            modifier = Modifier.padding(WeatherAppTheme.dimens.small),
            onClicked = { onBackButtonClicked() }
        )

        MenuHeadline(
            text = title,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

