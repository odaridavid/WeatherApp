package com.github.odaridavid.weatherapp.data.weather.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.odaridavid.weatherapp.data.weather.local.entity.CurrentWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.DailyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.HourlyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.PopulatedWeather
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherInfoResponseEntity

@Dao
interface WeatherDao {

    @Transaction
    @Query("SELECT * FROM WeatherEntity WHERE lat = :latitude AND lon = :longitude")
    suspend fun getWeather(latitude: Double, longitude: Double): PopulatedWeather?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeather: CurrentWeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(hourly: List<HourlyWeatherEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWeather(daily: List<DailyWeatherEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfo(weatherResponse: List<WeatherInfoResponseEntity>)

}