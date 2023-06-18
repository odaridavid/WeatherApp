package com.github.odaridavid.weatherapp.designsystem

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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

@Composable
fun <T> SettingOptionsDialog(
    items: List<T>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable (T) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.surface)
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                LazyColumn {
                    items(items) { item ->
                        content(item)
                    }
                }
            }
            ConfirmButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                onClick = { onConfirm() }
            )
        }
    }
}
