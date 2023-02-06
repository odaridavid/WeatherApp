package com.github.odaridavid.weatherapp.core

import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeatherData() : Flow<Result<Weather>>
}
