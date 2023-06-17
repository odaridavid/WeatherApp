package com.github.odaridavid.weatherapp.ui.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.ConfirmButton
import com.github.odaridavid.weatherapp.designsystem.IconWithAction
import com.github.odaridavid.weatherapp.designsystem.MenuHeadline
import com.github.odaridavid.weatherapp.designsystem.SettingOptions
import com.github.odaridavid.weatherapp.designsystem.VersionInfoText

@Composable
fun SettingsScreen(
    state: SettingsScreenViewState,
    onBackButtonClicked: () -> Unit,
    onLanguageChanged: (String) -> Unit,
    onUnitChanged: (String) -> Unit
) {
    Column {
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
            Dialog(onDismissRequest = { openLanguageSelectionDialog.value = false }) {
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.surface)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column {
                        availableLanguages.forEach { language ->
                            SettingOptions(
                                text = language,
                                selectedOption = selectedOption,
                                onOptionSelected = onOptionSelected
                            )
                        }
                    }
                    ConfirmButton(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        onClick = { onLanguageChanged(selectedOption) }
                    )
                }
            }
        }

        if (openUnitSelectionDialog.value) {
            val availableUnits = state.availableUnits
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(state.selectedUnit) }
            Dialog(onDismissRequest = { openUnitSelectionDialog.value = false }) {
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.surface)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column {
                        availableUnits.forEach { unitText ->
                            SettingOptions(unitText, selectedOption, onOptionSelected)
                        }
                    }
                    ConfirmButton(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        onClick = {
                            onUnitChanged(selectedOption)
                        },
                    )
                }
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
