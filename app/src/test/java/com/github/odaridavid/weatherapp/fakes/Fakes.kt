package com.github.odaridavid.weatherapp.fakes

import com.github.odaridavid.weatherapp.data.weather.remote.CurrentWeatherResponse
import com.github.odaridavid.weatherapp.data.weather.remote.DailyWeatherResponse
import com.github.odaridavid.weatherapp.data.weather.remote.HourlyWeatherResponse
import com.github.odaridavid.weatherapp.data.weather.remote.TemperatureResponse
import com.github.odaridavid.weatherapp.data.weather.remote.WeatherResponse
import com.github.odaridavid.weatherapp.model.CurrentWeather
import com.github.odaridavid.weatherapp.model.DailyWeather
import com.github.odaridavid.weatherapp.model.HourlyWeather
import com.github.odaridavid.weatherapp.model.Temperature
import com.github.odaridavid.weatherapp.model.Weather

// TODO Populate the responses with more data to test the mappers
val fakeSuccessWeatherResponse = WeatherResponse(
    current = CurrentWeatherResponse(
        temperature = 3.0f,
        feelsLike = 2.0f,
        weather = listOf()
    ),
    hourly = listOf(
        HourlyWeatherResponse(
            forecastedTime = 1618310400,
            temperature = 3.0f,
            weather = listOf()
        )
    ),
    daily = listOf(
        DailyWeatherResponse(
            forecastedTime = 1618310400,
            temperature = TemperatureResponse(min = 0.0f, max = 10.0f),
            weather = listOf()
        )
    )
)


val fakeSuccessMappedWeatherResponse = Weather(
    current = CurrentWeather(
        temperature = "3°C",
        feelsLike = "2°C",
        weather = listOf()
    ),
    hourly = listOf(
        HourlyWeather(
            forecastedTime = "10:40 AM",
            temperature = "3°C",
            weather = listOf()
        )
    ),
    daily = listOf(
        DailyWeather(
            forecastedTime = "Tuesday 13/4",
            temperature = Temperature(min = "0°C", max = "10°C"),
            weather = listOf(),
        )
    )
)
// TODO Parameterized tests to cover different formattings, time/date formats, temperature specifically.