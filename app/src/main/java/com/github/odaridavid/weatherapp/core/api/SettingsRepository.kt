package com.github.odaridavid.weatherapp.core.api

import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.ExcludedData
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setLanguage(language: String)

    suspend fun getLanguage(): Flow<String>

    suspend fun setUnits(units: String)

    suspend fun getUnits(): Flow<String>

    fun getAppVersion(): String

    fun getAvailableLanguages(): List<String>

    fun getAvailableUnits(): List<String>

    suspend fun setDefaultLocation(defaultLocation: DefaultLocation)

    suspend fun getDefaultLocation(): Flow<DefaultLocation>

    suspend fun getFormat(): Flow<String>

    suspend fun setFormat(format: String)

    fun getFormats() : List<String>

    suspend fun getExcludedData() : String

    suspend fun setExcludedData(excludedData: List<ExcludedData>)

}
