package com.github.odaridavid.weatherapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenViewState(isLoading = true))
    val state: StateFlow<HomeScreenViewState> = _state

    init {
        viewModelScope.launch {
            settingsRepository.getLanguage().collect { language ->
                setState { copy(language = language) }
            }
        }
        viewModelScope.launch {
            settingsRepository.getUnits().collect { units ->
                setState { copy(units = units) }
            }
        }
        viewModelScope.launch {
            settingsRepository.getDefaultLocation().collect { defaultLocation ->
                setState { copy(defaultLocation = defaultLocation) }
            }
        }
    }

    fun processIntent(homeScreenIntent: HomeScreenIntent) {
        when (homeScreenIntent) {
            is HomeScreenIntent.LoadWeatherData -> {
                viewModelScope.launch {
                    weatherRepository.fetchWeatherData(
                        language = state.value.language,
                        defaultLocation = state.value.defaultLocation,
                        units = state.value.units
                    ).collect { result ->
                        processResult(result)
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
    val units: String = "",
    val defaultLocation: DefaultLocation = DefaultLocation(0.0, 0.0),
    val locationName:String = "-",
    val language: String = "",
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
