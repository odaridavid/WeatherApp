package com.github.odaridavid.weatherapp.data.weather

import com.github.odaridavid.weatherapp.core.Result
import com.github.odaridavid.weatherapp.core.Result.Success
import com.github.odaridavid.weatherapp.core.Result.Error
import com.github.odaridavid.weatherapp.core.api.Logger
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.data.weather.remote.RemoteWeatherDataSource
import com.github.odaridavid.weatherapp.data.weather.remote.mapThrowableToErrorType
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val remoteWeatherDataSource: RemoteWeatherDataSource,
    private val logger: Logger
) : WeatherRepository {

    override suspend fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String
    ): Result<Weather> =
        try{
            val weather = remoteWeatherDataSource.fetchWeatherData(
                defaultLocation = defaultLocation,
                units = units,
                language = language
            )
            Success(weather) as Result<Weather>
        } catch (throwable: Throwable) {
            val errorType = mapThrowableToErrorType(throwable)
            logger.logException(throwable)
            Error(errorType)
        }


}
