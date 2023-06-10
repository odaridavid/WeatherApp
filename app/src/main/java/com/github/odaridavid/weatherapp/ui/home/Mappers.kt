package com.github.odaridavid.weatherapp.ui.home

import androidx.annotation.StringRes
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.core.ErrorType

@StringRes
fun mapErrorTypeToResourceId(errorType: ErrorType): Int = when (errorType) {
    ErrorType.SERVER -> R.string.error_server
    ErrorType.GENERIC -> R.string.error_generic
    ErrorType.IO_CONNECTION -> R.string.error_connection
    ErrorType.UNAUTHORIZED -> R.string.error_unauthorized
    ErrorType.CLIENT -> R.string.error_client
}
