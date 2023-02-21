package com.github.odaridavid.weatherapp.core.model

import androidx.annotation.StringRes
import com.github.odaridavid.weatherapp.R

/**
 * @project WeatherApp
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Mon 20 Feb 2023
 */
enum class TimeFormat(@StringRes val displayName: Int) {
    TWENTY_FOUR_HOUR(displayName = R.string.time_format_24_hour_display),
    TWELVE_HOUR(displayName = R.string.time_format_12_hour_display)
}