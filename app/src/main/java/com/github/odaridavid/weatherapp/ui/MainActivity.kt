package com.github.odaridavid.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.github.odaridavid.weatherapp.MainViewIntent
import com.github.odaridavid.weatherapp.MainViewModel
import com.github.odaridavid.weatherapp.R
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
                    val showWeatherUI = remember { mutableStateOf(false) }
                    CheckForPermissions(
                        onPermissionGranted = {
                            mainViewModel.processIntent(MainViewIntent.GrantPermission(isGranted = true))
                        },
                        onPermissionDenied = {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                                val isDialogShown = remember { mutableStateOf(true) }
                                if (isDialogShown.value) {
                                    PermissionRationaleDialog(
                                        isDialogShown,
                                        activityPermissionResult,
                                        showWeatherUI
                                    )
                                }
                            } else {
                                activityPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                            }
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

    @Composable
    private fun PermissionRationaleDialog(
        isDialogShown: MutableState<Boolean>,
        activityPermissionResult: ActivityResultLauncher<String>,
        showWeatherUI: MutableState<Boolean>
    ) {
        AlertDialog(
            onDismissRequest = { isDialogShown.value = false },
            title = {
                Text(text = getString(R.string.location_rationale_title))
            },
            text = {
                Text(text = getString(R.string.location_rationale_description))
            },
            buttons = {
                Button(onClick = {
                    isDialogShown.value = false
                    activityPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                }) {
                    Text(text = getString(R.string.location_rationale_button_grant))
                }
                Button(onClick = {
                    isDialogShown.value = false
                    showWeatherUI.value = false
                }) {
                    Text(text = getString(R.string.location_rationale_button_deny))
                }
            }
        )
    }

    @Composable
    private fun RequiresPermissionsScreen() {
        Column {
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = stringResource(R.string.location_no_permission_screen_description),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }

    @Composable
    private fun CheckForPermissions(
        onPermissionGranted: @Composable () -> Unit,
        onPermissionDenied: @Composable () -> Unit
    ) {
        when (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )) {
            PackageManager.PERMISSION_GRANTED -> {
                onPermissionGranted()
            }
            PackageManager.PERMISSION_DENIED -> {
                onPermissionDenied()
            }
        }
    }
}

