package com.github.odaridavid.weatherapp.data.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current") val current: CurrentWeatherResponse,
    @SerialName("hourly") val hourly: List<HourlyWeatherResponse>,
    @SerialName("daily") val daily: List<DailyWeatherResponse>
)

@Serializable
data class CurrentWeatherResponse(
    @SerialName("sunrise") val sunriseTime: Long,
    @SerialName("sunset") val sunsetTime: Long,
    @SerialName("temp") val temperature: Float,
    @SerialName("feels_like") val feelsLike: Float,
    @SerialName("weather") val weather: List<WeatherInfoResponse>
)

@Serializable
data class HourlyWeatherResponse(
    @SerialName("dt") val forecastedTime: Long,
    @SerialName("temp") val temperature: Float,
    @SerialName("feels_like") val feelsLike: Float,
    @SerialName("weather") val weather: List<WeatherInfoResponse>
)

@Serializable
data class DailyWeatherResponse(
    @SerialName("dt") val forecastedTime: Long,
    @SerialName("sunrise") val sunriseTime: Long,
    @SerialName("sunset") val sunsetTime: Long,
    @SerialName("temp") val temperature: TemperatureResponse,
    @SerialName("feels_like") val feelsLike: FeelsLikeResponse,
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
    @SerialName("morn") val morning: Float,
    @SerialName("day") val afternoon: Float,
    @SerialName("eve") val evening: Float,
    @SerialName("night") val night: Float,
    @SerialName("min") val min: Float,
    @SerialName("max") val max: Float,
)

@Serializable
data class FeelsLikeResponse(
    @SerialName("morn") val morning: Float,
    @SerialName("day") val afternoon: Float,
    @SerialName("eve") val evening: Float,
    @SerialName("night") val night: Float,
)

