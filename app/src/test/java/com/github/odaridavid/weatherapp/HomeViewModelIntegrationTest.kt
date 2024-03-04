package com.github.odaridavid.weatherapp

import app.cash.turbine.test
import com.github.odaridavid.weatherapp.core.Result
import com.github.odaridavid.weatherapp.core.api.Logger
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.TimeFormat
import com.github.odaridavid.weatherapp.data.weather.DefaultWeatherRepository
import com.github.odaridavid.weatherapp.data.weather.remote.DefaultRemoteWeatherDataSource
import com.github.odaridavid.weatherapp.data.weather.remote.OpenWeatherService
import com.github.odaridavid.weatherapp.data.weather.remote.WeatherResponse
import com.github.odaridavid.weatherapp.ui.home.HomeScreenIntent
import com.github.odaridavid.weatherapp.ui.home.HomeScreenViewState
import com.github.odaridavid.weatherapp.ui.home.HomeViewModel
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.TimeZone

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelIntegrationTest {

    @MockK
    val mockOpenWeatherService = mockk<OpenWeatherService>(relaxed = true)

    @MockK
    val mockLogger = mockk<Logger>(relaxed = true)

    @MockK
    val mockSettingsRepository = mockk<SettingsRepository>(relaxed = true)

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    @Test
    fun `when fetching weather data is successful, then display correct data`() = runBlocking {
        coEvery {
            mockOpenWeatherService.getWeatherData(
                any(), any(), any(), any(), any(), any()
            )
        } returns Response.success<WeatherResponse>(
            fakeSuccessWeatherResponse
        )

        coEvery { mockSettingsRepository.getFormat() } returns flowOf(TimeFormat.TWELVE_HOUR.value)

        val weatherRepository = createWeatherRepository()

        val viewModel = createViewModel(
            weatherRepository = weatherRepository
        )

        viewModel.processIntent(HomeScreenIntent.LoadWeatherData)

        val expectedState = HomeScreenViewState(
            units = "metric",
            defaultLocation = DefaultLocation(
                longitude = 0.0, latitude = 0.0
            ),
            locationName = "-",
            language = "English",
            weather = fakeSuccessMappedWeatherResponse,
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
                    any(), any(), any(), any(), any(), any()
                )
            } returns Response.error<WeatherResponse>(
                404, "{}".toResponseBody()
            )

            val weatherRepository = createWeatherRepository()

            val viewModel = createViewModel(weatherRepository = weatherRepository)

            viewModel.processIntent(HomeScreenIntent.LoadWeatherData)

            val expectedState = HomeScreenViewState(
                units = "metric",
                defaultLocation = DefaultLocation(
                    longitude = 0.0, latitude = 0.0
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
        val weatherRepository = mockk<WeatherRepository>(){
            coEvery { fetchWeatherData(any(), any(), any()) } returns Result.Success(fakeSuccessMappedWeatherResponse)
        }

        val viewModel = createViewModel(weatherRepository = weatherRepository)

        val expectedState = HomeScreenViewState(
            units = "metric",
            defaultLocation = DefaultLocation(
                longitude = 0.0, latitude = 0.0
            ),
            locationName = "-",
            language = "English",
            weather = fakeSuccessMappedWeatherResponse,
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
    fun `when we receive a city name, the state is updated with it`() = runTest {
        val weatherRepository = mockk<WeatherRepository>(){
            coEvery { fetchWeatherData(any(), any(), any()) } returns Result.Success(fakeSuccessMappedWeatherResponse)
        }

        val viewModel = createViewModel(
            weatherRepository = weatherRepository
        )

        val expectedState = HomeScreenViewState(
            units = "metric",
            defaultLocation = DefaultLocation(
                longitude = 0.0, latitude = 0.0
            ),
            locationName = "Paradise",
            language = "English",
            weather = fakeSuccessMappedWeatherResponse,
            isLoading = false,
            errorMessageId = null
        )

        viewModel.processIntent(HomeScreenIntent.DisplayCityName(cityName = "Paradise"))

        viewModel.state.test {
            awaitItem().also { state ->
                Truth.assertThat(state).isEqualTo(expectedState)
            }
        }
    }

    private fun createViewModel(
        weatherRepository: WeatherRepository,
        settingsRepository: SettingsRepository = mockk<SettingsRepository>() {
            coEvery { getUnits() } returns flowOf("metric")
            coEvery { getDefaultLocation() } returns flowOf(
                DefaultLocation(
                    longitude = 0.0, latitude = 0.0
                )
            )
            coEvery { getLanguage() } returns flowOf("English")
            coEvery { getAppVersion() } returns "1.0.0"
            coEvery { getAvailableLanguages() } returns listOf("English")
            coEvery { getAvailableUnits() } returns listOf("metric")
        }
    ): HomeViewModel =
        HomeViewModel(
            weatherRepository = weatherRepository,
            settingsRepository = settingsRepository
        )

    private fun createWeatherRepository() = DefaultWeatherRepository(
        remoteWeatherDataSource = DefaultRemoteWeatherDataSource(
            mockOpenWeatherService
        ),
        logger = mockLogger,
        settingsRepository = mockSettingsRepository,
    )
}
