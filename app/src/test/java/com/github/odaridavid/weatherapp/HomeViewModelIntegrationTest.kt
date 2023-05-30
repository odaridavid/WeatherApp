package com.github.odaridavid.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.data.weather.DefaultWeatherRepository
import com.github.odaridavid.weatherapp.data.weather.OpenWeatherService
import com.github.odaridavid.weatherapp.data.weather.WeatherResponse
import com.github.odaridavid.weatherapp.data.weather.local.dao.WeatherDao
import com.github.odaridavid.weatherapp.ui.home.HomeScreenIntent
import com.github.odaridavid.weatherapp.ui.home.HomeScreenViewState
import com.github.odaridavid.weatherapp.ui.home.HomeViewModel
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelIntegrationTest {

    private val settingsRepository = FakeSettingsRepository()

    @MockK
    val mockOpenWeatherService = mockk<OpenWeatherService>(relaxed = true)

    @MockK
    val mockWeatherDao = mockk<WeatherDao>(relaxed = true)

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `when fetching weather data is successful, then display correct data`() = runBlocking {
        coEvery {
            mockOpenWeatherService.getWeatherData(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns Response.success<WeatherResponse>(
            fakeSuccessWeatherResponse
        )
        coEvery { mockWeatherDao.getWeather() } returns fakePopulatedResponse

        val weatherRepository =
            DefaultWeatherRepository(
                openWeatherService = mockOpenWeatherService,
                weatherDao = mockWeatherDao,
            )

        val viewModel = createViewModel(weatherRepository = weatherRepository)

        viewModel.processIntent(HomeScreenIntent.LoadWeatherData)

        val expectedState = HomeScreenViewState(
            units = "metric",
            defaultLocation = DefaultLocation(
                longitude = 0.0,
                latitude = 0.0
            ),
            locationName = "-",
            language = "English",
            weather = fakeSuccessResponse,
            isLoading = false,
            errorMessageId = null
        )

        viewModel.state.test {
            awaitItem().also { state ->
                Truth.assertThat(state).isEqualTo(expectedState)
            }
        }
    }

    @Test
    fun `when fetching weather data is unsuccessful, then display correct error state`() =
        runBlocking {
            coEvery {
                mockOpenWeatherService.getWeatherData(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns Response.error<WeatherResponse>(
                404,
                "{}".toResponseBody()
            )

            val weatherRepository =
                DefaultWeatherRepository(
                    openWeatherService = mockOpenWeatherService,
                    weatherDao = mockWeatherDao)

            val viewModel = createViewModel(weatherRepository = weatherRepository)

            viewModel.processIntent(HomeScreenIntent.LoadWeatherData)

            val expectedState = HomeScreenViewState(
                units = "metric",
                defaultLocation = DefaultLocation(
                    longitude = 0.0,
                    latitude = 0.0
                ),
                locationName = "-",
                language = "English",
                weather = null,
                isLoading = false,
                errorMessageId = R.string.error_client
            )

            viewModel.state.test {
                awaitItem().also { state ->
                    Truth.assertThat(state).isEqualTo(expectedState)
                }
            }
        }

    @Test
    fun `when we init the screen, then update the state`() = runBlocking {
        val weatherRepository =
            DefaultWeatherRepository(
                openWeatherService = mockOpenWeatherService,
                weatherDao = mockWeatherDao)

        val viewModel = createViewModel(weatherRepository = weatherRepository)

        val expectedState = HomeScreenViewState(
            units = "metric",
            defaultLocation = DefaultLocation(
                longitude = 0.0,
                latitude = 0.0
            ),
            locationName = "-",
            language = "English",
            weather = null,
            isLoading = true,
            errorMessageId = null
        )

        viewModel.state.test {
            awaitItem().also { state ->
                Truth.assertThat(state).isEqualTo(expectedState)
            }
        }
    }

    @Test
    fun `when we receive a city name, the state is updated with it`() = runTest {
        val weatherRepository = mockk<WeatherRepository>()

        val viewModel = createViewModel(weatherRepository)

        val expectedState = HomeScreenViewState(
            units = "metric",
            defaultLocation = DefaultLocation(
                longitude = 0.0,
                latitude = 0.0
            ),
            locationName = "Paradise",
            language = "English",
            weather = null,
            isLoading = true,
            errorMessageId = null
        )

        viewModel.processIntent(HomeScreenIntent.DisplayCityName(cityName = "Paradise"))

        viewModel.state.test {
            awaitItem().also { state ->
                Truth.assertThat(state).isEqualTo(expectedState)
            }
        }
    }

    private fun createViewModel(weatherRepository: WeatherRepository): HomeViewModel =
        HomeViewModel(
            weatherRepository = weatherRepository,
            settingsRepository = settingsRepository
        )
}
