package com.github.odaridavid.weatherapp.core.api

import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String
    ) : Flow<Result<Weather>>
}
