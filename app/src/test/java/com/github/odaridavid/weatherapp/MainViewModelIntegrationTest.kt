package com.github.odaridavid.weatherapp

import app.cash.turbine.test
import com.github.odaridavid.weatherapp.core.api.Logger
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.rules.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MainViewModelIntegrationTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @MockK
    val logger = mockk<Logger>().apply {
        every { logException(any()) } returns Unit
    }

    @MockK
    val settingsRepository = mockk<SettingsRepository>().apply {
        coEvery { setDefaultLocation(any()) } returns Unit
    }

    @Test
    fun `when we grant permission, then the state is updated as expected`() = runTest {
        val viewModel = createMainViewModel()

        viewModel.processIntent(MainViewIntent.GrantPermission(true))

        viewModel.state.test {
            awaitItem().also { state ->
                assert(state.isPermissionGranted)
            }
        }
    }

    @Test
    fun `when we check location settings, then the state is updated as expected`() = runTest {
        val viewModel = createMainViewModel()

        viewModel.processIntent(MainViewIntent.CheckLocationSettings(true))

        viewModel.state.test {
            awaitItem().also { state ->
                assert(state.isLocationSettingEnabled)
            }
        }
    }

    // TODO Use parameterized tests
    @Test
    fun `when we deny permission, then the state is updated as expected`() = runTest {
        val viewModel = createMainViewModel()

        viewModel.processIntent(MainViewIntent.GrantPermission(false))

        viewModel.state.test {
            awaitItem().also { state ->
                assert(!state.isPermissionGranted)
            }
        }
    }

    @Test
    fun `when we log an exception,then the right methods are called`() = runTest {
        val viewModel = createMainViewModel(
            logger = logger,
        )
        viewModel.state.test {
            viewModel.processIntent(MainViewIntent.LogException(Exception("Test")))
            awaitItem()
        }

        verify { logger.logException(any()) }
    }

    @Test
    fun `when we receive a location, then the state is updated as expected`() = runTest {
        val viewModel = createMainViewModel(
            settingsRepository = settingsRepository,
        )
        viewModel.state.test {
            viewModel.processIntent(MainViewIntent.ReceiveLocation(0.0, 0.0))
            awaitItem().also { state ->
                assert(state.defaultLocation?.latitude == 0.0)
                assert(state.defaultLocation?.longitude == 0.0)
            }
        }
    }

    private fun createMainViewModel(
        settingsRepository: SettingsRepository = mockk(),
        logger: Logger = mockk(),
    ): MainViewModel {
        return MainViewModel(settingsRepository = settingsRepository, logger = logger)
    }
}
