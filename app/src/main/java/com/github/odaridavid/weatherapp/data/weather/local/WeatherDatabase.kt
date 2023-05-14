package com.github.odaridavid.weatherapp.data.weather.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.odaridavid.weatherapp.data.weather.local.dao.WeatherDao
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase: RoomDatabase(){
    abstract fun weatherDao():WeatherDao
}