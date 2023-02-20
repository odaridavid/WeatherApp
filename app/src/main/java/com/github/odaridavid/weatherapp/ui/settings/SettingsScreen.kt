package com.github.odaridavid.weatherapp.ui.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.core.model.TimeFormat

// TODO Improve dialog ui/solution
@Composable
fun SettingsScreen(
    state: SettingsScreenViewState,
    onBackButtonClicked: () -> Unit,
    onLanguageChanged: (String) -> Unit,
    onUnitChanged: (String) -> Unit,
    onTimeFormatChanged: (TimeFormat) -> Unit,
) {
    Column {

        Row(modifier = Modifier.padding(16.dp)) {
            Image(painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = stringResource(R.string.settings_content_description_icon),
                modifier = Modifier
                    .defaultMinSize(40.dp)
                    .clickable { onBackButtonClicked() }
                    .padding(8.dp))

            Text(
                text = stringResource(R.string.settings_screen_title),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        val openLanguageSelectionDialog = remember { mutableStateOf(false) }
        SettingOptionRow(
            optionLabel = stringResource(R.string.settings_language_label),
            optionValue = state.selectedLanguage,
            optionIcon = R.drawable.ic_language,
            optionIconContentDescription = stringResource(R.string.settings_content_description_lang_icon)
        ) {
            openLanguageSelectionDialog.value = openLanguageSelectionDialog.value.not()
        }

        val openUnitSelectionDialog = remember { mutableStateOf(false) }
        SettingOptionRow(
            optionLabel = stringResource(R.string.settings_unit_label),
            optionValue = state.selectedUnit,
            optionIcon = R.drawable.ic_units,
            optionIconContentDescription = stringResource(R.string.settings_content_description_unit_icon)
        ) {
            openUnitSelectionDialog.value = openUnitSelectionDialog.value.not()
        }

        val openTimeFormatSelectionDialog = remember { mutableStateOf(false) }
        SettingOptionRow(
            optionLabel = stringResource(R.string.settings_time_format),
            optionValue = state.selectedTimeFormat.displayName,
            optionIcon = R.drawable.baseline_access_time_24,
            optionIconContentDescription = stringResource(R.string.settings_content_description_time_icon)
        ) {
            openTimeFormatSelectionDialog.value = openTimeFormatSelectionDialog.value.not()
        }

        if (openLanguageSelectionDialog.value) {
            val availableLanguages = state.availableLanguages
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(state.selectedLanguage) }
            Dialog(onDismissRequest = { openLanguageSelectionDialog.value = false }) {
                Card(modifier = Modifier.padding(16.dp)) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Column() {
                            availableLanguages.forEach { text ->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .selectable(selected = (text == selectedOption),
                                            onClick = { onOptionSelected(text) })
                                ) {
                                    RadioButton(selected = (text == selectedOption),
                                        onClick = { onOptionSelected(text) })
                                    Text(
                                        text = text,
                                        style = MaterialTheme.typography.body1.merge(),
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                        Button(modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                            onClick = { onLanguageChanged(selectedOption) }) {
                            Text(text = stringResource(R.string.settings_confirm))
                        }
                    }
                }
            }
        }

        if (openUnitSelectionDialog.value) {
            val availableUnits = state.availableUnits
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(state.selectedUnit) }

            Dialog(onDismissRequest = { openUnitSelectionDialog.value = false }) {
                Card(modifier = Modifier.padding(16.dp)) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Column() {
                            availableUnits.forEach { text ->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = (text == selectedOption),
                                            onClick = { onOptionSelected(text) }
                                        )
                                ) {
                                    RadioButton(
                                        selected = (text == selectedOption),
                                        onClick = { onOptionSelected(text) }
                                    )
                                    Text(
                                        text = text,
                                        style = MaterialTheme.typography.body1.merge(),
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                            }
                        }
                        Button(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp),
                            onClick = { onUnitChanged(selectedOption) }) {
                            Text(text = stringResource(R.string.settings_confirm))
                        }
                    }
                }

            }
        }

        if (openTimeFormatSelectionDialog.value) {
            val availableFormats = state.availableFormats
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(state.selectedTimeFormat) }

            Dialog(
                onDismissRequest = { openTimeFormatSelectionDialog.value = false }
            ) {
                Column(modifier = Modifier.fillMaxWidth(1f)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                                    .padding(start = 16.dp)
                                    .background(color = MaterialTheme.colors.surface),
                            ) {
                                Text(
                                    text = "Select Time Format",
                                    style = MaterialTheme.typography.h5
                                )
                            }

                            availableFormats.forEach { text ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = (text == selectedOption),
                                            onClick = { onOptionSelected(text) }
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (text == selectedOption),
                                        onClick = { onOptionSelected(text) }
                                    )
                                    Text(
                                        text = text.displayName,
                                        style = MaterialTheme.typography.body1.merge(),
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                            }

                            Button(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(16.dp),
                                onClick = {
                                    openTimeFormatSelectionDialog.value =
                                        openTimeFormatSelectionDialog.value.not()
                                    onTimeFormatChanged(selectedOption)
                                }
                            ) {
                                Text(text = stringResource(R.string.settings_confirm))
                            }
                        }
                    }
                }
            }
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
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { onOptionClicked() }
        .padding(16.dp)) {
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
