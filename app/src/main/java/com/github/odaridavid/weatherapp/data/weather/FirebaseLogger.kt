package com.github.odaridavid.weatherapp.data.weather

import com.github.odaridavid.weatherapp.api.Logger
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseLogger @Inject constructor() : Logger {
    override fun logException(throwable: Throwable) {
        Firebase.crashlytics.recordException(throwable)
    }
}
