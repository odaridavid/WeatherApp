package com.github.odaridavid.weatherapp.data.weather.remote

import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.core.Result
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.Weather
import javax.inject.Inject

class DefaultRemoteWeatherDataSource @Inject constructor(
    private val openWeatherService: OpenWeatherService
) : RemoteWeatherDataSource {

    override suspend fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String
    ): Result<Weather> =
        try {

            val excludedData = "${ExcludedData.MINUTELY.value},${ExcludedData.ALERTS.value}"

            val response = openWeatherService.getWeatherData(
                longitude = defaultLocation.longitude,
                latitude = defaultLocation.latitude,
                excludedInfo = excludedData,
                units = units,
                language = getLanguageValue(language),
                appid = BuildConfig.OPEN_WEATHER_API_KEY
            )

            if (response.isSuccessful && response.body() != null) {
                val weatherData = response.body()!!.toCoreModel(unit = units)
                Result.Success(data = weatherData)
            } else {
                val throwable = mapResponseCodeToThrowable(response.code())
                throw throwable
            }
        } catch (e: Exception) {
            throw e
        }

    private fun getLanguageValue(language: String) =
        SupportedLanguage.values().first { it.languageName == language }.languageValue
}
