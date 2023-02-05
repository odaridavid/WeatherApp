package com.github.odaridavid.weatherapp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val openWeatherService: OpenWeatherService,
) : WeatherRepository {
    override fun fetchWeatherData(): Flow<Result<Weather>> = flow {
        // TODO Move logic to a preference screen
        // TODO Read location data after permission granted
        val response = openWeatherService.getWeatherData(
            longitude = 6.773456,
            latitude = 51.227741,
            excludedInfo = "${ExcludedData.MINUTELY.value},${ExcludedData.ALERTS.value}",
            units = Units.METRIC.value,
            language = SupportedLanguages.ENGLISH.languageValue,
            appid = BuildConfig.OPEN_WEATHER_API_KEY
        )
        if (response.isSuccessful && response.body() != null) {
            val weatherData = response.body()!!.toDomain()
            emit(Result.success(weatherData))
        } else {
            // TODO Log errors on a dashboard and handle different errors uniquely
            emit(Result.failure(Throwable("Unexpected Error Occurred : ${response.errorBody()}")))
        }
    }

}
