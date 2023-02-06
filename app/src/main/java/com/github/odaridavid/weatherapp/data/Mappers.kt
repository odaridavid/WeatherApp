package com.github.odaridavid.weatherapp.data

import com.github.odaridavid.weatherapp.core.CurrentWeather
import com.github.odaridavid.weatherapp.core.DailyWeather
import com.github.odaridavid.weatherapp.core.FeelsLike
import com.github.odaridavid.weatherapp.core.HourlyWeather
import com.github.odaridavid.weatherapp.core.Temperature
import com.github.odaridavid.weatherapp.core.Weather

// TODO Check on domain naming
fun WeatherResponse.toCoreModel(): Weather = Weather(
    current = current.toCoreModel(),
    daily = daily.map { it.toCoreModel() },
    hourly = hourly.map { it.toCoreModel() }
)

// TODO Populate and format data
fun CurrentWeatherResponse.toCoreModel(): CurrentWeather =
    CurrentWeather(
        sunriseTime = 0, sunsetTime = 0, temperature = 0.0f, feelsLike = 0.0f, weather = listOf()
    )

fun DailyWeatherResponse.toCoreModel(): DailyWeather =
    DailyWeather(
        forecastedTime = 0, sunriseTime = 0, sunsetTime = 0, temperature = Temperature(
            morning = 0.0f,
            afternoon = 0.0f,
            evening = 0.0f,
            night = 0.0f,
            min = 0.0f,
            max = 0.0f
        ), feelsLike = FeelsLike(
            morning = 0.0f,
            afternoon = 0.0f,
            evening = 0.0f,
            night = 0.0f
        ), weather = listOf()

    )

fun HourlyWeatherResponse.toCoreModel(): HourlyWeather =
    HourlyWeather(
        forecastedTime = 0, temperature = 0.0f, feelsLike = 0.0f, weather = listOf()
    )
