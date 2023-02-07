package com.github.odaridavid.weatherapp.data.weather

import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DailyWeather
import com.github.odaridavid.weatherapp.core.model.FeelsLike
import com.github.odaridavid.weatherapp.core.model.HourlyWeather
import com.github.odaridavid.weatherapp.core.model.Temperature
import com.github.odaridavid.weatherapp.core.model.Units
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.model.WeatherInfo
import kotlin.math.roundToInt

fun WeatherResponse.toCoreModel(unit: String): Weather = Weather(
    current = current.toCoreModel(unit = unit),
    daily = daily.map { it.toCoreModel(unit = unit) },
    hourly = hourly.map { it.toCoreModel(unit = unit) }
)

fun CurrentWeatherResponse.toCoreModel(unit: String): CurrentWeather =
    CurrentWeather(
        sunriseTime = sunriseTime,
        sunsetTime = sunsetTime,
        temperature = formatTemperatureValue(temperature, unit),
        feelsLike = formatTemperatureValue(feelsLike, unit),
        weather = weather.map { it.toCoreModel() }
    )

// TODO Format UTC dates to day
fun DailyWeatherResponse.toCoreModel(unit: String): DailyWeather =
    DailyWeather(
        forecastedTime = forecastedTime,
        sunriseTime = sunriseTime,
        sunsetTime = sunsetTime,
        temperature = temperature.toCoreModel(unit = unit),
        feelsLike = feelsLike.toCoreModel(unit = unit),
        weather = weather.map { it.toCoreModel() }

    )
// TODO Format UTC dates to hr
fun HourlyWeatherResponse.toCoreModel(unit: String): HourlyWeather =
    HourlyWeather(
        forecastedTime = forecastedTime,
        temperature = formatTemperatureValue(temperature, unit),
        feelsLike = formatTemperatureValue(feelsLike, unit),
        weather = weather.map { it.toCoreModel() }
    )

fun WeatherInfoResponse.toCoreModel(): WeatherInfo =
    WeatherInfo(
        id = id,
        main = main,
        description = description,
        icon = "${BuildConfig.OPEN_WEATHER_ICONS_URL}$icon@2x.png"
    )

fun FeelsLikeResponse.toCoreModel(unit: String): FeelsLike =
    FeelsLike(
        morning = formatTemperatureValue(morning, unit),
        afternoon = formatTemperatureValue(afternoon, unit),
        evening = formatTemperatureValue(evening, unit),
        night = formatTemperatureValue(night, unit),
    )

fun TemperatureResponse.toCoreModel(unit: String): Temperature =
    Temperature(
        morning = formatTemperatureValue(morning, unit),
        afternoon = formatTemperatureValue(afternoon, unit),
        evening = formatTemperatureValue(evening, unit),
        night = formatTemperatureValue(night, unit),
        min = formatTemperatureValue(min, unit),
        max = formatTemperatureValue(max, unit)
    )

private fun formatTemperatureValue(temperature: Float, unit: String): String =
    "${temperature.roundToInt()}${getUnitSymbols(unit = unit)}"

private fun getUnitSymbols(unit: String) = when (unit) {
    Units.METRIC.value -> "°C"
    Units.IMPERIAL.value -> "°F"
    Units.STANDARD.value -> "°K"
    else -> "N/A"
}
