package com.github.odaridavid.weatherapp

data class Weather(
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)

data class CurrentWeather(
    val sunriseTime: Long,
    val sunsetTime: Long,
    val temperature: Float,
    val feelsLike: Float,
    val weather: List<WeatherInfo>
)

data class HourlyWeather(
    val forecastedTime: Long,
    val temperature: Float,
    val feelsLike: Float,
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
    val morning: Float,
    val afternoon: Float,
    val evening: Float,
    val night: Float,
    val min: Float,
    val max: Float,
)

data class FeelsLike(
    val morning: Float,
    val afternoon: Float,
    val evening: Float,
    val night: Float,
)
