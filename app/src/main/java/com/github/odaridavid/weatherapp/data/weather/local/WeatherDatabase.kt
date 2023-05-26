package com.github.odaridavid.weatherapp.data.weather.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.odaridavid.weatherapp.data.weather.local.converters.Converters
import com.github.odaridavid.weatherapp.data.weather.local.dao.WeatherDao
import com.github.odaridavid.weatherapp.data.weather.local.entity.DailyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.HourlyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherEntity

@Database(
    entities = [
        WeatherEntity::class,
        HourlyWeatherEntity::class,
        DailyWeatherEntity::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WeatherDatabase: RoomDatabase(){
    abstract fun weatherDao():WeatherDao
}