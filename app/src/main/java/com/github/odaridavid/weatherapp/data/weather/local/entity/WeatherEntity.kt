package com.github.odaridavid.weatherapp.data.weather.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
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
    val lastRefreshed: Long = 0, // timestamp of last refresh
    val isValid: Boolean = false // whether data is still valid
){
    // Check if the data is still valid
    fun isDataValid(): Boolean {
        val currentTime = System.currentTimeMillis()
        val fifteenMinutesAgo = currentTime - 15 * 60 * 1000
        return lastRefreshed >= fifteenMinutesAgo && isValid
    }
}