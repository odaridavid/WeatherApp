package com.github.odaridavid.weatherapp

import android.content.Context
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository
import io.mockk.impl.annotations.MockK
import io.mockk.mockk

class SettingsRepositoryUnitTest {

    @MockK
    val mockContext = mockk<Context>(relaxed = true)


    fun createSettingsRepository(): SettingsRepository = DefaultSettingsRepository(
        context = mockContext
    )
}
