package com.github.odaridavid.weatherapp.data.settings

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.odaridavid.weatherapp.BuildConfig
import com.github.odaridavid.weatherapp.api.SettingsRepository
import com.github.odaridavid.weatherapp.data.dataStore
import com.github.odaridavid.weatherapp.model.DefaultLocation
import com.github.odaridavid.weatherapp.model.ExcludedData
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultSettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val prefLanguage by lazy { stringPreferencesKey(KEY_LANGUAGE) }
    private val prefUnits by lazy { stringPreferencesKey(KEY_UNITS) }
    private val prefTimeFormat by lazy { stringPreferencesKey(KEY_TIME_FORMAT) }
    private val prefLatLng by lazy { stringPreferencesKey(KEY_LAT_LNG) }
    private val prefExcludedData by lazy { stringPreferencesKey(KEY_EXCLUDED_DATA) }

    override suspend fun setLanguage(language: SupportedLanguage) {
        set(key = prefLanguage, value = language.name)
    }

    override suspend fun getLanguage(): Flow<SupportedLanguage> {
        return get(key = prefLanguage, default = SupportedLanguage.ENGLISH.name).map {
            SupportedLanguage.valueOf(it)
        }
    }

    override suspend fun setUnits(units: Units) {
        set(key = prefUnits, value = units.name)
    }

    override suspend fun getUnits(): Flow<Units> {
        return get(key = prefUnits, default = Units.METRIC.name).map {
            Units.valueOf(it)
        }
    }

    override fun getAppVersion(): String =
        "Version : ${BuildConfig.VERSION_NAME}-${BuildConfig.BUILD_TYPE}"

    override suspend fun setDefaultLocation(defaultLocation: DefaultLocation) {
        set(key = prefLatLng, value = "${defaultLocation.latitude}/${defaultLocation.longitude}")
    }

    override suspend fun getDefaultLocation(): Flow<DefaultLocation> {
        return get(
            key = prefLatLng,
            default = "$DEFAULT_LATITUDE/$DEFAULT_LONGITUDE"
        ).map { latlng ->
            val latLngList = latlng.split("/").map { it.toDouble() }
            DefaultLocation(latitude = latLngList[0], longitude = latLngList[1])
        }
    }

    override suspend fun getFormat(): Flow<TimeFormat> {
        return get(key = prefTimeFormat, default = TimeFormat.TWENTY_FOUR_HOUR.name).map {
            TimeFormat.valueOf(it)
        }
    }

    override suspend fun setFormat(format: TimeFormat) {
        set(key = prefTimeFormat, value = format.name)
    }

    // TODO Refactor to flow of list of excluded data
    override suspend fun getExcludedData(): Flow<String> = get(
        key = prefExcludedData,
        default = "${ExcludedData.MINUTELY.value},${ExcludedData.ALERTS.value}"
    )

    override suspend fun setExcludedData(excludedData: List<ExcludedData>) {
        val formattedData = excludedData.joinToString(separator = ",") { it.value }
        set(key = prefExcludedData, value = formattedData)
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

    companion object {
        // Düsseldorf
        const val DEFAULT_LONGITUDE = 6.773456
        const val DEFAULT_LATITUDE = 51.227741

        const val KEY_LANGUAGE = "language"
        const val KEY_UNITS = "units"
        const val KEY_LAT_LNG = "lat_lng"
        const val KEY_TIME_FORMAT = "time_formats"
        const val KEY_EXCLUDED_DATA = "excluded_data"
    }
}
