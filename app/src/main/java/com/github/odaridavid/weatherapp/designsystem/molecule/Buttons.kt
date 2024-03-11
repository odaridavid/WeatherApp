package com.github.odaridavid.weatherapp.designsystem.molecule

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme

@Composable
fun ConfirmButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors()
    ) {
        Text(text = stringResource(R.string.settings_confirm))
    }
}

@Composable
fun SettingOptionRadioButton(
    text: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        Modifier
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
        Body(text = text, modifier = Modifier.padding(start = WeatherAppTheme.dimens.small))
    }
}
