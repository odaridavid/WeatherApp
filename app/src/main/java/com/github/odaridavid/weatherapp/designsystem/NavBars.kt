package com.github.odaridavid.weatherapp.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.odaridavid.weatherapp.R

@Composable
fun TopBar(cityName: String, onSettingClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = cityName,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Image(
            painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.ic_settings_dark else R.drawable.ic_settings),
            contentDescription = stringResource(R.string.home_content_description_setting_icon),
            modifier = Modifier
                .defaultMinSize(40.dp)
                .clickable { onSettingClicked() }
                .padding(8.dp)
        )
    }
}
