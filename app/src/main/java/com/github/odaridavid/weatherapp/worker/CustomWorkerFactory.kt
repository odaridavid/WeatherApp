package com.github.odaridavid.weatherapp.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.data.weather.DefaultRefreshWeatherUseCase
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(
    private val refreshWeatherUseCase: DefaultRefreshWeatherUseCase,
    private val settingsRepository: SettingsRepository
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = UpdateWeatherWorker(
         context = appContext,
        params = workerParameters,
        refreshWeatherUseCase = refreshWeatherUseCase,
        settingsRepository =settingsRepository
    )
}