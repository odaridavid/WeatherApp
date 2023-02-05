package com.github.odaridavid.weatherapp

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
