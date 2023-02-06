package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.WeatherRepository
import com.github.odaridavid.weatherapp.data.DefaultWeatherRepository
import com.github.odaridavid.weatherapp.data.OpenWeatherService
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Test

class WeatherRepositoryUnitTest {

    @MockK
    val mockOpenWeatherService = mockk<OpenWeatherService>(relaxed = true)

    @Test
    fun `when we fetch weather data with an unsuccessful response, then an error is emitted`(){
        TODO("I am missing :(")
    }

    @Test
    fun `when we fetch weather data successfully, then a successfully mapped result is emitted`(){
        TODO("I am missing :(")
    }

    @Test
    fun `when we fetch weather data and an error occurs, then an error is emitted`(){
        TODO("I am missing :(")
    }

    fun createWeatherRepository(): WeatherRepository = DefaultWeatherRepository(
        openWeatherService = mockOpenWeatherService
    )
}
