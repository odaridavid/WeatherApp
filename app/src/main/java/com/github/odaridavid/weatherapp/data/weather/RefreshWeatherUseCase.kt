package com.github.odaridavid.weatherapp.data.weather

import android.content.Context
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.Weather
import com.github.odaridavid.weatherapp.util.NotificationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val notificationUtil: NotificationUtil,
    private val context: Context
) {
    operator fun invoke(
        defaultLocation: DefaultLocation,
        language: String,
        units: String
    ): Flow<ApiResult<Weather>>  = flow{
        weatherRepository.fetchWeatherData(
            defaultLocation = defaultLocation,
            language = language,
            units =units
        ).collect{ result->
            when (result) {
                is ApiResult.Success -> {
                    notificationUtil.makeNotification(context.getString(R.string.weather_updates))
                }
                is ApiResult.Error -> {
                    R.string.error_generic
                }
            }
        }

    }
}






