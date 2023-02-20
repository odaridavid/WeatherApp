package com.github.odaridavid.weatherapp.core.model

/**
 * @project WeatherApp
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Mon 20 Feb 2023
 */
enum class TimeFormat(val displayName: String) {
    TWENTY_FOUR_HOUR(displayName = "24 Hour (2400 hrs)"), TWELVE_HOUR(displayName = "12 Hour (AM/PM)")
}