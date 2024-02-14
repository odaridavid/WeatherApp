package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.data.weather.remote.CurrentWeatherResponse
import com.github.odaridavid.weatherapp.data.weather.remote.WeatherResponse

// TODO Populate the responses with more data to test the mappers
val fakeSuccessWeatherResponse = WeatherResponse(
    current = CurrentWeatherResponse(
        temperature = 3.0f,
        feelsLike = 2.0f,
        weather = listOf()
    ),
    hourly = listOf(),
    daily = listOf()
)

val fakeSuccessMappedWeatherResponse = Weather(
    current = CurrentWeather(
        temperature = "3°C",
        feelsLike = "2°C",
        weather = listOf()
    ),
    hourly = listOf(),
    daily = listOf()
)
