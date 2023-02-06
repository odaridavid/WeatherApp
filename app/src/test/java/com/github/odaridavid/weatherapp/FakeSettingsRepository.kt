package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import kotlinx.coroutines.flow.Flow

class FakeSettingsRepository : SettingsRepository {
    override suspend fun setLanguage(language: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getLanguage(): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun setUnits(units: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUnits(): Flow<String> {
        TODO("Not yet implemented")
    }

    override fun getAppVersion(): String {
        TODO("Not yet implemented")
    }

    override fun getAvailableLanguages(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun setDefaultLocation(defaultLocation: DefaultLocation) {
        TODO("Not yet implemented")
    }

    override suspend fun getDefaultLocation(): Flow<DefaultLocation> {
        TODO("Not yet implemented")
    }
}
