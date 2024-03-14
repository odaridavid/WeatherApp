package com.github.odaridavid.weatherapp.core.api

import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.TimeFormat
import com.github.odaridavid.weatherapp.core.model.Units
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setLanguage(language: SupportedLanguage)

    suspend fun getLanguage(): Flow<SupportedLanguage>

    suspend fun setUnits(units: Units)

    suspend fun getUnits(): Flow<Units>

    fun getAppVersion(): String

    suspend fun setDefaultLocation(defaultLocation: DefaultLocation)

    suspend fun getDefaultLocation(): Flow<DefaultLocation>

    suspend fun getFormat(): Flow<TimeFormat>

    suspend fun setFormat(format: TimeFormat)

    suspend fun getExcludedData(): Flow<String>

    suspend fun setExcludedData(excludedData: List<ExcludedData>)
}
