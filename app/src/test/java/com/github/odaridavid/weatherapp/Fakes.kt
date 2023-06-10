package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DailyWeather
import com.github.odaridavid.weatherapp.core.model.HourlyWeather
import com.github.odaridavid.weatherapp.core.model.Temperature
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.model.WeatherInfo
import com.github.odaridavid.weatherapp.data.weather.CurrentWeatherResponse
import com.github.odaridavid.weatherapp.data.weather.DailyWeatherResponse
import com.github.odaridavid.weatherapp.data.weather.HourlyWeatherResponse
import com.github.odaridavid.weatherapp.data.weather.TemperatureResponse
import com.github.odaridavid.weatherapp.data.weather.WeatherInfoResponse
import com.github.odaridavid.weatherapp.data.weather.WeatherResponse
import com.github.odaridavid.weatherapp.data.weather.local.entity.DailyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.HourlyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.PopulatedWeather
import com.github.odaridavid.weatherapp.data.weather.local.entity.TemperatureEntity
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
    daily = listOf()
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
                icon = "icon"
            )
        )
    ),
    hourly = listOf(),
    daily = listOf()
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
                icon = "icon"
            )
        )
    ),
    hourly = listOf(),
    daily = listOf()
)

val fakePopulatedResponse = PopulatedWeather(
    current = WeatherEntity(
        dt = 1L,
        main = "main",
        temp = 3.0F,
        temp_max = 0.0F,
        temp_min = 0.0F,
        description ="desc",
        icon = "icon",
        id = 1,
        feels_like = 2.0f,
    ),
    hourly = listOf(),
    daily = listOf()
)