package com.github.odaridavid.weatherapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.odaridavid.weatherapp.core.api.Logger
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.core.model.TimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val logger: Logger,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsScreenViewState())
    val state: StateFlow<SettingsScreenViewState> = _state.asStateFlow()

    fun processIntent(settingsScreenIntent: SettingsScreenIntent) {
        when (settingsScreenIntent) {
            SettingsScreenIntent.LoadSettingScreenData -> {
                viewModelScope.launch {
                    combine(
                        settingsRepository.getLanguage(),
                        settingsRepository.getUnits(),
                        settingsRepository.getFormat(),
                        settingsRepository.getExcludedData()
                    ) { language, units, format, excludedData ->
                        SettingsScreenViewState(
                            selectedLanguage = language,
                            selectedUnit = units,
                            selectedTimeFormat = format,
                            selectedExcludedData = mapStringToExcludedData(excludedData),
                            selectedExcludedDataDisplayValue = excludedData,
                            versionInfo = settingsRepository.getAppVersion(),
                            // TODO Call these directly without a repo
                            availableLanguages = settingsRepository.getAvailableLanguages(),
                            availableUnits = settingsRepository.getAvailableUnits(),
                            availableFormats = TimeFormat.entries,
                            excludedData = ExcludedData.entries,
                        )
                    }.collect { state ->
                        setState { state }
                    }
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

            is SettingsScreenIntent.ChangeExcludedData -> {
                viewModelScope.launch {
                    settingsRepository.setExcludedData(settingsScreenIntent.selectedExcludedData)
                    setState {
                        copy(
                            selectedExcludedData = settingsScreenIntent.selectedExcludedData,
                            selectedExcludedDataDisplayValue = mapExcludedDataToDisplayValue(
                                settingsScreenIntent.selectedExcludedData
                            ),
                        )
                    }
                }
            }
        }
    }

    private fun setState(stateReducer: SettingsScreenViewState.() -> SettingsScreenViewState) {
        viewModelScope.launch {
            _state.emit(stateReducer(state.value))
        }
    }

    private fun mapExcludedDataToDisplayValue(excludedData: List<ExcludedData>): String =
        excludedData.joinToString(separator = ",") { it.value.trim() }

    private fun mapStringToExcludedData(excludedData: String): List<ExcludedData> {
        return excludedData.split(",").map {
            when (it.trim()) {
                ExcludedData.CURRENT.value -> ExcludedData.CURRENT
                ExcludedData.HOURLY.value -> ExcludedData.HOURLY
                ExcludedData.DAILY.value -> ExcludedData.DAILY
                ExcludedData.MINUTELY.value -> ExcludedData.MINUTELY
                ExcludedData.ALERTS.value -> ExcludedData.ALERTS
                ExcludedData.NONE.value -> ExcludedData.NONE
                else -> {
                    logger.logException(IllegalArgumentException("Invalid excluded data"))
                    ExcludedData.NONE
                }
            }
        }
    }
}

data class SettingsScreenViewState(
    val selectedUnit: String = "",
    val selectedLanguage: String = "",
    val selectedTimeFormat: TimeFormat = TimeFormat.TWENTY_FOUR_HOUR,
    val selectedExcludedData: List<ExcludedData> = emptyList(),
    val selectedExcludedDataDisplayValue: String = "",
    val availableLanguages: List<String> = emptyList(),
    val availableUnits: List<String> = emptyList(),
    val availableFormats: List<TimeFormat> = emptyList(),
    val excludedData: List<ExcludedData> = emptyList(),
    val versionInfo: String = "",
    val error: Throwable? = null
)
