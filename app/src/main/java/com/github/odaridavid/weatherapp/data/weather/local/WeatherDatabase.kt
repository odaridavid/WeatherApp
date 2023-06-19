package com.github.odaridavid.weatherapp.data.weather.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.odaridavid.weatherapp.data.weather.local.dao.WeatherDao
import com.github.odaridavid.weatherapp.data.weather.local.entity.CurrentWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.DailyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.HourlyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherInfoResponseEntity

@Database(
    entities = [
        WeatherEntity::class,
        HourlyWeatherEntity::class,
        DailyWeatherEntity::class,
        WeatherInfoResponseEntity::class,
        CurrentWeatherEntity::class],
    version = 6,
    exportSchema = false
)
abstract class WeatherDatabase: RoomDatabase(){
    abstract fun weatherDao():WeatherDao
}