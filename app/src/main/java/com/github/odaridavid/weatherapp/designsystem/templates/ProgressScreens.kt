package com.github.odaridavid.weatherapp.designsystem.templates

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme

@Composable
fun LoadingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.FULL))
        CircularProgressIndicator(
            modifier = Modifier
                .padding(WeatherAppTheme.dimens.medium)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.FULL))
    }
}
