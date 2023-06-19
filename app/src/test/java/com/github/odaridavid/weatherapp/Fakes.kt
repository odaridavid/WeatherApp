package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.model.WeatherInfo
import com.github.odaridavid.weatherapp.data.weather.CurrentWeatherResponse
import com.github.odaridavid.weatherapp.data.weather.WeatherInfoResponse
import com.github.odaridavid.weatherapp.data.weather.WeatherResponse
import com.github.odaridavid.weatherapp.data.weather.local.entity.CurrentWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.CurrentWithWeatherInfo
import com.github.odaridavid.weatherapp.data.weather.local.entity.PopulatedWeather
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherInfoResponseEntity

val fakeSuccessWeatherResponse = WeatherResponse(
    current = CurrentWeatherResponse(
        temperature = 3.0f,
        feelsLike = 2.0f,
        weather = listOf(
            WeatherInfoResponse(
                id = 1,
                main = "main",
                description = "desc",
                icon = "icon"
            )
        )
    ),
    hourly = listOf(),
    daily = listOf(),
    lat = 0.00,
    long = 0.00
)

val fakeSuccessMappedWeatherResponse = Weather(
    current = CurrentWeather(
        temperature = "3°C",
        feelsLike = "2°C",
        weather = listOf(
            WeatherInfo(
                id = 1,
                main = "main",
                description = "desc",
                icon = BuildConfig.OPEN_WEATHER_ICONS_URL
            )
        )
    ),
    hourly = listOf(),
    daily = listOf(),
    lat = 0.00,
    long = 0.00
)
val fakeSuccessResponse = Weather(
    current = CurrentWeather(
        temperature = "3°C",
        feelsLike = "2°C",
        weather = listOf(
            WeatherInfo(
                id = 1,
                main = "main",
                description = "desc",
                icon = BuildConfig.OPEN_WEATHER_ICONS_URL
            )
        )
    ),
    hourly = listOf(),
    daily = listOf(),
    lat = 0.00,
    long = 0.00
)

val fakePopulatedResponse = PopulatedWeather(
    weather = WeatherEntity(
        weatherId = 0,
        lat = 0.00,
        lon = 0.00
    ),
    current = CurrentWithWeatherInfo(
        currentWeather = CurrentWeatherEntity(
            currentId = 0,
            feelsLike = 1f,
            temp = 1f,
        ),
        weather = listOf(
            WeatherInfoResponseEntity(
                id = 1,
                main = "main",
                description = "desc",
                icon = "icon"
            )
        )
    ),
    hourly = listOf(),
    daily = listOf()
)