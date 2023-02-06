package com.github.odaridavid.weatherapp.ui.settings

sealed class SettingsScreenIntent {

    object LoadSettingScreenData : SettingsScreenIntent()

    object ChangeLanguage : SettingsScreenIntent()

    object ChangeUnits : SettingsScreenIntent()
}
