package com.github.odaridavid.weatherapp.data.weather.remote

import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.model.ClientException
import com.github.odaridavid.weatherapp.model.CurrentWeather
import com.github.odaridavid.weatherapp.model.DailyWeather
import com.github.odaridavid.weatherapp.model.ErrorType
import com.github.odaridavid.weatherapp.model.GenericException
import com.github.odaridavid.weatherapp.model.HourlyWeather
import com.github.odaridavid.weatherapp.model.ServerException
import com.github.odaridavid.weatherapp.model.Temperature
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units
import com.github.odaridavid.weatherapp.model.Weather
import com.github.odaridavid.weatherapp.model.WeatherInfo
import java.io.IOException
import java.net.HttpURLConnection
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

fun WeatherResponse.toCoreModel(unit: String, format: String): Weather = Weather(
    current = current?.toCoreModel(unit = unit),
    daily = daily?.map { it.toCoreModel(unit = unit) },
    hourly = hourly?.map { it.toCoreModel(unit = unit, format = format) }
)

fun CurrentWeatherResponse.toCoreModel(unit: String): CurrentWeather =
    CurrentWeather(
        temperature = formatTemperatureValue(temperature, unit),
        feelsLike = formatTemperatureValue(feelsLike, unit),
        weather = weather.map { it.toCoreModel() }
    )

fun DailyWeatherResponse.toCoreModel(unit: String): DailyWeather =
    DailyWeather(
        forecastedTime = getDate(forecastedTime, "EEEE dd/M"),
        temperature = temperature.toCoreModel(unit = unit),
        weather = weather.map { it.toCoreModel() }
    )

fun HourlyWeatherResponse.toCoreModel(unit: String, format: String): HourlyWeather {
    val formatPattern = when (format) {
        TimeFormat.TWELVE_HOUR.value -> "h:mm a"
        TimeFormat.TWENTY_FOUR_HOUR.value -> "HH:SS"
        else -> "HH:SS"
    }
    return HourlyWeather(
        forecastedTime = getDate(forecastedTime, formatPattern),
        temperature = formatTemperatureValue(temperature, unit),
        weather = weather.map { it.toCoreModel() }
    )
}

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
    // TODO use locale from supported languages
    val sdf = SimpleDateFormat(formatPattern, Locale.ENGLISH)
    val dateFormat = Date(utcInMillis * 1000)
    return sdf.format(dateFormat)
}

fun mapResponseCodeToThrowable(code: Int): Throwable = when (code) {
    HttpURLConnection.HTTP_BAD_REQUEST -> ClientException("Bad request : $code: Check request parameters")
    HttpURLConnection.HTTP_UNAUTHORIZED -> ClientException("Unauthorized access : $code: Check API Token")
    HttpURLConnection.HTTP_NOT_FOUND -> ClientException("Resource not found : $code: Check parameters")
    TOO_MANY_REQUESTS -> ClientException("Too many requests : $code: Rate limit exceeded")
    in CLIENT_ERRORS -> ClientException("Client error : $code")
    in SERVER_ERRORS -> ServerException("Server error : $code")
    else -> GenericException("Generic error : $code")
}

fun mapThrowableToErrorType(throwable: Throwable): ErrorType {
    val errorType = when (throwable) {
        is IOException -> ErrorType.IO_CONNECTION
        is ClientException -> ErrorType.CLIENT
        is ServerException -> ErrorType.SERVER
        else -> ErrorType.GENERIC
    }
    return errorType
}

private val SERVER_ERRORS = 500..600
private val CLIENT_ERRORS = 400..499
private const val TOO_MANY_REQUESTS = 429
