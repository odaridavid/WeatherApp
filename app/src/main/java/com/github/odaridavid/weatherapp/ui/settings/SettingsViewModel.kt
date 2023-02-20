package com.github.odaridavid.weatherapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.model.TimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.Time
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsScreenViewState())
    val state: StateFlow<SettingsScreenViewState> = _state.asStateFlow()

    fun processIntent(settingsScreenIntent: SettingsScreenIntent) {
        when (settingsScreenIntent) {
            SettingsScreenIntent.LoadSettingScreenData -> {
                // app version
                viewModelScope.launch {
                    setState { copy(versionInfo = settingsRepository.getAppVersion()) }
                }

                // language
                viewModelScope.launch {
                    settingsRepository.getLanguage().collect { language ->
                        setState { copy(selectedLanguage = language) }
                    }
                }
                viewModelScope.launch {
                    setState { copy(availableLanguages = settingsRepository.getAvailableLanguages()) }
                }

                // units
                viewModelScope.launch {
                    settingsRepository.getUnits().collect { units ->
                        setState { copy(selectedUnit = units) }
                    }
                }
                viewModelScope.launch {
                    setState { copy(availableUnits = settingsRepository.getAvailableMetrics()) }
                }

                // time format
                viewModelScope.launch {
                    settingsRepository.getFormat().collect { format ->
                        setState { copy(selectedTimeFormat = TimeFormat.valueOf(format)) }
                    }
                }
                viewModelScope.launch {
                    setState { copy(availableFormats = settingsRepository.getFormats()) }
                }
            }

            is SettingsScreenIntent.ChangeLanguage -> {
                viewModelScope.launch {
                    settingsRepository.setLanguage(settingsScreenIntent.selectedLanguage)
                    setState { copy(selectedLanguage = settingsScreenIntent.selectedLanguage) }
                }
            }

            is SettingsScreenIntent.ChangeUnits -> {
                viewModelScope.launch {
                    settingsRepository.setUnits(settingsScreenIntent.selectedUnits)
                    setState { copy(selectedUnit = settingsScreenIntent.selectedUnits) }
                }
            }
            is SettingsScreenIntent.ChangeTimeFormat -> {
                viewModelScope.launch {
                    val format = settingsScreenIntent.selectedTimeFormat
                    settingsRepository.setFormat(format)
                    setState { copy(selectedTimeFormat = format) }
                }
            }
        }
    }

    private fun setState(stateReducer: SettingsScreenViewState.() -> SettingsScreenViewState) {
        viewModelScope.launch {
            _state.emit(stateReducer(state.value))
        }
    }
}

data class SettingsScreenViewState(
    val selectedUnit: String = "",
    val selectedLanguage: String = "",
    val availableLanguages: List<String> = emptyList(),
    val availableUnits: List<String> = emptyList(),
    val selectedTimeFormat: TimeFormat = TimeFormat.TWELVE_HOUR,
    val availableFormats: List<TimeFormat> = emptyList(),
    val versionInfo: String = "",
    val error: Throwable? = null
)
