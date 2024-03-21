package com.github.odaridavid.weatherapp.ui.settings

import com.github.odaridavid.weatherapp.model.ExcludedData
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units

sealed class SettingsScreenIntent {

    data object LoadSettingScreenData : SettingsScreenIntent()

    data class ChangeLanguage(val selectedLanguage: SupportedLanguage) : SettingsScreenIntent()

    data class ChangeUnits(val selectedUnits: Units) : SettingsScreenIntent()

    data class ChangeTimeFormat(val selectedTimeFormat: TimeFormat) : SettingsScreenIntent()

    data class ChangeExcludedData(val selectedExcludedData: List<ExcludedData>) :
        SettingsScreenIntent()
}
