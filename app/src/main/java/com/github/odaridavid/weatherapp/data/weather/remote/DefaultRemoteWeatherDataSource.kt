package com.github.odaridavid.weatherapp.data.weather.remote

import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.model.DefaultLocation
import com.github.odaridavid.weatherapp.model.Result
import com.github.odaridavid.weatherapp.model.Weather
import javax.inject.Inject

class DefaultRemoteWeatherDataSource @Inject constructor(
    private val openWeatherService: OpenWeatherService,
) : RemoteWeatherDataSource {

    override suspend fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String,
        format: String,
        excludedData: String,
    ): Result<Weather> =
        try {

            val response = openWeatherService.getWeatherData(
                longitude = defaultLocation.longitude,
                latitude = defaultLocation.latitude,
                excludedInfo = excludedData,
                units = units,
                language = language,
                appid = BuildConfig.OPEN_WEATHER_API_KEY,
            )

            if (response.isSuccessful && response.body() != null) {
                val weatherData = response.body()!!.toCoreModel(unit = units, format = format)
                Result.Success(data = weatherData)
            } else {
                val throwable = mapResponseCodeToThrowable(response.code())
                throw throwable
            }
        } catch (e: Exception) {
            throw e
        }
}
