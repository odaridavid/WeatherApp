package com.github.odaridavid.weatherapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.data.weather.DefaultRefreshWeatherUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class UpdateWeatherWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val refreshWeatherUseCase: DefaultRefreshWeatherUseCase,
    private val settingsRepository: SettingsRepository
): CoroutineWorker(context, params){

    override suspend fun doWork(): Result {
        return try {
            val defaultLocation = settingsRepository.getDefaultLocation().first()
            val language = settingsRepository.getLanguage().first()
            val units = settingsRepository.getUnits().first()
            refreshWeatherUseCase.invoke(
                defaultLocation = defaultLocation,
                language = language,
                units = units)
            Result.success()
        } catch(e: Error) {
            Result.retry()
        }
    }
}