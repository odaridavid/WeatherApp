package com.github.odaridavid.weatherapp.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.organism.BottomSheet
import com.github.odaridavid.weatherapp.designsystem.organism.SettingOptionRadioButton
import com.github.odaridavid.weatherapp.designsystem.organism.SettingOptionRow
import com.github.odaridavid.weatherapp.designsystem.organism.SettingOptionsDialog
import com.github.odaridavid.weatherapp.designsystem.organism.TopNavigationBar
import com.github.odaridavid.weatherapp.designsystem.organism.VersionInfoText

// todo replace the ugly looking dialogs with better looking bottom sheets.
@Composable
fun SettingsScreen(
    state: SettingsScreenViewState,
    onBackButtonClicked: () -> Unit,
    onLanguageChanged: (String) -> Unit,
    onUnitChanged: (String) -> Unit,
    onTimeFormatChanged: (String) -> Unit,
    onAboutClicked: () -> Unit,
    onExcludedDataChanged: (List<ExcludedData>) -> Unit,
) {
    Column {
        TopNavigationBar(
            onBackButtonClicked = onBackButtonClicked,
            title = stringResource(R.string.settings_screen_title),
        )

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
            optionValue = state.selectedTimeFormat,
            optionIcon = R.drawable.ic_time_24,
            optionIconContentDescription = stringResource(R.string.settings_content_description_time_icon)
        ) {
            openTimeFormatSelectionDialog.value = openTimeFormatSelectionDialog.value.not()
        }

        var showBottomSheet by remember { mutableStateOf(false) }
        SettingOptionRow(
            optionLabel = stringResource(id = R.string.settings_exclude_label),
            optionIcon = R.drawable.ic_exclude_24,
            optionValue = state.selectedExcludedDataDisplayValue,
            optionIconContentDescription = stringResource(R.string.settings_content_description_exclude_icon),
        ) {
            showBottomSheet = showBottomSheet.not()
        }

        SettingOptionRow(
            optionLabel = stringResource(R.string.settings_about),
            optionIcon = R.drawable.ic_info_24,
            optionIconContentDescription = stringResource(R.string.settings_content_description_about_icon)
        ) {
            onAboutClicked()
        }

        if (openLanguageSelectionDialog.value) {
            val availableLanguages = state.availableLanguages
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(state.selectedLanguage) }
            SettingOptionsDialog(
                onDismiss = { openLanguageSelectionDialog.value = false },
                onConfirm = {
                    onLanguageChanged(selectedOption)
                    openLanguageSelectionDialog.value = false
                },
                items = availableLanguages,
            ) { language ->
                SettingOptionRadioButton(
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
                onConfirm = {
                    onUnitChanged(selectedOption)
                    openUnitSelectionDialog.value = false
                },
                items = availableUnits,
            ) { unit ->
                SettingOptionRadioButton(
                    text = unit,
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected
                )
            }
        }

        if (openTimeFormatSelectionDialog.value) {
            val availableFormats = state.availableFormats
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(state.selectedTimeFormat) }
            SettingOptionsDialog(
                onDismiss = { openTimeFormatSelectionDialog.value = false },
                onConfirm = {
                    onTimeFormatChanged(selectedOption)
                    openUnitSelectionDialog.value = false
                },
                items = availableFormats,
            ) { unit ->
                SettingOptionRadioButton(
                    text = unit,
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected
                )
            }
        }

        if (showBottomSheet) {
            BottomSheet(
                title = stringResource(id = R.string.settings_exclude_label),
                selectedExcludedData = state.selectedExcludedData,
                items = state.excludedData,
                onDismiss = {
                    showBottomSheet = showBottomSheet.not()
                },
                onSaveOption = { excludedData ->
                    onExcludedDataChanged(excludedData)
                    showBottomSheet = showBottomSheet.not()
                }
            )
        }

        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.FULL))

        VersionInfoText(
            versionInfo = state.versionInfo,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}
