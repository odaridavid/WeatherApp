package com.github.odaridavid.weatherapp

import app.cash.turbine.test
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.ui.home.HomeScreenIntent
import com.github.odaridavid.weatherapp.ui.home.HomeViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelIntegrationTest {

    private val weatherRepository = FakeWeatherRepository()
    private val settingsRepository = FakeSettingsRepository()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun `when fetching weather data is successful, then display correct data`() = runBlocking {
        weatherRepository.isSuccessful = true

        val viewModel = createViewModel()

        viewModel.processIntent(HomeScreenIntent.LoadWeatherData)

        viewModel.state.test {
            awaitItem().also { state ->
                assert(!state.isLoading)
                assert(state.language == "English")
                assert(state.units == "metric")
                assert(state.weather == fakeSuccessMappedWeatherResponse)
            }
        }
    }

    @Test
    fun `when fetching weather data is unsuccessful, then display correct error state`() = runBlocking {
        weatherRepository.isSuccessful = false

        val viewModel = createViewModel()

        viewModel.processIntent(HomeScreenIntent.LoadWeatherData)

        viewModel.state.test {
            awaitItem().also { state ->
                println(state)
                assert(!state.isLoading)
                assert(state.language == "English")
                assert(state.units == "metric")
                assert(state.weather == null)
                assert(state.error != null)
            }
        }
    }

    @Test
    fun `when we init the screen, then update the state`() = runBlocking {
        val viewModel = createViewModel()

        viewModel.state.test {
            awaitItem().also { state ->
                assert(state.isLoading)
                assert(state.language == "English")
                assert(state.units == "metric")
            }
        }
    }

    private fun createViewModel(): HomeViewModel = HomeViewModel(
        weatherRepository = weatherRepository,
        settingsRepository = settingsRepository
    )
}
