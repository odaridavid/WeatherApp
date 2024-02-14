package com.github.odaridavid.weatherapp.ui.settings

sealed class SettingsScreenIntent {

    object LoadSettingScreenData : SettingsScreenIntent()

    data class ChangeLanguage(val selectedLanguage: String) : SettingsScreenIntent()

    data class ChangeUnits(val selectedUnits: String) : SettingsScreenIntent()

    data class ChangeTimeFormat(val selectedTimeFormat: String) : SettingsScreenIntent()
}
