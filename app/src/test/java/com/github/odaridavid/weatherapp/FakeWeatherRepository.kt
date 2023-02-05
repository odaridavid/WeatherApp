package com.github.odaridavid.weatherapp

import kotlinx.coroutines.flow.Flow

class FakeWeatherRepository :WeatherRepository{
    override fun fetchWeatherData(): Flow<Result<Weather>> {
        TODO("Not yet implemented")
    }

}
