package com.github.odaridavid.weatherapp.worker

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherUpdateScheduler @Inject constructor(
    private val workManager: WorkManager
) {
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    fun schedulePeriodicWeatherUpdates() {
        val refreshWeatherRequest = PeriodicWorkRequestBuilder<UpdateWeatherWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        workManager.enqueue(refreshWeatherRequest)
    }
}
