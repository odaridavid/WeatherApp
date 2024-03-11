package com.github.odaridavid.weatherapp.designsystem.organism

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.LargeBody
import com.github.odaridavid.weatherapp.designsystem.molecule.LargeLabel

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

        LargeLabel(
            text = optionLabel,
            modifier = Modifier.padding(WeatherAppTheme.dimens.small),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.FULL))

        optionValue?.let {
            LargeBody(
                text = it,
                modifier = Modifier.padding(WeatherAppTheme.dimens.extraSmall)
            )
        }
    }
}
