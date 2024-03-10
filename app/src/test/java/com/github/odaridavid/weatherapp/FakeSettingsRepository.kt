package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.TimeFormat
import com.github.odaridavid.weatherapp.core.model.Units
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository.Companion.KEY_LANGUAGE
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository.Companion.KEY_LAT_LNG
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository.Companion.KEY_TIME_FORMAT
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository.Companion.KEY_UNITS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSettingsRepository : SettingsRepository {

     private val settingsMap = mutableMapOf<String,String>()
    override suspend fun setLanguage(language: String) {
        settingsMap[KEY_LANGUAGE] = language
    }

    override suspend fun getLanguage(): Flow<String> = flow {
        emit(settingsMap[KEY_LANGUAGE] ?: SupportedLanguage.ENGLISH.languageName)
    }

    override suspend fun setUnits(units: String) {
        settingsMap[KEY_UNITS] = units
    }

    override suspend fun getUnits(): Flow<String> = flow {
       emit(settingsMap[KEY_UNITS] ?: Units.METRIC.value)
    }

    override fun getAppVersion(): String = "1.0.0"

    override fun getAvailableLanguages(): List<String> = SupportedLanguage.entries.map { it.languageName }

    override suspend fun setDefaultLocation(defaultLocation: DefaultLocation) {
        settingsMap[KEY_LAT_LNG] = "${defaultLocation.latitude}/${defaultLocation.longitude}"
    }

    override suspend fun getDefaultLocation(): Flow<DefaultLocation> = flow{
        val latLng = settingsMap[KEY_LAT_LNG] ?: "0.0/0.0"
        val latLngList = latLng.split("/")
        DefaultLocation(latitude = latLngList[0].toDouble(), longitude = latLngList[1].toDouble())
    }

    override suspend fun getFormat(): Flow<String> = flow {
        emit(settingsMap[KEY_TIME_FORMAT] ?: TimeFormat.TWENTY_FOUR_HOUR.name)
    }

    override suspend fun setFormat(format: String) {
        settingsMap[KEY_TIME_FORMAT] = format
    }

    override fun getFormats(): List<String> {
        return TimeFormat.entries.map { it.value }
    }

    override suspend fun getExcludedData(): String {
        TODO("Not yet implemented")
    }

    override suspend fun setExcludedData(excludedData: List<ExcludedData>) {
        TODO("Not yet implemented")
    }

    override fun getAvailableUnits(): List<String> = Units.entries.map { it.value }
}
