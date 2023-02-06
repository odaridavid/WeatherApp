package com.github.odaridavid.weatherapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenViewState(isLoading = true))
    val state: StateFlow<HomeScreenViewState> = _state

    init {
        // TODO Load daily,current and  hourly differently
        processIntent(HomeScreenIntent.LoadWeatherData)
    }

    private fun processIntent(homeScreenIntent: HomeScreenIntent) {
        when (homeScreenIntent) {
            is HomeScreenIntent.LoadWeatherData -> {
                viewModelScope.launch {
                    combine(
                        settingsRepository.getLanguage(),
                        settingsRepository.getUnits(),
                        settingsRepository.getDefaultLocation()
                    ) { language, units, defaultLocation ->
                        weatherRepository.fetchWeatherData(
                            language = language,
                            defaultLocation = defaultLocation,
                            units = units
                        ).collect { result ->
                            processResult(result)
                        }
                    }
                }
            }
        }
    }

    private fun processResult(result: Result<Weather>) {
        when {
            result.isSuccess -> {
                val weatherData = result.getOrThrow()
                setState {
                    copy(
                        weather = weatherData,
                        isLoading = false,
                        error = null
                    )
                }
            }

            result.isFailure -> {
                // TODO Localise errors
                setState {
                    copy(
                        isLoading = false,
                        error = result.exceptionOrNull()
                            ?: Throwable("Unknown error occurred")
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
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
