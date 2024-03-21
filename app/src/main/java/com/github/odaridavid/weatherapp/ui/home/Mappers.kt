package com.github.odaridavid.weatherapp.ui.home

import androidx.annotation.StringRes
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.model.ErrorType

@StringRes
fun ErrorType.toResourceId(): Int = when (this) {
    ErrorType.SERVER -> R.string.error_server
    ErrorType.GENERIC -> R.string.error_generic
    ErrorType.IO_CONNECTION -> R.string.error_connection
    ErrorType.CLIENT -> R.string.error_client
}
