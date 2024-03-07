package com.github.odaridavid.weatherapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
