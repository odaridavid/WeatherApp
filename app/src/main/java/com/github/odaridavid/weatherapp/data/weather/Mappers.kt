package com.github.odaridavid.weatherapp.data.weather

import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DailyWeather
import com.github.odaridavid.weatherapp.core.model.FeelsLike
import com.github.odaridavid.weatherapp.core.model.HourlyWeather
import com.github.odaridavid.weatherapp.core.model.Temperature
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.model.WeatherInfo

fun WeatherResponse.toCoreModel(): Weather = Weather(
    current = current.toCoreModel(),
    daily = daily.map { it.toCoreModel() },
    hourly = hourly.map { it.toCoreModel() }
)

// TODO Format date and temperature data
fun CurrentWeatherResponse.toCoreModel(): CurrentWeather =
    CurrentWeather(
        sunriseTime = sunriseTime,
        sunsetTime = sunsetTime,
        temperature = temperature,
        feelsLike = feelsLike,
        weather = weather.map { it.toCoreModel() }
    )

fun DailyWeatherResponse.toCoreModel(): DailyWeather =
    DailyWeather(
        forecastedTime = forecastedTime,
        sunriseTime = sunriseTime,
        sunsetTime = sunsetTime,
        temperature = temperature.toCoreModel(),
        feelsLike = feelsLike.toCoreModel(),
        weather = weather.map { it.toCoreModel() }

    )

fun HourlyWeatherResponse.toCoreModel(): HourlyWeather =
    HourlyWeather(
        forecastedTime = forecastedTime,
        temperature = temperature,
        feelsLike = feelsLike,
        weather = weather.map { it.toCoreModel() }
    )

fun WeatherInfoResponse.toCoreModel(): WeatherInfo =
    WeatherInfo(
        id = id,
        main = main,
        description = description,
        icon = "${BuildConfig.OPEN_WEATHER_ICONS_URL}$icon@2x.png"
    )

fun FeelsLikeResponse.toCoreModel(): FeelsLike =
    FeelsLike(morning = morning, afternoon = afternoon, evening = evening, night = night)

fun TemperatureResponse.toCoreModel(): Temperature =
    Temperature(
        morning = morning,
        afternoon = afternoon,
        evening = evening,
        night = night,
        min = min,
        max = max
    )
