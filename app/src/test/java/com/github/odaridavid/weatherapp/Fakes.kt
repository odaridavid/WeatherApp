package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.model.WeatherInfo
import com.github.odaridavid.weatherapp.data.weather.CurrentWeatherResponse
import com.github.odaridavid.weatherapp.data.weather.WeatherInfoResponse
import com.github.odaridavid.weatherapp.data.weather.WeatherResponse
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherEntity

val fakeSuccessWeatherResponse = WeatherResponse(
    current = CurrentWeatherResponse(
        temperature = 3.0f,
        feelsLike = 2.0f,
        weather = listOf(WeatherInfoResponse(
            id = 1,
            main = "fake",
            description = "fake",
            icon = "fake"
        ))
    ),
    hourly = emptyList(),
    daily = emptyList()
)

val fakeSuccessMappedWeatherResponse = Weather(
    current = CurrentWeather(
        temperature = "3°C",
        feelsLike = "2°C",
        weather = listOf(WeatherInfo(
            id = 0,
            main = "fake",
            description = "fake",
            icon = "http://openweathermap.org/img/wn/fake@2x.png"
        ))
    ),
    hourly = emptyList(),
    daily = emptyList()
)
val fakeSuccessMappedEntityResponse = WeatherEntity(
    hourly = emptyList(),
    daily = emptyList(),
    dt =  10L,
    id = 1,
    feels_like = 2.0f,
    temp = 3.0f,
    temp_max = 0.0f,
    temp_min = 0.0f,
    description = "fake",
    icon = "fake",
    main = "fake",
)
