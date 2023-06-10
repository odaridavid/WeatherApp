package com.github.odaridavid.weatherapp.data.weather.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("/data/3.0/onecall")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appid: String,
        @Query("exclude") excludedInfo: String,
        @Query("units") units: String,
        @Query("lang") language: String
    ): Response<WeatherResponse>

}
