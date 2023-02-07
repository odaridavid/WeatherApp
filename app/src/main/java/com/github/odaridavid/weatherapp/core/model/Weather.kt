package com.github.odaridavid.weatherapp.core.model

data class Weather(
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)

data class CurrentWeather(
    val sunriseTime: Long,
    val sunsetTime: Long,
    val temperature: String,
    val feelsLike: String,
    val weather: List<WeatherInfo>
)

data class HourlyWeather(
    val forecastedTime: Long,
    val temperature: String,
    val feelsLike: String,
    val weather: List<WeatherInfo>
)

data class DailyWeather(
    val forecastedTime: Long,
    val sunriseTime: Long,
    val sunsetTime: Long,
    val temperature: Temperature,
    val feelsLike: FeelsLike,
    val weather: List<WeatherInfo>
)

data class WeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Temperature(
    val morning: String,
    val afternoon: String,
    val evening: String,
    val night: String,
    val min: String,
    val max: String,
)

data class FeelsLike(
    val morning: String,
    val afternoon: String,
    val evening: String,
    val night: String,
)
