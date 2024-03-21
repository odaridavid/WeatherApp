package com.github.odaridavid.weatherapp.fakes

import com.github.odaridavid.weatherapp.api.SettingsRepository
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository.Companion.KEY_EXCLUDED_DATA
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository.Companion.KEY_LANGUAGE
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository.Companion.KEY_LAT_LNG
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository.Companion.KEY_TIME_FORMAT
import com.github.odaridavid.weatherapp.data.settings.DefaultSettingsRepository.Companion.KEY_UNITS
import com.github.odaridavid.weatherapp.model.DefaultLocation
import com.github.odaridavid.weatherapp.model.ExcludedData
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSettingsRepository : SettingsRepository {

    private val settingsMap = mutableMapOf<String, Any>()
    override suspend fun setLanguage(language: SupportedLanguage) {
        settingsMap[KEY_LANGUAGE] = language
    }

    override suspend fun getLanguage(): Flow<SupportedLanguage> = flow {
        emit(settingsMap[KEY_LANGUAGE] as? SupportedLanguage ?: SupportedLanguage.ENGLISH)
    }

    override suspend fun setUnits(units: Units) {
        settingsMap[KEY_UNITS] = units
    }

    override suspend fun getUnits(): Flow<Units> = flow {
        emit(settingsMap[KEY_UNITS] as? Units ?: Units.METRIC)
    }

    override fun getAppVersion(): String = "1.0.0"

    override suspend fun setDefaultLocation(defaultLocation: DefaultLocation) {
        settingsMap[KEY_LAT_LNG] = "${defaultLocation.latitude}/${defaultLocation.longitude}"
    }

    override suspend fun getDefaultLocation(): Flow<DefaultLocation> = flow {
        val latLng = settingsMap[KEY_LAT_LNG] as? String ?: "0.0/0.0"
        val latLngList = latLng.split("/")
        DefaultLocation(latitude = latLngList[0].toDouble(), longitude = latLngList[1].toDouble())
    }

    override suspend fun getFormat(): Flow<TimeFormat> = flow {
        emit(settingsMap[KEY_TIME_FORMAT] as? TimeFormat ?: TimeFormat.TWENTY_FOUR_HOUR)
    }

    override suspend fun setFormat(format: TimeFormat) {
        settingsMap[KEY_TIME_FORMAT] = format
    }

    override suspend fun getExcludedData(): Flow<String> = flow {
        emit(
            settingsMap[KEY_EXCLUDED_DATA] as? String
                ?: "${ExcludedData.MINUTELY.value},${ExcludedData.ALERTS.value}"
        )
    }

    override suspend fun setExcludedData(excludedData: List<ExcludedData>) {
        val formattedData = excludedData.joinToString(separator = ",") { it.value }
        settingsMap[KEY_EXCLUDED_DATA] = formattedData
    }
}
