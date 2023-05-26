package com.github.odaridavid.weatherapp.data.weather.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherInfoResponseEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun fromWeatherInfo(value: List<WeatherInfoResponseEntity>?): String = Json.encodeToString(value)

    @TypeConverter
    fun toWeatherInfo(value: String?) = value?.let { Json.decodeFromString<List<WeatherInfoResponseEntity>>(it) }

}
