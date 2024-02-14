package com.github.odaridavid.weatherapp.designsystem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.odaridavid.weatherapp.R

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
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary
        )
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
            .padding(16.dp)
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
        Body(text = text, modifier = Modifier.padding(start = 8.dp))
    }
}
