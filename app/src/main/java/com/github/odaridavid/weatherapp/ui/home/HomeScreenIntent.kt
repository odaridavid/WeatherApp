package com.github.odaridavid.weatherapp.ui.home

sealed class HomeScreenIntent {
    data object LoadWeatherData : HomeScreenIntent()

    data class DisplayCityName(val cityName: String) : HomeScreenIntent()
}
