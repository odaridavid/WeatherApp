package com.github.odaridavid.weatherapp.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.odaridavid.weatherapp.api.SettingsRepository
import com.github.odaridavid.weatherapp.api.WeatherRepository
import com.github.odaridavid.weatherapp.model.DefaultLocation
import com.github.odaridavid.weatherapp.model.Result
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.Units
import com.github.odaridavid.weatherapp.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenViewState(isLoading = true))
    val state: StateFlow<HomeScreenViewState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.getLanguage(),
                settingsRepository.getUnits(),
                settingsRepository.getDefaultLocation()
            ) { language, units, defaultLocation ->
                Triple(language, units, defaultLocation)
            }.collect { (language, units, defaultLocation) ->
                setState {
                    copy(
                        language = language,
                        units = units,
                        defaultLocation = defaultLocation
                    )
                }.also { processIntent(HomeScreenIntent.LoadWeatherData) }
            }
        }
    }

    fun processIntent(homeScreenIntent: HomeScreenIntent) {
        when (homeScreenIntent) {
            is HomeScreenIntent.LoadWeatherData -> {
                viewModelScope.launch {
                    val result = weatherRepository.fetchWeatherData(
                        language = state.value.language.languageValue,
                        defaultLocation = state.value.defaultLocation,
                        units = state.value.units.value
                    )
                    processResult(result)
                }
            }

            is HomeScreenIntent.DisplayCityName -> {
                setState { copy(locationName = homeScreenIntent.cityName) }
            }
        }
    }

    private fun processResult(result: Result<Weather>) {
        when (result) {
            is Result.Success -> {
                val weatherData = result.data
                setState {
                    copy(
                        weather = weatherData,
                        isLoading = false,
                        errorMessageId = null
                    )
                }
            }

            is Result.Error -> {
                setState {
                    copy(
                        isLoading = false,
                        errorMessageId = result.errorType.toResourceId()
                    )
                }
            }
        }
    }

    private fun setState(stateReducer: HomeScreenViewState.() -> HomeScreenViewState) {
        viewModelScope.launch {
            _state.emit(stateReducer(state.value))
        }
    }
}

data class HomeScreenViewState(
    val units: Units = Units.METRIC,
    val defaultLocation: DefaultLocation = DefaultLocation(0.0, 0.0),
    val locationName: String = "-",
    val language: SupportedLanguage = SupportedLanguage.ENGLISH,
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null
)
