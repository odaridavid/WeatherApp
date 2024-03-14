package com.github.odaridavid.weatherapp.ui.settings

import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.core.model.TimeFormat

sealed class SettingsScreenIntent {

    data object LoadSettingScreenData : SettingsScreenIntent()

    data class ChangeLanguage(val selectedLanguage: String) : SettingsScreenIntent()

    data class ChangeUnits(val selectedUnits: String) : SettingsScreenIntent()

    data class ChangeTimeFormat(val selectedTimeFormat: TimeFormat) : SettingsScreenIntent()

    data class ChangeExcludedData(val selectedExcludedData: List<ExcludedData>) :
        SettingsScreenIntent()
}
