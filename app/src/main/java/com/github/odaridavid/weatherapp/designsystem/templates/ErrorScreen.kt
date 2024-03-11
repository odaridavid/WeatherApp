package com.github.odaridavid.weatherapp.designsystem.templates

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.organism.ActionErrorMessage

@Composable
fun ColumnScope.ErrorScreen(errorMsgId: Int, onTryAgainClicked: () -> Unit) {
    Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.HALF))
    ActionErrorMessage(
        errorMessageId = errorMsgId,
        modifier = Modifier.padding(WeatherAppTheme.dimens.medium)
    ) {
        onTryAgainClicked()
    }
    Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.HALF))
}
