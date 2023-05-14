package com.github.odaridavid.weatherapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.Units
import com.github.odaridavid.weatherapp.data.weather.DefaultWeatherRepository
import com.github.odaridavid.weatherapp.util.NotificationUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class UpdateWeatherWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val weatherRepository: DefaultWeatherRepository,
    private val notificationUtil: NotificationUtil
): CoroutineWorker(context, params){

    override suspend fun doWork(): Result {
        return try {
            val defaultLocation = getDefaultLocation()
            val language = getDefaultLanguage()
            val units = getDefaultUnits()
            weatherRepository.fetchWeatherData(
                defaultLocation = defaultLocation,
                language = language,
                units = units
            )
            notificationUtil.makeNotification("Weather Updated")
            Result.success()
        } catch(e: Error) {
            Result.retry()
        }
    }

    private fun getDefaultLocation(): DefaultLocation {
       return DefaultLocation(
           latitude = 12.11,
           longitude = 98.55,
       )
    }

    private fun getDefaultLanguage(): String {
        return SupportedLanguage.values().toString()
    }

    private fun getDefaultUnits(): String {
        return Units.METRIC.value
    }
}