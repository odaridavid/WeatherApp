package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.api.Logger
import com.github.odaridavid.weatherapp.api.SettingsRepository
import com.github.odaridavid.weatherapp.api.WeatherRepository
import com.github.odaridavid.weatherapp.data.weather.DefaultWeatherRepository
import com.github.odaridavid.weatherapp.data.weather.remote.DefaultRemoteWeatherDataSource
import com.github.odaridavid.weatherapp.data.weather.remote.OpenWeatherService
import com.github.odaridavid.weatherapp.data.weather.remote.RemoteWeatherDataSource
import com.github.odaridavid.weatherapp.data.weather.remote.WeatherResponse
import com.github.odaridavid.weatherapp.fakes.FakeSettingsRepository
import com.github.odaridavid.weatherapp.fakes.fakeSuccessMappedWeatherResponse
import com.github.odaridavid.weatherapp.fakes.fakeSuccessWeatherResponse
import com.github.odaridavid.weatherapp.model.DefaultLocation
import com.github.odaridavid.weatherapp.model.ErrorType
import com.github.odaridavid.weatherapp.model.Result
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.util.TimeZone

class WeatherRepositoryTest {

    @MockK
    val mockOpenWeatherService = mockk<OpenWeatherService>(relaxed = true)

    private val settingsRepository: SettingsRepository by lazy {
        FakeSettingsRepository()
    }

    @MockK
    val mockLogger = mockk<Logger>(relaxed = true)

    // TODO Look into parameterized testing to cover different mapper scenarios

    @Before
    fun setup() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    @Test
    fun `when we fetch weather data successfully, then a successfully mapped result is emitted`() =
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
            } returns Response.success<WeatherResponse>(
                fakeSuccessWeatherResponse
            )

            settingsRepository.setFormat(TimeFormat.TWELVE_HOUR)

            val weatherRepository = createWeatherRepository()

            val expectedResult = fakeSuccessMappedWeatherResponse

            val actualResults = weatherRepository.fetchWeatherData(
                defaultLocation = DefaultLocation(
                    longitude = 10.0,
                    latitude = 12.90
                ),
                language = "English",
                units = "metric"
            )
            Truth.assertThat(actualResults).isInstanceOf(Result.Success::class.java)
            Truth.assertThat((actualResults as Result.Success).data).isEqualTo(expectedResult)
        }

    @Test
    fun `when we fetch weather data and a server error occurs, then a server error is emitted`() =
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
                500,
                "{}".toResponseBody()
            )

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.fetchWeatherData(
                defaultLocation = DefaultLocation(
                    longitude = 10.0,
                    latitude = 12.90
                ),
                language = "English",
                units = "metric"
            )
            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType).isEqualTo(ErrorType.SERVER)
        }

    @Test
    fun `when we fetch weather data and a client error occurs, then a client error is emitted`() =
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

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.fetchWeatherData(
                defaultLocation = DefaultLocation(
                    longitude = 10.0,
                    latitude = 12.90
                ),
                language = "English",
                units = "metric"
            )

            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType).isEqualTo(ErrorType.CLIENT)
        }

    @Test
    fun `when we fetch weather data and an unauthorized error occurs, then a client error is emitted`() =
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
                401,
                "{}".toResponseBody()
            )

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.fetchWeatherData(
                defaultLocation = DefaultLocation(
                    longitude = 10.0,
                    latitude = 12.90
                ),
                language = "English",
                units = "metric"
            )

            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType)
                .isEqualTo(ErrorType.CLIENT)
        }

    @Test
    fun `when we fetch weather data and a generic error occurs, then a generic error is emitted`() =
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
                800,
                "{}".toResponseBody()
            )

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.fetchWeatherData(
                defaultLocation = DefaultLocation(
                    longitude = 10.0,
                    latitude = 12.90
                ),
                language = "English",
                units = "metric"
            )

            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType)
                .isEqualTo(ErrorType.GENERIC)
        }

    @Test
    fun `when we fetch weather data and an IOException is thrown, then a connection error is emitted`() =
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
            } throws IOException()

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.fetchWeatherData(
                defaultLocation = DefaultLocation(
                    longitude = 10.0,
                    latitude = 12.90
                ),
                language = "English",
                units = "metric"
            )

            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType)
                .isEqualTo(ErrorType.IO_CONNECTION)
        }

    @Test
    fun `when we fetch weather data and an unknown Exception is thrown, then a generic error is emitted`() =
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
            } throws Exception()

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.fetchWeatherData(
                defaultLocation = DefaultLocation(
                    longitude = 10.0,
                    latitude = 12.90
                ),
                language = "English",
                units = "metric"
            )

            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType)
                .isEqualTo(ErrorType.GENERIC)
        }

    private fun createWeatherRepository(
        logger: Logger = mockLogger,
        remoteWeatherDataSource: RemoteWeatherDataSource = DefaultRemoteWeatherDataSource(
            openWeatherService = mockOpenWeatherService
        ),
        settingsRepository: SettingsRepository = this.settingsRepository,
    ): WeatherRepository = DefaultWeatherRepository(
        remoteWeatherDataSource = remoteWeatherDataSource,
        logger = logger,
        settingsRepository = settingsRepository,
    )
}
