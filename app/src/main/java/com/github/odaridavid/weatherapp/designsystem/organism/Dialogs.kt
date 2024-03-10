package com.github.odaridavid.weatherapp.designsystem.organism

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.ConfirmButton
import com.github.odaridavid.weatherapp.designsystem.molecule.SettingOptionRadioButton

// TODO Adapt per screen
private val SETTING_DIALOG_MAX_HEIGHT = 200.dp
private val SETTING_DIALOG_MIN_HEIGHT = 0.dp

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
            Button(
                onClick = {
                    isDialogShown.value = false
                    activityPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                }
            ) {
                Text(text = stringResource(R.string.location_rationale_button_grant))
            }
            Button(
                onClick = {
                    isDialogShown.value = false
                    showWeatherUI.value = false
                }
            ) {
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
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colors.surface)
        ) {
            val (dialog, confirmButton) = createRefs()
            LazyColumn(
                modifier = Modifier
                    .heightIn(SETTING_DIALOG_MIN_HEIGHT, SETTING_DIALOG_MAX_HEIGHT)
                    .fillMaxWidth()
                    .constrainAs(dialog) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                items(items) { item ->
                    content(item)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .constrainAs(confirmButton) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(dialog.bottom, margin = 16.dp)
                    }
            ) {
                Spacer(modifier = Modifier.weight(1f))
                ConfirmButton(
                    modifier = Modifier
                        .padding(WeatherAppTheme.dimens.medium),
                    onClick = {
                        onConfirm()
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun UpdateDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box {
            Text(text = stringResource(R.string.update_available))
            Button(onClick = { onConfirm() }) {
                Text(text = stringResource(R.string.install_update))
            }
        }
    }
}

@Preview
@Composable
fun SettingOptionsDialogPreview() {
    SettingOptionsDialog(
        items = listOf("Celsius", "Fahrenheit"),
        onDismiss = {},
        onConfirm = {},
    ) { unit ->
        SettingOptionRadioButton(
            text = unit,
            selectedOption = "Celsius",
            onOptionSelected = {}
        )
    }
}
