package com.github.odaridavid.weatherapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.github.odaridavid.weatherapp.MainViewIntent
import com.github.odaridavid.weatherapp.MainViewModel
import com.github.odaridavid.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel: MainViewModel by viewModels()
        val activityPermissionResult =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                mainViewModel.processIntent(MainViewIntent.GrantPermission(isGranted = isGranted))
            }

        setContent {
            val state = mainViewModel.state.collectAsState().value

            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CheckForPermissions(
                        onPermissionGranted = {
                            mainViewModel.processIntent(MainViewIntent.GrantPermission(isGranted = true))
                        },
                        onPermissionDenied = {
                            OnPermissionDenied(activityPermissionResult)
                        }
                    )

                    if (state.isPermissionGranted) {
                        WeatherAppScreensConfig(
                            navController = rememberNavController(),
                            homeViewModel = viewModel(),
                            settingsViewModel = viewModel()
                        )
                    } else {
                        RequiresPermissionsScreen()
                    }
                }
            }
        }
    }
}

