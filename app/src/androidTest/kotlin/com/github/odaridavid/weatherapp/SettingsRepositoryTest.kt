package com.github.odaridavid.weatherapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.github.odaridavid.weatherapp.api.SettingsRepository
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository
import com.github.odaridavid.weatherapp.model.DefaultLocation
import com.github.odaridavid.weatherapp.model.ExcludedData
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@SmallTest
class SettingsRepositoryTest {

    // TODO instrumentation test coverage
    private lateinit var settingsRepository: SettingsRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        settingsRepository = DefaultSettingsRepository(context)
    }

    @Test
    fun when_we_update_language_then_we_get_the_updated_language() = runTest {
        settingsRepository.setLanguage(SupportedLanguage.FRENCH)
        val language = settingsRepository.getLanguage()
        language.test {
            awaitItem().also { language ->
                assert(language == SupportedLanguage.FRENCH)
            }
        }
    }

    @Test
    fun when_we_update_units_then_we_get_the_updated_units() = runTest {
        settingsRepository.setUnits(Units.IMPERIAL)
        val units = settingsRepository.getUnits()
        units.test {
            awaitItem().also { units ->
                assert(units == Units.IMPERIAL)
            }
        }
    }

    @Test
    fun when_we_update_time_format_then_we_get_the_updated_time_format() = runTest {
        settingsRepository.setFormat(TimeFormat.TWENTY_FOUR_HOUR)
        val timeFormat = settingsRepository.getFormat()
        timeFormat.test {
            awaitItem().also { timeFormat ->
                assert(timeFormat == TimeFormat.TWENTY_FOUR_HOUR)
            }
        }
    }

    @Test
    fun when_we_update_excluded_data_then_we_get_the_updated_excluded_data() = runTest {
        val excludedData = listOf(ExcludedData.MINUTELY, ExcludedData.ALERTS)
        settingsRepository.setExcludedData(excludedData)
        val data = settingsRepository.getExcludedData()
        data.test {
            awaitItem().also { data ->
                assert(data == "minutely,alerts")
            }
        }
    }

    @Test
    fun when_we_update_default_location_then_we_get_the_updated_default_location() = runTest {
        val defaultLocation = DefaultLocation(23.23, 34.12)
        settingsRepository.setDefaultLocation(defaultLocation)
        val location = settingsRepository.getDefaultLocation()
        location.test {
            awaitItem().also { location ->
                assert(location == defaultLocation)
            }
        }
    }
}
