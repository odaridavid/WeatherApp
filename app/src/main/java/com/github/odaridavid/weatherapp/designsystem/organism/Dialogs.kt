package com.github.odaridavid.weatherapp.designsystem.organism

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.LargeLabel
import com.github.odaridavid.weatherapp.designsystem.molecule.MediumBody
import com.github.odaridavid.weatherapp.designsystem.molecule.NegativeButton
import com.github.odaridavid.weatherapp.designsystem.molecule.PositiveButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionRationaleDialog(
    isDialogShown: MutableState<Boolean>,
    activityPermissionResult: ActivityResultLauncher<String>,
    showWeatherUI: MutableState<Boolean>
) {
    BasicAlertDialog(
        onDismissRequest = { isDialogShown.value = false },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column {
            LargeLabel(
                text = stringResource(R.string.location_rationale_title),
                modifier = Modifier.padding(
                    horizontal = WeatherAppTheme.dimens.medium,
                    vertical = WeatherAppTheme.dimens.small
                )
            )

            MediumBody(
                text = stringResource(R.string.location_rationale_description),
                modifier = Modifier.padding(WeatherAppTheme.dimens.medium)
            )

            Row(modifier = Modifier.padding(WeatherAppTheme.dimens.medium)) {
                PositiveButton(
                    text = stringResource(R.string.location_rationale_button_grant),
                    onClick = {
                        isDialogShown.value = false
                        activityPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                NegativeButton(
                    text = stringResource(R.string.location_rationale_button_deny),
                    onClick = {
                        isDialogShown.value = false
                        showWeatherUI.value = false
                    }
                )
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
            MediumBody(text = stringResource(R.string.update_available))
            PositiveButton(
                text = stringResource(R.string.install_update),
                onClick = { onConfirm() }
            )
        }
    }
}
