package com.github.odaridavid.weatherapp.di

import com.github.odaridavid.weatherapp.core.api.Logger
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.data.weather.DefaultWeatherRepository
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository
import com.github.odaridavid.weatherapp.data.weather.FirebaseLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindWeatherRepository(weatherRepository: DefaultWeatherRepository): WeatherRepository

    @Binds
    fun bindSettingsRepository(settingsRepository: DefaultSettingsRepository): SettingsRepository

    @Binds
    fun bindFirebaseLogger(logger: FirebaseLogger): Logger

}
