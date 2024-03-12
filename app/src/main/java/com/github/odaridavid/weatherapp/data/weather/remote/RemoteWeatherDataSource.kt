package com.github.odaridavid.weatherapp.data.weather.remote

import com.github.odaridavid.weatherapp.core.Result
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.Weather

interface RemoteWeatherDataSource {

    suspend fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String,
        format: String,
        excludedData: String
    ): Result<Weather>
}
