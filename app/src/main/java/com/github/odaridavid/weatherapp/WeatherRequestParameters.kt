package com.github.odaridavid.weatherapp

enum class Units(val value:String){
    STANDARD("standard"),
    METRIC("metric"),
    IMPERIAL("imperial"),
}

enum class ExcludedData(val value:String){
    CURRENT("current"),
    HOURLY("hourly"),
    DAILY("daily"),
    MINUTELY("minutely"),
    ALERTS("alerts"),
}
