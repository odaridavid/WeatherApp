package com.github.odaridavid.weatherapp.data.weather

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.data.weather.local.dao.WeatherDao
import com.github.odaridavid.weatherapp.worker.UpdateWeatherWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val openWeatherService: OpenWeatherService,
    private val weatherDao: WeatherDao,
    @ApplicationContext private val context: Context
) : WeatherRepository {
    override fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String
    ): Flow<ApiResult<Weather>> = flow {
        val cachedWeather = weatherDao.getWeather()
        if (cachedWeather != null && cachedWeather.isDataValid()) {
            val weatherData = cachedWeather.asExternalModel(unit = units)
            emit(ApiResult.Success(data = weatherData))
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
            val currentWeatherResponse = response.current
            val hourlyWeatherResponse = response.hourly
            val dailyWeatherResponse = response.daily
            val weatherEntity = response.asEntity(
                currentWeatherResponse = currentWeatherResponse,
                hourlyWeatherResponse = hourlyWeatherResponse,
                dailyWeatherResponse = dailyWeatherResponse
            )
            weatherDao.insertCurrentWeather(weatherEntity)
            val weatherData = weatherEntity.asExternalModel(units)
            emit(ApiResult.Success(data = weatherData))
        } else {
            val errorMessage = mapResponseCodeToErrorMessage(apiResponse.code())
            Timber.e("Error Message $errorMessage")
            emit(ApiResult.Error(errorMessage))
        }
    }.catch { throwable ->
        val errorMessage = when (throwable) {
            is IOException -> R.string.error_connection
            else -> R.string.error_generic
        }
        emit(ApiResult.Error(errorMessage))
    }.onCompletion {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val refreshWeatherRequest = OneTimeWorkRequestBuilder<UpdateWeatherWorker>()
            .setConstraints(constraints)
            .setInitialDelay(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueue(refreshWeatherRequest)
    }

    private fun mapResponseCodeToErrorMessage(code: Int): Int = when (code) {
        HTTP_UNAUTHORIZED -> R.string.error_unauthorized
        in 400..499 -> R.string.error_client
        in 500..600 -> R.string.error_server
        else -> R.string.error_generic
    }

    private fun getLanguageValue(language: String) =
        SupportedLanguage.values().first { it.languageName == language }.languageValue

}
