package com.github.odaridavid.weatherapp.model

data class Weather(
    val current: CurrentWeather?,
    val hourly: List<HourlyWeather>?,
    val daily: List<DailyWeather>?
)

data class CurrentWeather(
    val temperature: String,
    val feelsLike: String,
    val weather: List<WeatherInfo>
)

data class HourlyWeather(
    val forecastedTime: String,
    val temperature: String,
    val weather: List<WeatherInfo>
)

data class DailyWeather(
    val forecastedTime: String,
    val temperature: Temperature,
    val weather: List<WeatherInfo>
)

data class WeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Temperature(
    val min: String,
    val max: String,
)
