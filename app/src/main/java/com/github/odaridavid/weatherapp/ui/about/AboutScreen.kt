package com.github.odaridavid.weatherapp.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.organism.TopNavigationBar
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer

@Composable
fun AboutScreen(
    onBackButtonClicked: () -> Unit,
) {
    Column {
        TopNavigationBar(
            onBackButtonClicked = onBackButtonClicked,
            title = stringResource(R.string.about_screen_title),
        )

        LibrariesContainer(
            Modifier.fillMaxSize()
        )
    }
}