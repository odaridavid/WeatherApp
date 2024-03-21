package com.github.odaridavid.weatherapp.api

import com.github.odaridavid.weatherapp.model.DefaultLocation
import com.github.odaridavid.weatherapp.model.ExcludedData
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units
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
