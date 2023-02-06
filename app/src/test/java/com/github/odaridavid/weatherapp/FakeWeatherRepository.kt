package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import kotlinx.coroutines.flow.Flow

class FakeWeatherRepository : WeatherRepository {
    override fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String
    ): Flow<Result<Weather>> {
        TODO("Not yet implemented")
    }


}
