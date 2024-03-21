package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.api.SettingsRepository
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository
import com.github.odaridavid.weatherapp.fakes.FakeSettingsRepository
import com.github.odaridavid.weatherapp.model.DefaultLocation
import com.github.odaridavid.weatherapp.model.ExcludedData
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SettingsRepositoryTest {

    @Test
    fun `when we update language, then we get the updated language`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.setLanguage(SupportedLanguage.FRENCH)
            settingsRepo.getLanguage().map { language ->
                assert(language == SupportedLanguage.FRENCH)
            }
        }
    }

    @Test
    fun `when we fetch the language, then we get the default language`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.getLanguage().map { language ->
                assert(language == SupportedLanguage.ENGLISH)
            }
        }
    }

    @Test
    fun `when we update units, then we get the updated units`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.setUnits(Units.IMPERIAL)
            settingsRepo.getUnits().map { units ->
                assert(units == Units.IMPERIAL)
            }
        }
    }

    @Test
    fun `when we fetch the units, then we get the default units`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.getUnits().map { units ->
                assert(units == Units.METRIC)
            }
        }
    }

    @Test
    fun `when we update time format, then we get the updated time format`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.setFormat(TimeFormat.TWELVE_HOUR)
            settingsRepo.getFormat().map { format ->
                assert(format == TimeFormat.TWELVE_HOUR)
            }
        }
    }

    @Test
    fun `when we fetch the time format, then we get the default time format`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.getFormat().map { format ->
                assert(format == TimeFormat.TWENTY_FOUR_HOUR)
            }
        }
    }

    @Test
    fun `when we fetch the location, then we get the default location`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.getDefaultLocation().map { location ->
                assert(location.latitude == DefaultSettingsRepository.DEFAULT_LATITUDE)
                assert(location.longitude == DefaultSettingsRepository.DEFAULT_LONGITUDE)
            }
        }
    }

    @Test
    fun `when we update the location, then we get the correct location`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.setDefaultLocation(DefaultLocation(0.0, 0.0))
            settingsRepo.getDefaultLocation().map { location ->
                assert(location.latitude == 0.0)
                assert(location.longitude == 0.0)
            }
        }
    }

    @Test
    fun `when we update excluded data, then we get the updated excluded data`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.setExcludedData(
                listOf(
                    ExcludedData.ALERTS,
                    ExcludedData.MINUTELY,
                    ExcludedData.DAILY
                )
            )
            settingsRepo.getExcludedData().map { excludedData ->
                assert(excludedData == "${ExcludedData.ALERTS.value},${ExcludedData.MINUTELY.value},${ExcludedData.DAILY.value}")
            }
        }
    }

    private fun createSettingsRepository(): SettingsRepository = FakeSettingsRepository()
}
