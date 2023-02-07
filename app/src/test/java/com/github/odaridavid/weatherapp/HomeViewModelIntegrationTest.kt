package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.ui.home.HomeViewModel
import io.mockk.mockk
import org.junit.Test

class HomeViewModelIntegrationTest {

    private val weatherRepository: WeatherRepository = FakeWeatherRepository()

    @Test
    fun `when the app is initialised, then check for location permissions`() {
        TODO("Implement me")
    }

    @Test
    fun `when the app is initialised and location has been checked,then fetch weather data`() {
        TODO("Implement me")
    }

    @Test
    fun `when fetching weather data is successful, then display correct data`() {
        TODO("Implement me")
    }

    @Test
    fun `when fetching weather data is unsuccessful, then display correct error state`() {
        TODO("Implement me")
    }

    @Test
    fun `when fetching weather data, then display loading state`() {
        TODO("Implement me")
    }

    fun createViewModel(): HomeViewModel = HomeViewModel(
        weatherRepository = weatherRepository,
        settingsRepository = mockk()
    )
}
