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
val fakeSuccessResponse = Weather(
    current = CurrentWeather(
        temperature = "0°C",
        feelsLike = "0°C",
        weather = listOf()
    ),
    hourly = listOf(),
    daily = listOf()
)

val fakePopulatedResponse = PopulatedWeather(
    current = WeatherEntity(
        feels_like = 2.0f,
        temp = 3.0f,
        weather = listOf()
    ),
    hourly = listOf(),
    daily = listOf()
)