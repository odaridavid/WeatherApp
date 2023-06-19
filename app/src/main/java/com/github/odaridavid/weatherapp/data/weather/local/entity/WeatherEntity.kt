package com.github.odaridavid.weatherapp.data.weather.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class PopulatedWeather(
    @Embedded val weather: WeatherEntity,
    @Relation(
        entity = CurrentWeatherEntity::class,
        parentColumn = "weatherId",
        entityColumn = "currentId"
    )
    val current: CurrentWithWeatherInfo,
    @Relation(
        entity = HourlyWeatherEntity::class,
        parentColumn = "weatherId",
        entityColumn = "dt"
    )
    val hourly: List<HourlyWithWeatherInfo >,
    @Relation(
        entity = DailyWeatherEntity::class,
        parentColumn = "weatherId",
        entityColumn = "dt"
    )
    val daily: List<DailyWithWeatherInfo >
){
    fun isDataValid(): Boolean {
        val currentTime = System.currentTimeMillis()
        val fifteenMinutesAgo = currentTime - 15 * 60 * 1000

        val isCurrentValid = current.currentWeather.lastRefreshed >= fifteenMinutesAgo && current.currentWeather.isValid
        val areHourlyValid = hourly.all { it.hourlyWeatherEntity.lastRefreshed >= fifteenMinutesAgo && it.hourlyWeatherEntity.isValid }
        val areDailyValid = daily.all { it.dailyWeatherEntity.lastRefreshed >= fifteenMinutesAgo && it.dailyWeatherEntity.isValid }

        return isCurrentValid && areHourlyValid && areDailyValid
    }
}

@Entity
data class WeatherEntity(
    @PrimaryKey val weatherId: Int = 0,
    val lat: Double,
    val lon: Double,
)
@Entity
data class CurrentWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val currentId: Long = 0L,
    val feelsLike: Float,
    val temp: Float,
    val lastRefreshed: Long = 0,
    val isValid: Boolean = false,
)

data class CurrentWithWeatherInfo(
    @Embedded val currentWeather: CurrentWeatherEntity,
    @Relation(
        parentColumn = "currentId",
        entityColumn = "id",
    )
    val weather: List<WeatherInfoResponseEntity>
)

@Entity
data class HourlyWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val hourlyId: Long = 0,
    val dt: Long,
    val temperature: Float,
    val lastRefreshed: Long = 0,
    val isValid: Boolean = false,
)

@Entity
data class DailyWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val dailyId: Long = 0,
    val dt: Long,
    @Embedded
    val temperature: TemperatureEntity,
    val lastRefreshed: Long = 0,
    val isValid: Boolean = false,
)

data class TemperatureEntity(
    val min: Float,
    val max: Float
)

data class HourlyWithWeatherInfo(
    @Embedded val hourlyWeatherEntity: HourlyWeatherEntity,
    @Relation(
        parentColumn = "hourlyId",
        entityColumn = "id",
    )
    val weather: List<WeatherInfoResponseEntity>
)

data class DailyWithWeatherInfo(
    @Embedded val dailyWeatherEntity: DailyWeatherEntity,
    @Relation(
        parentColumn = "dailyId",
        entityColumn = "id",
    )
    val weather: List<WeatherInfoResponseEntity>
)

@Entity
data class WeatherInfoResponseEntity(
    @PrimaryKey
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
