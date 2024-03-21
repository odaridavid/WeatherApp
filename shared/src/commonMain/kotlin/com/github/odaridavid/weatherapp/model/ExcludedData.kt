package com.github.odaridavid.weatherapp.model

enum class ExcludedData(val value: String, val id: Int) {
    CURRENT("current", 0),
    HOURLY("hourly", 1),
    DAILY("daily", 2),
    MINUTELY("minutely", 3),
    ALERTS("alerts", 4),
    NONE("none", -1),
}
