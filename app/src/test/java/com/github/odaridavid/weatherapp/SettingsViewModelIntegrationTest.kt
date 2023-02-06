package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.ui.settings.SettingsViewModel
import io.mockk.mockk

class SettingsViewModelIntegrationTest {

    fun createSettingsScreenViewModel(): SettingsViewModel = SettingsViewModel(
        settingsRepository = mockk()
    )
}
