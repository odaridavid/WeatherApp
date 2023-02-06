package com.github.odaridavid.weatherapp.ui.settings

import androidx.lifecycle.ViewModel
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

}

data class SettingsScreenViewState(
    val units: String = "",
    val language: String = "",
    val versionInfo: String = "",
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
