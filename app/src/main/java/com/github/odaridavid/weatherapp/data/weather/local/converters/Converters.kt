package com.github.odaridavid.weatherapp.data.weather.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.github.odaridavid.weatherapp.data.weather.local.entity.DailyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.HourlyWeatherEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.TemperatureEntity
import com.github.odaridavid.weatherapp.data.weather.local.entity.WeatherInfoResponseEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromHourlyWeatherEntityList(hourlyList: List<HourlyWeatherEntity>): String {
        return gson.toJson(hourlyList)
    }

    @TypeConverter
    fun toHourlyWeatherEntityList(json: String): List<HourlyWeatherEntity> {
        val type = object : TypeToken<List<HourlyWeatherEntity>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromDailyWeatherEntityList(dailyList: List<DailyWeatherEntity>): String {
        return gson.toJson(dailyList)
    }

    @TypeConverter
    fun toDailyWeatherEntityList(json: String): List<DailyWeatherEntity> {
        val type = object : TypeToken<List<DailyWeatherEntity>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromWeatherInfoResponseEntityList(weatherList: List<WeatherInfoResponseEntity>): String {
        return gson.toJson(weatherList)
    }

    @TypeConverter
    fun toWeatherInfoResponseEntityList(json: String): List<WeatherInfoResponseEntity> {
        val type = object : TypeToken<List<WeatherInfoResponseEntity>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromTemperatureEntity(temperature: TemperatureEntity): String {
        return gson.toJson(temperature)
    }

    @TypeConverter
    fun toTemperatureEntity(json: String): TemperatureEntity {
        return gson.fromJson(json, TemperatureEntity::class.java)
    }
}
