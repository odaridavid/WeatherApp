package com.github.odaridavid.weatherapp

import app.cash.turbine.test
import com.github.odaridavid.weatherapp.api.Logger
import com.github.odaridavid.weatherapp.api.SettingsRepository
import com.github.odaridavid.weatherapp.fakes.FakeSettingsRepository
import com.github.odaridavid.weatherapp.model.ExcludedData
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units
import com.github.odaridavid.weatherapp.rules.MainCoroutineRule
import com.github.odaridavid.weatherapp.ui.settings.SettingsScreenIntent
import com.github.odaridavid.weatherapp.ui.settings.SettingsScreenViewState
import com.github.odaridavid.weatherapp.ui.settings.SettingsViewModel
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val settingsRepository: SettingsRepository = FakeSettingsRepository()

    @MockK
    private val logger: Logger = mockk()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun `when we load screen data, then the state is updated as expected`() = runBlocking {
        val settingsViewModel = createSettingsScreenViewModel()

        settingsViewModel.processIntent(SettingsScreenIntent.LoadSettingScreenData)

        val expectedState = SettingsScreenViewState(
            selectedUnit = Units.METRIC,
            selectedLanguage = SupportedLanguage.ENGLISH,
            availableLanguages = SupportedLanguage.entries,
            availableUnits = Units.entries,
            selectedTimeFormat = TimeFormat.TWENTY_FOUR_HOUR,
            availableFormats = TimeFormat.entries,
            versionInfo = "1.0.0",
            selectedExcludedData = listOf(ExcludedData.MINUTELY, ExcludedData.ALERTS),
            excludedData = ExcludedData.entries,
            selectedExcludedDataDisplayValue = "minutely,alerts"
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

        settingsViewModel.processIntent(SettingsScreenIntent.ChangeUnits(selectedUnits = Units.STANDARD))

        settingsViewModel.state.test {
            awaitItem().also { state ->
                assert(state.error == null)
                assert(state.selectedUnit == Units.STANDARD)
            }
        }
    }

    @Test
    fun `when we change language, then the language is updated `() = runBlocking {
        val settingsViewModel = createSettingsScreenViewModel()

        settingsViewModel.processIntent(SettingsScreenIntent.ChangeLanguage(selectedLanguage = SupportedLanguage.AFRIKAANS))

        settingsViewModel.state.test {
            awaitItem().also { state ->
                assert(state.error == null)
                assert(state.selectedLanguage == SupportedLanguage.AFRIKAANS)
            }
        }
    }

    @Test
    fun `when we change time format, then the format is updated `() = runBlocking {
        val settingsViewModel = createSettingsScreenViewModel()

        settingsViewModel.processIntent(SettingsScreenIntent.ChangeTimeFormat(selectedTimeFormat = TimeFormat.TWENTY_FOUR_HOUR))

        settingsViewModel.state.test {
            awaitItem().also { state ->
                assert(state.error == null)
                assert(state.selectedTimeFormat == TimeFormat.TWENTY_FOUR_HOUR)
            }
        }
    }

    @Test
    fun `when we change excluded data, then the excluded data is updated `() = runBlocking {
        val settingsViewModel = createSettingsScreenViewModel()

        settingsViewModel.processIntent(
            SettingsScreenIntent.ChangeExcludedData(
                selectedExcludedData = listOf(
                    ExcludedData.CURRENT,
                    ExcludedData.DAILY,
                    ExcludedData.NONE
                )
            )
        )

        settingsViewModel.state.test {
            awaitItem().also { state ->
                assert(state.error == null)
                assert(
                    state.selectedExcludedData == listOf(
                        ExcludedData.CURRENT,
                        ExcludedData.DAILY,
                        ExcludedData.NONE
                    )
                )
                assert(state.selectedExcludedDataDisplayValue == "current,daily,none")
            }
        }
    }

    private fun createSettingsScreenViewModel(): SettingsViewModel = SettingsViewModel(
        settingsRepository = settingsRepository,
        logger = logger,
    )
}
