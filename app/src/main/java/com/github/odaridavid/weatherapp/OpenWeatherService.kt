package com.github.odaridavid.weatherapp

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("/data/3.0/onecall")
    fun getWeatherData(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("appid") appid: String,
        @Query("exclude") excludedInfo: String,
        @Query("units") units: String,
        @Query("lang") language: String
    )
    // TODO Define response structure as data class
}
