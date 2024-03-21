package com.github.odaridavid.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.odaridavid.weatherapp.api.Logger
import com.github.odaridavid.weatherapp.api.SettingsRepository
import com.github.odaridavid.weatherapp.model.DefaultLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val logger: Logger
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState> = _state.asStateFlow()

    private val _hasAppUpdate = MutableStateFlow(false)
    val hasAppUpdate: StateFlow<Boolean> = _hasAppUpdate.asStateFlow()

    fun processIntent(mainViewIntent: MainViewIntent) {
        when (mainViewIntent) {
            is MainViewIntent.GrantPermission -> {
                setState { copy(isPermissionGranted = mainViewIntent.isGranted) }
            }

            is MainViewIntent.CheckLocationSettings -> {
                setState { copy(isLocationSettingEnabled = mainViewIntent.isEnabled) }
            }

            is MainViewIntent.ReceiveLocation -> {
                val defaultLocation = DefaultLocation(
                    longitude = mainViewIntent.longitude,
                    latitude = mainViewIntent.latitude
                )
                viewModelScope.launch {
                    settingsRepository.setDefaultLocation(defaultLocation)
                }
                setState { copy(defaultLocation = defaultLocation) }
            }

            is MainViewIntent.LogException -> {
                logger.logException(mainViewIntent.throwable)
            }

            is MainViewIntent.UpdateApp -> {
                viewModelScope.launch {
                    _hasAppUpdate.emit(true)
                }
            }
        }
    }

    private fun setState(stateReducer: MainViewState.() -> MainViewState) {
        viewModelScope.launch {
            _state.emit(stateReducer(state.value))
        }
    }
}

data class MainViewState(
    val isPermissionGranted: Boolean = false,
    val isLocationSettingEnabled: Boolean = false,
    val defaultLocation: DefaultLocation? = DefaultLocation(longitude = 0.0, latitude = 0.0)
)

sealed class MainViewIntent {

    data class GrantPermission(val isGranted: Boolean) : MainViewIntent()

    data class CheckLocationSettings(val isEnabled: Boolean) : MainViewIntent()

    data class ReceiveLocation(val latitude: Double, val longitude: Double) : MainViewIntent()

    data class LogException(val throwable: Throwable) : MainViewIntent()

    data object UpdateApp : MainViewIntent()
}
