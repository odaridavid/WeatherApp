package com.github.odaridavid.weatherapp.data.weather

import com.github.odaridavid.weatherapp.api.Logger
import com.github.odaridavid.weatherapp.api.SettingsRepository
import com.github.odaridavid.weatherapp.api.WeatherRepository
import com.github.odaridavid.weatherapp.data.weather.remote.RemoteWeatherDataSource
import com.github.odaridavid.weatherapp.data.weather.remote.mapThrowableToErrorType
import com.github.odaridavid.weatherapp.model.DefaultLocation
import com.github.odaridavid.weatherapp.model.ExcludedData
import com.github.odaridavid.weatherapp.model.Result
import com.github.odaridavid.weatherapp.model.Result.Error
import com.github.odaridavid.weatherapp.model.Weather
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
            val format = settingsRepository.getFormat().first().value

            val excludedData = settingsRepository
                .getExcludedData()
                .first()
                .replace(ExcludedData.NONE.value, "")

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
