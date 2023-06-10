package com.github.odaridavid.weatherapp.data.weather.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.github.odaridavid.weatherapp.data.weather.local.converters.Converters

data class PopulatedWeather(
    @Embedded
    val current: WeatherEntity,

    @Relation(parentColumn = "identity", entityColumn ="dt_column", entity = HourlyWeatherEntity::class)
    val hourly: List<HourlyWeatherEntity>,

    @Relation(parentColumn = "identity", entityColumn ="dt", entity = DailyWeatherEntity::class)
    val daily: List<DailyWeatherEntity>
){
    fun isDataValid(): Boolean {
        val currentTime = current.dt
        val fifteenMinutesAgo = currentTime - 15 * 60 * 1000

        val isCurrentValid = current.lastRefreshed >= fifteenMinutesAgo && current.isValid
        val areHourlyValid = hourly.all { it.lastRefreshed >= fifteenMinutesAgo && it.isValid }
        val areDailyValid = daily.all { it.lastRefreshed >= fifteenMinutesAgo && it.isValid }

        return isCurrentValid && areHourlyValid && areDailyValid
    }
}

@Entity(tableName = "weather_entity")
data class WeatherEntity(
    @PrimaryKey @ColumnInfo(name = "identity")
    val id: Int,
    val dt: Long,
    val feels_like: Float,
    val temp: Float,
    val temp_max: Float,
    val temp_min: Float,
    val description: String,
    val icon: String,
    val main: String,
    val lastRefreshed: Long = 0,
    val isValid: Boolean = false
)

@Entity(
    tableName = "hourly_weather",
    foreignKeys = [
        ForeignKey(entity = WeatherEntity::class,
            parentColumns = ["identity"],
            childColumns = ["dt_column"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index("dt_column")]
)
data class HourlyWeatherEntity(
    @PrimaryKey
    val id: Int= 0,
    @ColumnInfo(name = "dt_column")
    val dt: Long,
    val temperature: Float,
    @TypeConverters(Converters::class)
    val weather: List<WeatherInfoResponseEntity>,
    val lastRefreshed: Long = 0,
    val isValid: Boolean = false
)

@Entity(
    tableName = "daily_weather",
    foreignKeys = [
        ForeignKey(entity = WeatherEntity::class,
            parentColumns = ["identity"],
            childColumns = ["dt"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index("dt")]
)
data class DailyWeatherEntity(
    @PrimaryKey
    val id: Int= 0,
    @ColumnInfo(name = "dt")
    val dt: Long,
    @Embedded
    val temperature: TemperatureEntity,
    @TypeConverters(Converters::class)
    val weather: List<WeatherInfoResponseEntity>,
    val lastRefreshed: Long = 0,
    val isValid: Boolean = false
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