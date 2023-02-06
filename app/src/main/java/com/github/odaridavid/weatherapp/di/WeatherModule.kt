package com.github.odaridavid.weatherapp.di

import com.github.odaridavid.weatherapp.data.DefaultWeatherRepository
import com.github.odaridavid.weatherapp.core.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface WeatherModule {

    @Binds
    fun bindWeatherRepository(weatherRepository: DefaultWeatherRepository): WeatherRepository

}
