package com.github.odaridavid.weatherapp.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.organism.MultiSelectBottomSheet
import com.github.odaridavid.weatherapp.designsystem.organism.SettingOptionRow
import com.github.odaridavid.weatherapp.designsystem.organism.SingleSelectBottomSheet
import com.github.odaridavid.weatherapp.designsystem.organism.TopNavigationBar
import com.github.odaridavid.weatherapp.designsystem.organism.VersionInfoText
import com.github.odaridavid.weatherapp.model.ExcludedData
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsScreenViewState,
    onBackButtonClicked: () -> Unit,
    onLanguageChanged: (SupportedLanguage) -> Unit,
    onUnitChanged: (Units) -> Unit,
    onTimeFormatChanged: (TimeFormat) -> Unit,
    onAboutClicked: () -> Unit,
    onExcludedDataChanged: (List<ExcludedData>) -> Unit,
) {
    Column {
        TopNavigationBar(
            onBackButtonClicked = onBackButtonClicked,
            title = stringResource(R.string.settings_screen_title),
        )

        val scope = rememberCoroutineScope()
        val languageSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        SettingOptionRow(
            optionLabel = stringResource(R.string.settings_language_label),
            optionValue = state.selectedLanguage.languageName,
            optionIcon = R.drawable.ic_language,
            optionIconContentDescription = stringResource(R.string.settings_content_description_lang_icon)
        ) {
            scope.launch {
                languageSheetState.show()
            }
        }

        val unitsSheetState = rememberModalBottomSheetState()
        SettingOptionRow(
            optionLabel = stringResource(R.string.settings_unit_label),
            optionValue = state.selectedUnit.value,
            optionIcon = R.drawable.ic_units,
            optionIconContentDescription = stringResource(R.string.settings_content_description_unit_icon)
        ) {
            scope.launch {
                unitsSheetState.show()
            }
        }

        val timeFormatSheetState = rememberModalBottomSheetState()
        SettingOptionRow(
            optionLabel = stringResource(R.string.settings_time_format),
            optionValue = state.selectedTimeFormat.value,
            optionIcon = R.drawable.ic_time_24,
            optionIconContentDescription = stringResource(R.string.settings_content_description_time_icon)
        ) {
            scope.launch {
                timeFormatSheetState.show()
            }
        }

        val excludeSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        SettingOptionRow(
            optionLabel = stringResource(id = R.string.settings_exclude_label),
            optionIcon = R.drawable.ic_exclude_24,
            optionValue = state.selectedExcludedDataDisplayValue,
            optionIconContentDescription = stringResource(R.string.settings_content_description_exclude_icon),
        ) {
            scope.launch {
                excludeSheetState.show()
            }
        }

        SettingOptionRow(
            optionLabel = stringResource(R.string.settings_about),
            optionIcon = R.drawable.ic_info_24,
            optionIconContentDescription = stringResource(R.string.settings_content_description_about_icon)
        ) {
            onAboutClicked()
        }

        SetupBottomSheets(
            state = state,
            onLanguageChanged = onLanguageChanged,
            onUnitChanged = onUnitChanged,
            onTimeFormatChanged = onTimeFormatChanged,
            onExcludedDataChanged = onExcludedDataChanged,
            languageSheetState = languageSheetState,
            unitsSheetState = unitsSheetState,
            timeFormatSheetState = timeFormatSheetState,
            excludeSheetState = excludeSheetState,
        )

        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.FULL))

        VersionInfoText(
            versionInfo = state.versionInfo,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SetupBottomSheets(
    state: SettingsScreenViewState,
    onLanguageChanged: (SupportedLanguage) -> Unit,
    onUnitChanged: (Units) -> Unit,
    onTimeFormatChanged: (TimeFormat) -> Unit,
    onExcludedDataChanged: (List<ExcludedData>) -> Unit,
    languageSheetState: SheetState,
    unitsSheetState: SheetState,
    timeFormatSheetState: SheetState,
    excludeSheetState: SheetState
) {

    if (languageSheetState.isVisible) {
        SingleSelectBottomSheet(
            title = stringResource(id = R.string.settings_language_label),
            sheetState = languageSheetState,
            selectedItem = state.selectedLanguage.toBottomSheetModel(isSelected = true),
            items = state.availableLanguages.map { it.toBottomSheetModel(isSelected = false) },
            onSaveState = { bottomSheet ->
                onLanguageChanged(bottomSheet.toSupportedLanguage())
            }
        )
    }

    if (unitsSheetState.isVisible) {
        SingleSelectBottomSheet(
            title = stringResource(id = R.string.settings_unit_label),
            sheetState = unitsSheetState,
            selectedItem = state.selectedUnit.toBottomSheetModel(isSelected = true),
            items = state.availableUnits.map { it.toBottomSheetModel(isSelected = false) },
            onSaveState = { bottomSheet ->
                onUnitChanged(bottomSheet.toUnits())
            }
        )
    }
    if (timeFormatSheetState.isVisible) {
        SingleSelectBottomSheet(
            title = stringResource(id = R.string.settings_time_format),
            sheetState = timeFormatSheetState,
            selectedItem = state.selectedTimeFormat.toBottomSheetModel(isSelected = true),
            items = state.availableFormats.map { it.toBottomSheetModel(isSelected = false) },
            onSaveState = { bottomSheet ->
                onTimeFormatChanged(bottomSheet.toTimeFormat())
            }
        )
    }
    if (excludeSheetState.isVisible) {
        MultiSelectBottomSheet(
            title = stringResource(id = R.string.settings_exclude_label),
            sheetState = excludeSheetState,
            selectedItems = state.selectedExcludedData.map { it.toBottomSheetModel(isSelected = true) },
            items = state.excludedData.map { it.toBottomSheetModel(isSelected = false) },
            onSaveState = { bottomSheet ->
                onExcludedDataChanged(bottomSheet.map { it.toExcludedData() })
            }
        )
    }
}
