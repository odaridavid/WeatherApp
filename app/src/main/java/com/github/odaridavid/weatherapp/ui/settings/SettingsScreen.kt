package com.github.odaridavid.weatherapp.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.IconWithAction
import com.github.odaridavid.weatherapp.designsystem.MenuHeadline
import com.github.odaridavid.weatherapp.designsystem.SettingOptionRow
import com.github.odaridavid.weatherapp.designsystem.SettingOptions
import com.github.odaridavid.weatherapp.designsystem.SettingOptionsDialog
import com.github.odaridavid.weatherapp.designsystem.VersionInfoText

@Composable
fun SettingsScreen(
    state: SettingsScreenViewState,
    onBackButtonClicked: () -> Unit,
    onLanguageChanged: (String) -> Unit,
    onUnitChanged: (String) -> Unit
) {
    Column {
        SettingsTopBar(onBackButtonClicked)

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

        if (openLanguageSelectionDialog.value) {
            val availableLanguages = state.availableLanguages
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(state.selectedLanguage) }
            SettingOptionsDialog(
                onDismiss = { openLanguageSelectionDialog.value = false },
                onConfirm = { onLanguageChanged(selectedOption) },
                items = availableLanguages,
            ) { language ->
                SettingOptions(
                    text = language,
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected
                )
            }
        }

        if (openUnitSelectionDialog.value) {
            val availableUnits = state.availableUnits
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(state.selectedUnit) }
            SettingOptionsDialog(
                onDismiss = { openUnitSelectionDialog.value = false },
                onConfirm = { onUnitChanged(selectedOption) },
                items = availableUnits,
            ) { units ->
                SettingOptions(
                    text = units,
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected
                )
            }
        }

        Spacer(modifier = Modifier.weight(1.0f))

        VersionInfoText(
            versionInfo = state.versionInfo,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
private fun SettingsTopBar(onBackButtonClicked: () -> Unit) {
    Row(modifier = Modifier.padding(16.dp)) {
        IconWithAction(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = stringResource(R.string.settings_content_description_icon),
            modifier = Modifier
                .padding(8.dp),
            onClicked = { onBackButtonClicked() }
        )

        MenuHeadline(
            text = stringResource(R.string.settings_screen_title),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

