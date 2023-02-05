package com.github.odaridavid.weatherapp

import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeatherData() : Flow<Result<Weather>>
}
