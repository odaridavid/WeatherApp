package com.github.odaridavid.weatherapp.data.weather.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current") val current: CurrentWeatherResponse? = null,
    @SerialName("hourly") val hourly: List<HourlyWeatherResponse>? = null,
    @SerialName("daily") val daily: List<DailyWeatherResponse>? = null,
)

@Serializable
data class CurrentWeatherResponse(
    @SerialName("temp") val temperature: Float,
    @SerialName("feels_like") val feelsLike: Float,
    @SerialName("weather") val weather: List<WeatherInfoResponse>
)

@Serializable
data class HourlyWeatherResponse(
    @SerialName("dt") val forecastedTime: Long,
    @SerialName("temp") val temperature: Float,
    @SerialName("weather") val weather: List<WeatherInfoResponse>
)

@Serializable
data class DailyWeatherResponse(
    @SerialName("dt") val forecastedTime: Long,
    @SerialName("temp") val temperature: TemperatureResponse,
    @SerialName("weather") val weather: List<WeatherInfoResponse>
)

@Serializable
data class WeatherInfoResponse(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class TemperatureResponse(
    @SerialName("min") val min: Float,
    @SerialName("max") val max: Float,
)
