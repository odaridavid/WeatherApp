package com.github.odaridavid.weatherapp.data.weather

import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.core.ErrorType
import com.github.odaridavid.weatherapp.core.Result
import com.github.odaridavid.weatherapp.core.api.Logger
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val openWeatherService: OpenWeatherService,
    private val logger: Logger
) : WeatherRepository {

    override fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String
    ): Flow<Result<Weather>> = flow {

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
            emit(Result.Success(data = weatherData))
        } else {
            val errorType = mapResponseCodeToErrorType(response.code())
            emit(Result.Error(errorType = errorType))
        }
    }.catch { throwable: Throwable ->
        val errorType = mapThrowableToErrorType(throwable)
        logger.logException(throwable)
        emit(Result.Error(errorType))
    }

    private fun getLanguageValue(language: String) =
        SupportedLanguage.values().first { it.languageName == language }.languageValue

}
