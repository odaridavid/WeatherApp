package com.github.odaridavid.weatherapp.api

import com.github.odaridavid.weatherapp.model.DefaultLocation
import com.github.odaridavid.weatherapp.model.Result
import com.github.odaridavid.weatherapp.model.Weather

interface WeatherRepository {

    suspend fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String
    ) : Result<Weather>
}
