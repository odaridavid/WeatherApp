package com.github.odaridavid.weatherapp.data.weather

import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.core.Result
import com.github.odaridavid.weatherapp.core.api.Logger
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.data.weather.local.dao.WeatherDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val openWeatherService: OpenWeatherService,
    private val weatherDao: WeatherDao,
    private val logger: Logger
) : WeatherRepository {
    override fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String
    ): Flow<Result<Weather>> = flow {
        val cachedWeather = weatherDao.getWeather(
            latitude = defaultLocation.latitude,
            longitude = defaultLocation.longitude
        )
        if (cachedWeather != null && cachedWeather.isDataValid()) {
            val weatherData = cachedWeather.toCoreEntity(unit = units)
            emit(Result.Success(data = weatherData))
            return@flow
        }
        val excludedData = "${ExcludedData.MINUTELY.value},${ExcludedData.ALERTS.value}"

        val apiResponse = openWeatherService.getWeatherData(
            longitude = defaultLocation.longitude,
            latitude = defaultLocation.latitude,
            excludedInfo = excludedData,
            units = units,
            language = getLanguageValue(language),
            appid = BuildConfig.OPEN_WEATHER_API_KEY
        )
        val response = apiResponse.body()
        if (apiResponse.isSuccessful && response != null) {
            val currentWeather = apiResponse.body()!!.toCurrentWeather()
            val hourlyWeather = apiResponse.body()!!.toHourlyEntity()
            val dailyWeather = apiResponse.body()!!.toDailyEntity()
            val weatherInfo = apiResponse.body()!!.toWeatherInfoResponse()
            val weatherEntity = apiResponse.body()!!.asCoreEntity()
            weatherDao.insertCurrentWeather(currentWeather)
            weatherDao.insertHourlyWeather(hourlyWeather)
            weatherDao.insertDailyWeather(dailyWeather)
            weatherDao.insertWeatherInfo(weatherInfo)
            val result = weatherEntity.toCoreEntity(unit = units)
            emit(Result.Success(data = result))
        } else {
            val errorType = mapResponseCodeToErrorType(apiResponse.code())
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
