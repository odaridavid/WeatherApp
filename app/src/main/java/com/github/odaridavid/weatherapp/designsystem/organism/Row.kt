package com.github.odaridavid.weatherapp.designsystem.organism

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme

@Composable
fun SettingOptionRow(
    optionLabel: String,
    optionValue: String? = null,
    @DrawableRes optionIcon: Int,
    optionIconContentDescription: String,
    modifier: Modifier = Modifier,
    onOptionClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onOptionClicked() }
            .padding(WeatherAppTheme.dimens.medium)
    ) {
        Image(
            painter = painterResource(id = optionIcon),
            contentDescription = optionIconContentDescription,
            modifier = Modifier.padding(WeatherAppTheme.dimens.small)
        )
        Text(
            text = optionLabel,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(WeatherAppTheme.dimens.small)
        )
        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.full))

        optionValue?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(WeatherAppTheme.dimens.small)
            )
        }
    }
}
