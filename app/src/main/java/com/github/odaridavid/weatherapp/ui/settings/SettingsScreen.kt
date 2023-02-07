package com.github.odaridavid.weatherapp.ui.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.odaridavid.weatherapp.R

@Composable
fun SettingsScreen(state: SettingsScreenViewState, onBackButtonClicked: () -> Unit) {
    Column {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Setting Icon",
                modifier = Modifier
                    .defaultMinSize(40.dp)
                    .clickable { onBackButtonClicked() }
                    .padding(8.dp)
            )

            Text(
                text = "Settings",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        SettingOptionRow(
            optionLabel = "Language",
            optionValue = state.selectedLanguage,
            optionIcon = R.drawable.ic_language,
            optionIconContentDescription = "Language Icon"
        ) {
            // TODO Open language selection dialog and change value
        }

        SettingOptionRow(
            optionLabel = "Units",
            optionValue = state.units,
            optionIcon = R.drawable.ic_units,
            optionIconContentDescription = "Units Icon"
        ) {
            // TODO Open unit selection dialog and change value
        }

        Spacer(modifier = Modifier.weight(1.0f))

        Text(
            text = state.versionInfo,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SettingOptionRow(
    optionLabel: String,
    optionValue: String,
    @DrawableRes optionIcon: Int,
    optionIconContentDescription: String,
    modifier: Modifier = Modifier,
    onOptionClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onOptionClicked() }
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = optionIcon),
            contentDescription = optionIconContentDescription,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = optionLabel,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = optionValue,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp)
        )
    }
}
