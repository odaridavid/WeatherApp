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

    private val _state = MutableStateFlow(HomeScreenViewState.Loading as HomeScreenViewState)
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
                    toSuccessState(
                        language = language,
                        units = units,
                        defaultLocation = defaultLocation
                    )
                }

                processIntent(HomeScreenIntent.LoadWeatherData)
            }
        }
    }

    fun processIntent(homeScreenIntent: HomeScreenIntent) {
        when (homeScreenIntent) {
            is HomeScreenIntent.LoadWeatherData -> {
                viewModelScope.launch {
                    val result = weatherRepository.fetchWeatherData(
                        language = (state.value as HomeScreenViewState.Success).language.languageValue,
                        defaultLocation = (state.value as HomeScreenViewState.Success).defaultLocation,
                        units = (state.value as HomeScreenViewState.Success).units.value
                    )
                    processResult(result)
                }
            }

            is HomeScreenIntent.DisplayCityName -> {
                setState {
                    toSuccessState(locationName = homeScreenIntent.cityName)
                }
            }
        }
    }

    private fun processResult(result: Result<Weather>) {
        when (result) {
            is Result.Success -> {
                val weatherData = result.data
                setState {
                    toSuccessState(weather = weatherData)
                }
            }

            is Result.Error -> {
                setState {
                    HomeScreenViewState.Error(errorMessageId = result.errorType.toResourceId())
                }
            }
        }
    }

    private fun setState(stateReducer: HomeScreenViewState.() -> HomeScreenViewState) {
        viewModelScope.launch {
            _state.emit(stateReducer(state.value))
        }
    }

    // TODO Fix dissapeared name on the top bar
    private fun HomeScreenViewState.toSuccessState(
        weather: Weather? = null,
        units: Units = Units.METRIC,
        locationName: String = "",
        language: SupportedLanguage = SupportedLanguage.ENGLISH,
        defaultLocation: DefaultLocation = DefaultLocation(0.0, 0.0),
    ): HomeScreenViewState.Success {
        return when (this) {
            is HomeScreenViewState.Success -> this.copy(
                units = if (this.units != units) units else this.units,
                defaultLocation = if (this.defaultLocation != defaultLocation) defaultLocation else this.defaultLocation,
                locationName = if (this.locationName != locationName) locationName else this.locationName,
                language = if (this.language != language) language else this.language,
                weather = weather ?: this.weather
            )

            is HomeScreenViewState.Loading, is HomeScreenViewState.Error -> HomeScreenViewState.Success(
                units = units,
                defaultLocation = defaultLocation,
                locationName = locationName,
                language = language,
                weather = weather ?: Weather(null, null, null)
            )
        }
    }
}

sealed class HomeScreenViewState {
    data class Success(
        val units: Units,
        val defaultLocation: DefaultLocation = DefaultLocation(0.0, 0.0),
        val locationName: String,
        val language: SupportedLanguage,
        val weather: Weather
    ) : HomeScreenViewState()

    object Loading : HomeScreenViewState()

    data class Error(@StringRes val errorMessageId: Int) : HomeScreenViewState()
}
