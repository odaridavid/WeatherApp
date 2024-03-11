package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.TimeFormat
import com.github.odaridavid.weatherapp.core.model.Units
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository
import com.github.odaridavid.weatherapp.fakes.FakeSettingsRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SettingsRepositoryUnitTest {
    // TODO Integration test with the real data store
    @Test
    fun `when we fetch available languages, then we get all available languages`() {
        val settingsRepo = createSettingsRepository()
        assert(settingsRepo.getAvailableLanguages().size == SupportedLanguage.entries.size)
    }

    @Test
    fun `when we update language, then we get the updated language`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.setLanguage(SupportedLanguage.FRENCH.languageName)
            settingsRepo.getLanguage().map { language ->
                assert(language == SupportedLanguage.FRENCH.languageName)
            }
        }
    }

    @Test
    fun `when we fetch the language, then we get the default language`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.getLanguage().map { language ->
                assert(language == SupportedLanguage.ENGLISH.languageName)
            }
        }
    }

    @Test
    fun `when we update units, then we get the updated units`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.setUnits(Units.IMPERIAL.value)
            settingsRepo.getUnits().map { units ->
                assert(units == Units.IMPERIAL.value)
            }
        }
    }

    @Test
    fun `when we fetch the units, then we get the default units`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.getUnits().map { units ->
                assert(units == Units.METRIC.value)
            }
        }
    }

    @Test
    fun `when we fetch available units, then we get all available units`() {
        val settingsRepo = createSettingsRepository()
        assert(settingsRepo.getAvailableUnits().size == Units.entries.size)
    }

    @Test
    fun `when we update time format, then we get the updated time format`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.setFormat(TimeFormat.TWELVE_HOUR.name)
            settingsRepo.getFormat().map { format ->
                assert(format == TimeFormat.TWELVE_HOUR.name)
            }
        }
    }

    @Test
    fun `when we fetch the time format, then we get the default time format`() {
        val settingsRepo = createSettingsRepository()
        runBlocking {
            settingsRepo.getFormat().map { format ->
                assert(format == TimeFormat.TWENTY_FOUR_HOUR.name)
            }
        }
    }

    @Test
    fun `when we fetch available time formats, then we get all available formats`() {
        val settingsRepo = createSettingsRepository()
        assert(settingsRepo.getFormats().size == TimeFormat.entries.size)
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
            settingsRepo.setExcludedData(listOf(ExcludedData.ALERTS, ExcludedData.MINUTELY, ExcludedData.DAILY))
            settingsRepo.getExcludedData().map { excludedData ->
                assert(excludedData == "${ExcludedData.ALERTS.value},${ExcludedData.MINUTELY.value},${ExcludedData.DAILY.value}")
            }
        }
    }

    private fun createSettingsRepository(): SettingsRepository = FakeSettingsRepository()
}
