package com.github.odaridavid.weatherapp.di

import android.content.Context
import com.github.odaridavid.weatherapp.data.weather.DefaultWeatherRepository
import com.github.odaridavid.weatherapp.data.weather.RefreshWeatherUseCase
import com.github.odaridavid.weatherapp.util.NotificationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideRefreshWeatherUseCase(
        repository: DefaultWeatherRepository,
        notificationUtil: NotificationUtil,
        context: Context
    ): RefreshWeatherUseCase {
        return RefreshWeatherUseCase(
            weatherRepository = repository,
            notificationUtil = notificationUtil,
            context = context
        )
    }
}