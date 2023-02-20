package com.github.odaridavid.weatherapp.data.settings

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.core.api.SettingsRepository
import com.github.odaridavid.weatherapp.core.model.DefaultLocation
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.TimeFormat
import com.github.odaridavid.weatherapp.core.model.Units
import com.github.odaridavid.weatherapp.data.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultSettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val PREF_LANGUAGE by lazy { stringPreferencesKey("language") }
    private val PREF_UNITS by lazy { stringPreferencesKey("units") }
    private val TIME_FORMAT by lazy { stringPreferencesKey("time_formats") }
    private val PREF_LAT_LNG by lazy { stringPreferencesKey("lat_lng") }

    //Düsseldorf
    private val DEFAULT_LONGITUDE = 6.773456
    private val DEFAULT_LATITUDE = 51.227741

    override suspend fun setLanguage(language: String) {
        set(key = PREF_LANGUAGE, value = language)
    }

    override suspend fun getLanguage(): Flow<String> =
        get(key = PREF_LANGUAGE, default = SupportedLanguage.ENGLISH.languageName)

    override suspend fun setUnits(units: String) {
        set(key = PREF_UNITS, value = units)
    }

    override suspend fun getUnits(): Flow<String> =
        get(key = PREF_UNITS, default = Units.METRIC.value)

    override fun getAppVersion(): String =
        "Version : ${BuildConfig.VERSION_NAME}-${BuildConfig.BUILD_TYPE}"

    override fun getAvailableLanguages(): List<String> =
        SupportedLanguage.values().map { it.languageName }

    override fun getAvailableMetrics(): List<String> = Units.values().map { it.value }

    override suspend fun setDefaultLocation(defaultLocation: DefaultLocation) {
        set(key = PREF_LAT_LNG, value = "${defaultLocation.latitude}/${defaultLocation.longitude}")
    }

    override suspend fun getDefaultLocation(): Flow<DefaultLocation> {
        return get(
            key = PREF_LAT_LNG,
            default = "$DEFAULT_LATITUDE/$DEFAULT_LONGITUDE"
        ).map { latlng ->
            val latLngList = latlng.split("/").map { it.toDouble() }
            DefaultLocation(latitude = latLngList[0], longitude = latLngList[1])
        }
    }

    override suspend fun getFormat(): Flow<String> =
        get(key = TIME_FORMAT, default = TimeFormat.TWENTY_FOUR_HOUR.name)


    override suspend fun setFormat(format: TimeFormat) {
        set(key = TIME_FORMAT, value = format.name)
    }

    override fun getFormats(): List<TimeFormat> {
       return TimeFormat.values().toList()
    }

    private suspend fun <T> set(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { settings ->
            settings[key] = value
        }
    }

    private fun <T> get(key: Preferences.Key<T>, default: T): Flow<T> {
        return context.dataStore.data.map { settings ->
            settings[key] ?: default
        }
    }
}
