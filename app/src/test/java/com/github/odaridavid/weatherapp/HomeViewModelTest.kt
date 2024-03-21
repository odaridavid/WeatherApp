package com.github.odaridavid.weatherapp

import app.cash.turbine.test
import com.github.odaridavid.weatherapp.api.Logger
import com.github.odaridavid.weatherapp.api.SettingsRepository
import com.github.odaridavid.weatherapp.api.WeatherRepository
import com.github.odaridavid.weatherapp.data.weather.DefaultWeatherRepository
import com.github.odaridavid.weatherapp.data.weather.remote.DefaultRemoteWeatherDataSource
import com.github.odaridavid.weatherapp.data.weather.remote.OpenWeatherService
import com.github.odaridavid.weatherapp.data.weather.remote.WeatherResponse
import com.github.odaridavid.weatherapp.fakes.FakeSettingsRepository
import com.github.odaridavid.weatherapp.fakes.fakeSuccessMappedWeatherResponse
import com.github.odaridavid.weatherapp.fakes.fakeSuccessWeatherResponse
import com.github.odaridavid.weatherapp.model.DefaultLocation
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units
import com.github.odaridavid.weatherapp.rules.MainCoroutineRule
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
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.TimeZone

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @MockK
    val mockOpenWeatherService = mockk<OpenWeatherService>(relaxed = true)

    @MockK
    val mockLogger = mockk<Logger>(relaxed = true)

    private val settingsRepository: SettingsRepository by lazy {
        FakeSettingsRepository()
    }

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

        settingsRepository.setFormat(TimeFormat.TWELVE_HOUR)

        val weatherRepository = createWeatherRepository()

        val viewModel = createViewModel(
            weatherRepository = weatherRepository
        )

        viewModel.processIntent(HomeScreenIntent.LoadWeatherData)

        val expectedState = HomeScreenViewState(
            units = Units.METRIC,
            defaultLocation = DefaultLocation(
                longitude = 0.0, latitude = 0.0
            ),
            locationName = "-",
            language = SupportedLanguage.ENGLISH,
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
                units = Units.METRIC,
                defaultLocation = DefaultLocation(
                    longitude = 0.0, latitude = 0.0
                ),
                locationName = "-",
                language = SupportedLanguage.ENGLISH,
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
        coEvery {
            mockOpenWeatherService.getWeatherData(
                any(), any(), any(), any(), any(), any()
            )
        } returns Response.success<WeatherResponse>(
            fakeSuccessWeatherResponse
        )

        val settingsRepository = mockk<SettingsRepository>() {
            coEvery { getDefaultLocation() } returns flowOf(DefaultLocation(0.0, 0.0))
            coEvery { getLanguage() } returns flowOf(SupportedLanguage.ENGLISH)
            coEvery { getUnits() } returns flowOf(Units.METRIC)
            coEvery { getFormat() } returns flowOf(TimeFormat.TWELVE_HOUR)
            coEvery { getExcludedData() } returns flowOf("minutely,alerts")
        }

        val viewModel = createViewModel(
            settingsRepository = settingsRepository,
            weatherRepository = createWeatherRepository(
                settingsRepository = settingsRepository
            )
        )

        val expectedState = HomeScreenViewState(
            units = Units.METRIC,
            defaultLocation = DefaultLocation(
                longitude = 0.0, latitude = 0.0
            ),
            locationName = "-",
            language = SupportedLanguage.ENGLISH,
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
    fun `when we receive a city name, the state is updated with it`() = runBlocking {
        coEvery {
            mockOpenWeatherService.getWeatherData(
                any(), any(), any(), any(), any(), any()
            )
        } returns Response.success<WeatherResponse>(
            fakeSuccessWeatherResponse
        )
        val settingsRepository = mockk<SettingsRepository>() {
            coEvery { getDefaultLocation() } returns flowOf(DefaultLocation(0.0, 0.0))
            coEvery { getLanguage() } returns flowOf(SupportedLanguage.ENGLISH)
            coEvery { getUnits() } returns flowOf(Units.METRIC)
            coEvery { getFormat() } returns flowOf(TimeFormat.TWELVE_HOUR)
            coEvery { getExcludedData() } returns flowOf("minutely,alerts")
        }

        val viewModel = createViewModel(
            settingsRepository = settingsRepository,
            weatherRepository = createWeatherRepository(
                settingsRepository = settingsRepository
            )
        )

        viewModel.processIntent(HomeScreenIntent.DisplayCityName(cityName = "Paradise"))

        val expectedState = HomeScreenViewState(
            units = Units.METRIC,
            defaultLocation = DefaultLocation(
                longitude = 0.0, latitude = 0.0
            ),
            locationName = "Paradise",
            language = SupportedLanguage.ENGLISH,
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

    private fun createViewModel(
        weatherRepository: WeatherRepository,
        settingsRepository: SettingsRepository = this.settingsRepository
    ): HomeViewModel =
        HomeViewModel(
            weatherRepository = weatherRepository,
            settingsRepository = settingsRepository
        )

    private fun createWeatherRepository(
        settingsRepository: SettingsRepository = this.settingsRepository
    ) = DefaultWeatherRepository(
        remoteWeatherDataSource = DefaultRemoteWeatherDataSource(
            mockOpenWeatherService
        ),
        logger = mockLogger,
        settingsRepository = settingsRepository,
    )
}
