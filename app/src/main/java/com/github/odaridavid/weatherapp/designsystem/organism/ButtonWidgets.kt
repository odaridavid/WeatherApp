package com.github.odaridavid.weatherapp.designsystem.organism

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.MediumBody

@Composable
fun SettingOptionRadioButton(
    text: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(WeatherAppTheme.dimens.medium)
            .selectable(
                selected = (text == selectedOption),
                onClick = { onOptionSelected(text) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (text == selectedOption),
            onClick = null
        )
        MediumBody(text = text, modifier = Modifier.padding(start = WeatherAppTheme.dimens.small))
    }
}
