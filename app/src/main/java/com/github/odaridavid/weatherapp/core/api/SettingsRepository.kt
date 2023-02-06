package com.github.odaridavid.weatherapp.core.api

import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setLanguage(language: String)

    suspend fun getLanguage(): Flow<String>

    suspend fun setUnits(units: String)

    suspend fun getUnits(): Flow<String>

    fun getAppVersion(): String

    fun getAvailableLanguages(): List<String>

    suspend fun setDefaultLocation(defaultLocation: DefaultLocation)

    suspend fun getDefaultLocation(): Flow<DefaultLocation>
}
