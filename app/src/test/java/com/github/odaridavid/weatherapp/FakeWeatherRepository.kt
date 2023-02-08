package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeatherRepository : WeatherRepository {

    var isSuccessful = true
    override fun fetchWeatherData(
        defaultLocation: DefaultLocation,
        language: String,
        units: String
    ): Flow<Result<Weather>> = flow {
        if (isSuccessful){
            emit(Result.success(fakeSuccessMappedWeatherResponse))
        }else{
            emit(Result.failure(Throwable("An Error Occurred")))
        }
    }


}
