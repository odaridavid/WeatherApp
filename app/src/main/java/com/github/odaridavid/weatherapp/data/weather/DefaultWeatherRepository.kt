package com.github.odaridavid.weatherapp.data.weather

import com.github.odaridavid.weatherapp.core.Result
import com.github.odaridavid.weatherapp.core.Result.Error
import com.github.odaridavid.weatherapp.core.api.Logger
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.data.weather.remote.RemoteWeatherDataSource
import com.github.odaridavid.weatherapp.data.weather.remote.mapThrowableToErrorType
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val remoteWeatherDataSource: RemoteWeatherDataSource,
    private val logger: Logger,
    private val settingsRepository: SettingsRepository,
) : WeatherRepository {

    override suspend fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String,
    ): Result<Weather> =
        try {
            val format = settingsRepository.getFormat().first()

            val excludedData = settingsRepository.getExcludedData().first()

            remoteWeatherDataSource.fetchWeatherData(
                defaultLocation = defaultLocation,
                units = units,
                language = language,
                format = format,
                excludedData = excludedData
            )
        } catch (throwable: Throwable) {
            val errorType = mapThrowableToErrorType(throwable)
            logger.logException(throwable)
            Error(errorType)
        }


}
