package com.github.odaridavid.weatherapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.github.odaridavid.weatherapp.worker.CustomWorkerFactory
import com.github.odaridavid.weatherapp.worker.WeatherUpdateScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WeatherApp : Application(),  Configuration.Provider{
    @Inject
    lateinit var workerFactory: CustomWorkerFactory

    @Inject
    lateinit var weatherUpdateScheduler: WeatherUpdateScheduler

    override fun onCreate() {
        super.onCreate()
        schedulePeriodicWeatherUpdates()
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder().setWorkerFactory(workerFactory).build()

    private fun schedulePeriodicWeatherUpdates() {
        weatherUpdateScheduler.schedulePeriodicWeatherUpdates()
    }
}
