package com.github.odaridavid.weatherapp.ui.home

sealed class HomeScreenIntent {
    object LoadWeatherData : HomeScreenIntent()
}
