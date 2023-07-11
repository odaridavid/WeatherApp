package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.Units
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSettingsRepository : SettingsRepository {

     private val settingsMap = mutableMapOf<String,String>()
    override suspend fun setLanguage(language: String) {
        settingsMap["language"] = language
    }

    override suspend fun getLanguage(): Flow<String> = flow {
        emit(settingsMap["language"] ?: SupportedLanguage.ENGLISH.languageName)
    }

    override suspend fun setUnits(units: String) {
        settingsMap["units"] = units
    }

    override suspend fun getUnits(): Flow<String> = flow {
       emit(settingsMap["units"] ?: Units.METRIC.value)
    }

    override fun getAppVersion(): String = "1.0.0"

    override fun getAvailableLanguages(): List<String> = SupportedLanguage.values().map { it.languageName }

    override suspend fun setDefaultLocation(defaultLocation: DefaultLocation) {
        settingsMap["latlng"] = "${defaultLocation.latitude}/${defaultLocation.longitude}"
    }

    override suspend fun getDefaultLocation(): Flow<DefaultLocation> = flow{
        val latLng = settingsMap["latlng"] ?: "0.0/0.0"
        val latLngList = latLng.split("/")
        DefaultLocation(latitude = latLngList[0].toDouble(), longitude = latLngList[1].toDouble())
    }

    override fun getAvailableUnits(): List<String> = Units.values().map { it.value }
}
