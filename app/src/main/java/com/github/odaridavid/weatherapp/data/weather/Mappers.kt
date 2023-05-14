package com.github.odaridavid.weatherapp.data.weather

import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DailyWeather
import com.github.odaridavid.weatherapp.core.model.HourlyWeather
import com.github.odaridavid.weatherapp.core.model.Temperature
import com.github.odaridavid.weatherapp.core.model.Units
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.model.WeatherInfo
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherEntity
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

fun WeatherResponse.asEntity(
    currentWeatherResponse: CurrentWeatherResponse,
    hourlyWeatherResponse: List<HourlyWeatherResponse>,
    dailyWeatherResponse: List<DailyWeatherResponse>
) = WeatherEntity(
    id = currentWeatherResponse.weather.first().id,
    dt = hourlyWeatherResponse.first().forecastedTime,
    feels_like = currentWeatherResponse.feelsLike,
    temp = currentWeatherResponse.temperature,
    temp_max = dailyWeatherResponse.first().temperature.max,
    temp_min = dailyWeatherResponse.first().temperature.min,
    description = currentWeatherResponse.weather.first().description,
    icon = currentWeatherResponse.weather.first().icon,
    main = currentWeatherResponse.weather.first().main,
)

fun WeatherEntity.asExternalModel(unit: String):Weather =
    Weather(
    current = CurrentWeather(
        temperature = formatTemperatureValue(temp, unit),
        feelsLike = formatTemperatureValue(feels_like, unit),
        weather = listOf(
            WeatherInfo(
                id = id,
                main = main,
                description = description,
                icon = icon,
            )
        )
    ) ,
    hourly = listOf(
       HourlyWeather(
           forecastedTime = getDate(dt,"HH:SS"),
           temperature = formatTemperatureValue(temp, unit),
           weather = listOf(
               WeatherInfo(
                   id = id,
                   main = main,
                   description = description,
                   icon = "${BuildConfig.OPEN_WEATHER_ICONS_URL}$icon@2x.png"
               )
           )
       )
    ),
    daily = listOf(
        DailyWeather(
            forecastedTime = getDate(dt,"EEEE dd/M"),
            temperature = Temperature(
                min = formatTemperatureValue(temp_min, unit),
                max = formatTemperatureValue(temp_max, unit),
            ),
            weather = listOf(
                WeatherInfo(
                    id = id,
                    main = main,
                    description = description,
                    icon = "${BuildConfig.OPEN_WEATHER_ICONS_URL}$icon@2x.png"
                )
            )
        )
    )
)