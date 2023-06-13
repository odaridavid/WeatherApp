package com.github.odaridavid.weatherapp.designsystem

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.github.odaridavid.weatherapp.R

@Composable
fun PermissionRationaleDialog(
    isDialogShown: MutableState<Boolean>,
    activityPermissionResult: ActivityResultLauncher<String>,
    showWeatherUI: MutableState<Boolean>
) {
    AlertDialog(
        onDismissRequest = { isDialogShown.value = false },
        title = {
            Text(text = stringResource(R.string.location_rationale_title))
        },
        text = {
            Text(text = stringResource(R.string.location_rationale_description))
        },
        buttons = {
            Button(onClick = {
                isDialogShown.value = false
                activityPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }) {
                Text(text = stringResource(R.string.location_rationale_button_grant))
            }
            Button(onClick = {
                isDialogShown.value = false
                showWeatherUI.value = false
            }) {
                Text(text = stringResource(R.string.location_rationale_button_deny))
            }
        }
    )
}
