package com.github.odaridavid.weatherapp.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(1.0f))
        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.weight(1.0f))
    }
}
