package com.github.odaridavid.weatherapp

import android.content.Context
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.Units
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Test

class SettingsRepositoryUnitTest {

    @MockK
    val mockContext = mockk<Context>(relaxed = true)

    @Test
    fun `when we fetch available languages, then we get all available languages`(){
        val settingsRepo = createSettingsRepository()
        assert(settingsRepo.getAvailableLanguages().size == SupportedLanguage.values().size)
    }

    @Test
    fun `when we fetch available units, then we get all available units`(){
        val settingsRepo = createSettingsRepository()
        assert(settingsRepo.getAvailableUnits().size == Units.values().size)
    }

    private fun createSettingsRepository(): SettingsRepository = DefaultSettingsRepository(
        context = mockContext
    )
}
