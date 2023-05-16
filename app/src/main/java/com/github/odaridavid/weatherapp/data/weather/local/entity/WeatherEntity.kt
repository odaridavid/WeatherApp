package com.github.odaridavid.weatherapp.data.weather.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.github.odaridavid.weatherapp.data.weather.local.converters.Converters

@Entity(tableName = "weather")
data class WeatherEntity(
    @ColumnInfo(name = "hourly")
    @TypeConverters(Converters::class)
    val hourly: List<HourlyWeatherEntity> = emptyList(),

    @ColumnInfo(name = "daily")
    @TypeConverters(Converters::class)
    val daily: List<DailyWeatherEntity> = emptyList(),

    val dt: Long,
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val feels_like: Float,
    val temp: Float,
    val temp_max: Float,
    val temp_min: Float,
    val description: String,
    val icon: String,
    val main: String,
    val lastRefreshed: Long = 0,
    val isValid: Boolean = false
){
    fun isDataValid(): Boolean {
        val currentTime = System.currentTimeMillis()
        val fifteenMinutesAgo = currentTime - 15 * 60 * 1000
        return lastRefreshed >= fifteenMinutesAgo && isValid
    }
}

data class HourlyWeatherEntity(
    val dt: Long,
    val temperature: Float,
    val weather: List<WeatherInfoResponseEntity>
)

data class DailyWeatherEntity(
    val dt: Long,
    val temperature: TemperatureEntity,
    val weather: List<WeatherInfoResponseEntity>
)

data class TemperatureEntity(
    val min: Float,
    val max: Float
)
data class WeatherInfoResponseEntity(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)