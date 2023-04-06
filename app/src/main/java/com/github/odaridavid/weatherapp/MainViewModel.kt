package com.github.odaridavid.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState> = _state.asStateFlow()

    fun processIntent(mainViewIntent: MainViewIntent) {
        when (mainViewIntent) {
            is MainViewIntent.GrantPermission -> {
                setState { copy(isPermissionGranted = mainViewIntent.isGranted) }
            }
            is MainViewIntent.CheckLocationEnabled -> {
                setState { copy(canFetchLocation = mainViewIntent.isPossible) }
            }
        }
    }

    private fun setState(stateReducer: MainViewState.() -> MainViewState) {
        viewModelScope.launch {
            _state.emit(stateReducer(state.value))
        }
    }

}

data class MainViewState(val isPermissionGranted: Boolean = false, val canFetchLocation: Boolean = false)

sealed class MainViewIntent {

    data class GrantPermission(val isGranted: Boolean) : MainViewIntent()

    data class CheckLocationEnabled(val isPossible: Boolean) : MainViewIntent()

}
