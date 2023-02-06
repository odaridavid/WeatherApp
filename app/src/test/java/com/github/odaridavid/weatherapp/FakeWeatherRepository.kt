package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.Weather
import com.github.odaridavid.weatherapp.core.WeatherRepository
import kotlinx.coroutines.flow.Flow

class FakeWeatherRepository : WeatherRepository {
    override fun fetchWeatherData(): Flow<Result<Weather>> {
        TODO("Not yet implemented")
    }

}
