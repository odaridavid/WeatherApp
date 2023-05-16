package com.github.odaridavid.weatherapp.data.weather

import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DailyWeather
import com.github.odaridavid.weatherapp.core.model.HourlyWeather
import com.github.odaridavid.weatherapp.core.model.Temperature
import com.github.odaridavid.weatherapp.core.model.Units
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.model.WeatherInfo
import com.github.odaridavid.weatherapp.data.weather.local.entity.DailyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.HourlyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.TemperatureEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherInfoResponseEntity
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt

fun WeatherResponse.toCoreModel(unit: String): Weather = Weather(
    current = current.toCoreModel(unit = unit),
    daily = daily.map { it.toCoreModel(unit = unit) },
    hourly = hourly.map { it.toCoreModel(unit = unit) }
)

fun CurrentWeatherResponse.toCoreModel(unit: String): CurrentWeather =
    CurrentWeather(
        temperature = formatTemperatureValue(temperature, unit),
        feelsLike = formatTemperatureValue(feelsLike, unit),
        weather = weather.map { it.toCoreModel() }
    )

fun DailyWeatherResponse.toCoreModel(unit: String): DailyWeather =
    DailyWeather(
        forecastedTime = getDate(forecastedTime,"EEEE dd/M"),
        temperature = temperature.toCoreModel(unit = unit),
        weather = weather.map { it.toCoreModel() }
    )

fun HourlyWeatherResponse.toCoreModel(unit: String): HourlyWeather =
    HourlyWeather(
        forecastedTime = getDate(forecastedTime,"HH:SS"),
        temperature = formatTemperatureValue(temperature, unit),
        weather = weather.map { it.toCoreModel() }
    )

fun WeatherInfoResponse.toCoreModel(): WeatherInfo =
    WeatherInfo(
        id = id,
        main = main,
        description = description,
        icon = "${BuildConfig.OPEN_WEATHER_ICONS_URL}$icon@2x.png"
    )

fun TemperatureResponse.toCoreModel(unit: String): Temperature =
    Temperature(
        min = formatTemperatureValue(min, unit),
        max = formatTemperatureValue(max, unit)
    )
fun WeatherEntity.toCoreEntity(unit: String): Weather =
    Weather(
        current = toCurrentWeather(unit = unit),
        hourly = hourly.map { it.toCoreEntity(unit = unit) },
        daily = daily.map { it.toCoreEntity(unit = unit) }
    )
private fun WeatherEntity.toCurrentWeather(unit: String): CurrentWeather =
    CurrentWeather(
        temperature = formatTemperatureValue(temp, unit),
        feelsLike = formatTemperatureValue(feels_like, unit),
        weather = listOf(toWeatherInfo())
    )
private fun WeatherEntity.toWeatherInfo(): WeatherInfo =
    WeatherInfo(
        id = id,
        main = main,
        description = description,
        icon = "${BuildConfig.OPEN_WEATHER_ICONS_URL}$icon@2x.png"
    )
fun DailyWeatherEntity.toCoreEntity(unit: String): DailyWeather =
    DailyWeather(
        forecastedTime = getDate(dt,"EEEE dd/M"),
        temperature = temperature.toCoreEntity(unit = unit),
        weather = weather.map { it.toCoreEntity() }
    )

fun HourlyWeatherEntity.toCoreEntity(unit: String): HourlyWeather =
    HourlyWeather(
        forecastedTime = getDate(dt,"HH:SS"),
        temperature = formatTemperatureValue(temperature, unit),
        weather = weather.map { it.toCoreEntity() }
    )

fun WeatherInfoResponseEntity.toCoreEntity(): WeatherInfo =
    WeatherInfo(
        id = id,
        main = main,
        description = description,
        icon = "${BuildConfig.OPEN_WEATHER_ICONS_URL}$icon@2x.png"
    )

fun TemperatureEntity.toCoreEntity(unit: String): Temperature =
    Temperature(
        min = formatTemperatureValue(min, unit),
        max = formatTemperatureValue(max, unit)
    )

private fun formatTemperatureValue(temperature: Float, unit: String): String =
    "${temperature.roundToInt()}${getUnitSymbols(unit = unit)}"

private fun getUnitSymbols(unit: String) = when (unit) {
    Units.METRIC.value -> Units.METRIC.tempLabel
    Units.IMPERIAL.value -> Units.IMPERIAL.tempLabel
    Units.STANDARD.value -> Units.STANDARD.tempLabel
    else -> "N/A"
}

private fun getDate(utcInMillis: Long, formatPattern: String): String {
    val sdf = SimpleDateFormat(formatPattern)
    val dateFormat = Date(utcInMillis * 1000)
    return sdf.format(dateFormat)
}

fun WeatherResponse.toWeatherEntity(): WeatherEntity {
    val currentTime = System.currentTimeMillis()
    val currentWeatherInfo = current.weather.first()

    val hourlyWeatherEntities = hourly.map { hourlyResponse ->
        HourlyWeatherEntity(
            dt = hourlyResponse.forecastedTime,
            temperature = hourlyResponse.temperature,
            weather = hourlyResponse.weather.map { it.toWeatherInfoResponse() }
        )
    }

    val dailyWeatherEntities = daily.map { dailyResponse ->
        DailyWeatherEntity(
            dt = dailyResponse.forecastedTime,
            temperature = dailyResponse.temperature.toTemperatureEntity(),
            weather = dailyResponse.weather.map { it.toWeatherInfoResponse() }
        )
    }

    return WeatherEntity(
        dt = currentTime,
        id = 0,
        feels_like = current.feelsLike,
        temp = current.temperature,
        temp_max = current.temperature,
        temp_min = current.temperature,
        description = currentWeatherInfo.description,
        icon = currentWeatherInfo.icon,
        main = currentWeatherInfo.main,
        lastRefreshed = currentTime,
        isValid = true,
        hourly = hourlyWeatherEntities,
        daily = dailyWeatherEntities
    )
}

private fun WeatherInfoResponse.toWeatherInfoResponse(): WeatherInfoResponseEntity {
    return WeatherInfoResponseEntity(
        id = id,
        main = main,
        description = description,
        icon = icon
    )
}

fun TemperatureResponse.toTemperatureEntity(): TemperatureEntity {
    return TemperatureEntity(
        min = min,
        max = max
    )
}