package com.github.odaridavid.weatherapp

// TODO Check on domain naming
fun WeatherResponse.toDomain(): Weather = Weather(
    current = current.toDomain(),
    daily = daily.map { it.toDomain() },
    hourly = hourly.map { it.toDomain() }
)

// TODO Populate and format data
fun CurrentWeatherResponse.toDomain(): CurrentWeather =
    CurrentWeather(
        sunriseTime = 0, sunsetTime = 0, temperature = 0.0f, feelsLike = 0.0f, weather = listOf()
    )

fun DailyWeatherResponse.toDomain(): DailyWeather =
    DailyWeather(
        forecastedTime = 0, sunriseTime = 0, sunsetTime = 0, temperature = Temperature(
            morning = 0.0f,
            afternoon = 0.0f,
            evening = 0.0f,
            night = 0.0f,
            min = 0.0f,
            max = 0.0f
        ), feelsLike = FeelsLike(
            morning = 0.0f,
            afternoon = 0.0f,
            evening = 0.0f,
            night = 0.0f
        ), weather = listOf()

    )

fun HourlyWeatherResponse.toDomain(): HourlyWeather =
    HourlyWeather(
        forecastedTime = 0, temperature = 0.0f, feelsLike = 0.0f, weather = listOf()
    )
