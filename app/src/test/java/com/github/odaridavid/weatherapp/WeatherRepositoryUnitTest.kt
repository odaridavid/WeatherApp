package com.github.odaridavid.weatherapp

import app.cash.turbine.test
import com.github.odaridavid.weatherapp.core.api.WeatherRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.data.weather.DefaultWeatherRepository
import com.github.odaridavid.weatherapp.data.weather.OpenWeatherService
import com.github.odaridavid.weatherapp.data.weather.WeatherResponse
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

class WeatherRepositoryUnitTest {

    @MockK
    val mockOpenWeatherService = mockk<OpenWeatherService>(relaxed = true)
    @Test
    fun `when we fetch weather data successfully, then a successfully mapped result is emitted`() = runBlocking {
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

        val weatherRepository = createWeatherRepository()

        val expectedResult = fakeSuccessMappedWeatherResponse

        weatherRepository.fetchWeatherData(
            defaultLocation = DefaultLocation(
                longitude = 10.0,
                latitude = 12.90
            ),
            language = "English",
            units = "metric"
        ).test {
            awaitItem().also { result ->
                assert(result.getOrThrow() == expectedResult)
            }
            awaitComplete()
        }
    }

    @Test
    fun `when we fetch weather data and an error occurs, then an error is emitted`() = runBlocking {
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

        weatherRepository.fetchWeatherData(
            defaultLocation = DefaultLocation(
                longitude = 10.0,
                latitude = 12.90
            ),
            language = "English",
            units = "metric"
        ).test {
            awaitItem().also { result ->
                assert(result.exceptionOrNull() is Throwable)
            }
            awaitComplete()
        }
    }

    private fun createWeatherRepository(): WeatherRepository = DefaultWeatherRepository(
        openWeatherService = mockOpenWeatherService
    )
}
