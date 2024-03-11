package com.github.odaridavid.weatherapp

import app.cash.turbine.test
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.TimeFormat
import com.github.odaridavid.weatherapp.core.model.Units
import com.github.odaridavid.weatherapp.fakes.FakeSettingsRepository
import com.github.odaridavid.weatherapp.rules.MainCoroutineRule
import com.github.odaridavid.weatherapp.ui.settings.SettingsScreenIntent
import com.github.odaridavid.weatherapp.ui.settings.SettingsScreenViewState
import com.github.odaridavid.weatherapp.ui.settings.SettingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelIntegrationTest {

    private val settingsRepository: SettingsRepository = FakeSettingsRepository()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun `when we load screen data, then the state is updated as expected`() = runBlocking {
        val settingsViewModel = createSettingsScreenViewModel()

        settingsViewModel.processIntent(SettingsScreenIntent.LoadSettingScreenData)

        val expectedState = SettingsScreenViewState(
            selectedUnit = Units.METRIC.value,
            selectedLanguage = SupportedLanguage.ENGLISH.languageName,
            availableLanguages = SupportedLanguage.entries.map { it.languageName },
            availableUnits = Units.entries.map { it.value },
            selectedTimeFormat = TimeFormat.TWENTY_FOUR_HOUR.name,
            availableFormats = TimeFormat.entries.map { it.value },
            versionInfo = "1.0.0"
        )

        settingsViewModel.state.test {
            awaitItem().also { state ->
                assert(state.error == null)
                assert(state == expectedState)
            }
        }
    }

    @Test
    fun `when we change units, then the units are updated`() = runBlocking {
        val settingsViewModel = createSettingsScreenViewModel()

        settingsViewModel.processIntent(SettingsScreenIntent.ChangeUnits(selectedUnits = "standard"))

        settingsViewModel.state.test {
            awaitItem().also { state ->
                assert(state.error == null)
                assert(state.selectedUnit == "standard")
            }
        }
    }

    @Test
    fun `when we change language, then the language is updated `() = runBlocking {
        val settingsViewModel = createSettingsScreenViewModel()

        settingsViewModel.processIntent(SettingsScreenIntent.ChangeLanguage(selectedLanguage = "French"))

        settingsViewModel.state.test {
            awaitItem().also { state ->
                assert(state.error == null)
                assert(state.selectedLanguage == "French")
            }
        }
    }

    @Test
    fun `when we change time format, then the format is updated `() = runBlocking {
        val settingsViewModel = createSettingsScreenViewModel()

        settingsViewModel.processIntent(SettingsScreenIntent.ChangeTimeFormat(selectedTimeFormat = TimeFormat.TWENTY_FOUR_HOUR.name))

        settingsViewModel.state.test {
            awaitItem().also { state ->
                assert(state.error == null)
                assert(state.selectedTimeFormat == TimeFormat.TWENTY_FOUR_HOUR.name)
            }
        }
    }

    private fun createSettingsScreenViewModel(): SettingsViewModel = SettingsViewModel(
        settingsRepository = settingsRepository
    )
}
