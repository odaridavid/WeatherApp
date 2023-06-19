package com.github.odaridavid.weatherapp.data.weather

import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.core.ErrorType
import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DailyWeather
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.HourlyWeather
import com.github.odaridavid.weatherapp.core.model.Temperature
import com.github.odaridavid.weatherapp.core.model.Units
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.model.WeatherInfo
import com.github.odaridavid.weatherapp.data.weather.local.entity.CurrentWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.CurrentWithWeatherInfo
import com.github.odaridavid.weatherapp.data.weather.local.entity.DailyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.DailyWithWeatherInfo
import com.github.odaridavid.weatherapp.data.weather.local.entity.HourlyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.HourlyWithWeatherInfo
import com.github.odaridavid.weatherapp.data.weather.local.entity.PopulatedWeather
import com.github.odaridavid.weatherapp.data.weather.local.entity.TemperatureEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherInfoResponseEntity
import java.io.IOException
import java.net.HttpURLConnection
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt


fun WeatherResponse.toCoreModel(unit: String): Weather = Weather(
    lat = lat,
    long = long,
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
fun WeatherInfoResponse.toCoreEntity(): WeatherInfoResponseEntity =
    WeatherInfoResponseEntity(
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
fun PopulatedWeather.toCoreEntity(unit: String): Weather =
    Weather(
        lat = weather.lat,
        long =weather.lon,
        current =current.asCoreEntity(unit = unit),
        hourly = hourly.map{it.asCoreModel(unit = unit)},
        daily = daily.map {
            it.asCoreModel(unit = unit) }
    )

fun CurrentWithWeatherInfo.asCoreEntity(unit: String): CurrentWeather =
    CurrentWeather(
        temperature = formatTemperatureValue(currentWeather.temp, unit),
        feelsLike = formatTemperatureValue(currentWeather.feelsLike, unit),
        weather = weather.map { it.asCoreModel() }
    )
fun DailyWithWeatherInfo.asCoreModel(unit: String): DailyWeather =
    DailyWeather(
        forecastedTime = getDate(dailyWeatherEntity.dt,"EEEE dd/M"),
        temperature = dailyWeatherEntity.temperature.asCoreModel(unit = unit),
        weather = weather.map { it.asCoreModel() }
    )

fun HourlyWithWeatherInfo.asCoreModel(unit: String): HourlyWeather =
    HourlyWeather(
        forecastedTime = getDate(hourlyWeatherEntity.dt,"HH:SS"),
        temperature = formatTemperatureValue(hourlyWeatherEntity.temperature, unit),
        weather = weather.map { it.asCoreModel() }
    )

fun WeatherInfoResponseEntity.asCoreModel(): WeatherInfo =
    WeatherInfo(
        id = id,
        main = main,
        description = description,
        icon = "${BuildConfig.OPEN_WEATHER_ICONS_URL}$icon@2x.png"
    )
fun TemperatureEntity.asCoreModel(unit: String): Temperature =
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

fun WeatherResponse.asCoreEntity():PopulatedWeather =
    PopulatedWeather(
        weather = WeatherEntity(
            lat = lat,
            lon = long,
        ),
        current = current.asCoreEntity(),
        daily = daily.map { it.asCoreEntity() },
        hourly = hourly.map { it.asCoreEntity() }
    )
fun CurrentWeatherResponse.asCoreEntity(): CurrentWithWeatherInfo =
    CurrentWithWeatherInfo(
        currentWeather = CurrentWeatherEntity(
            feelsLike = feelsLike,
            temp = temperature
        ),
        weather = weather.map { it.toCoreEntity() }
    )

fun WeatherResponse.toCurrentWeather(): CurrentWeatherEntity =
    CurrentWeatherEntity(
        feelsLike = current.feelsLike,
        temp = current.temperature
    )
fun WeatherResponse.toHourlyEntity():List<HourlyWeatherEntity> {
    val hourlyWeatherEntities = hourly.map { hourlyResponse ->
        HourlyWeatherEntity(
            dt = hourlyResponse.forecastedTime,
            temperature = hourlyResponse.temperature,
        )
    }
    return hourlyWeatherEntities
}

fun WeatherResponse.toDailyEntity():List<DailyWeatherEntity> {
    val dailyWeatherEntities = daily.map { dailyResponse ->
        DailyWeatherEntity(
            dt = dailyResponse.forecastedTime,
            temperature = dailyResponse.temperature.toTemperatureEntity(),
        )
    }
    return dailyWeatherEntities
}

fun WeatherResponse.toWeatherInfoResponse(): List<WeatherInfoResponseEntity> {
    val currentWeatherInfoList = current.weather.map { weatherInfoResponse ->
        WeatherInfoResponseEntity(
            id = weatherInfoResponse.id,
            main = weatherInfoResponse.main,
            description = weatherInfoResponse.description,
            icon = weatherInfoResponse.icon
        )
    }

    val hourlyWeatherInfoList = hourly.flatMap { hourlyWeatherResponse ->
        hourlyWeatherResponse.weather.map { weatherInfoResponse ->
            WeatherInfoResponseEntity(
                id = weatherInfoResponse.id,
                main = weatherInfoResponse.main,
                description = weatherInfoResponse.description,
                icon = weatherInfoResponse.icon
            )
        }
    }

    val dailyWeatherInfoList = daily.flatMap { dailyWeatherResponse ->
        dailyWeatherResponse.weather.map { weatherInfoResponse ->
            WeatherInfoResponseEntity(
                id = weatherInfoResponse.id,
                main = weatherInfoResponse.main,
                description = weatherInfoResponse.description,
                icon = weatherInfoResponse.icon
            )
        }
    }

    return currentWeatherInfoList + hourlyWeatherInfoList + dailyWeatherInfoList
}

fun DailyWeatherResponse.asCoreEntity(): DailyWithWeatherInfo =
    DailyWithWeatherInfo(
        dailyWeatherEntity = DailyWeatherEntity(
            dt = forecastedTime,
            temperature = temperature.toTemperatureEntity()
        ),
        weather = weather.map { it.toCoreEntity() }
    )

fun HourlyWeatherResponse.asCoreEntity(): HourlyWithWeatherInfo =
    HourlyWithWeatherInfo(
        hourlyWeatherEntity = HourlyWeatherEntity(
            dt = forecastedTime,
            temperature =temperature,
        ),
        weather = weather.map { it.toCoreEntity() }
    )

fun TemperatureResponse.toTemperatureEntity(): TemperatureEntity {
    return TemperatureEntity(
        min = min,
        max = max
    )
}

fun mapThrowableToErrorType(throwable: Throwable): ErrorType {
    val errorType = when (throwable) {
        is IOException -> ErrorType.IO_CONNECTION
        else -> ErrorType.GENERIC
    }
    return errorType
}

fun mapResponseCodeToErrorType(code: Int): ErrorType = when (code) {
    HttpURLConnection.HTTP_UNAUTHORIZED -> ErrorType.UNAUTHORIZED
    in 400..499 -> ErrorType.CLIENT
    in 500..600 -> ErrorType.SERVER
    else -> ErrorType.GENERIC
}