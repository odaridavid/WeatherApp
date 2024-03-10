package com.github.odaridavid.weatherapp.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import com.github.odaridavid.weatherapp.designsystem.organism.PermissionRationaleDialog
import java.util.Locale

private const val NO_OF_ADDRESSES = 1
fun Context.getCityName(longitude: Double, latitude: Double, onAddressReceived: (Address) -> Unit) {
    val geoCoder = Geocoder(this, Locale.getDefault())

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geoCoder.getFromLocation(latitude, longitude, NO_OF_ADDRESSES) { addresses ->
            if (addresses.isNotEmpty()) {
                 onAddressReceived(addresses[0])
            }
        }
    } else {
        val addresses = geoCoder.getFromLocation(latitude, longitude, NO_OF_ADDRESSES)
        if (addresses?.isNotEmpty() == true) {
              onAddressReceived(addresses[0])
        }
    }
}

@Composable
fun Activity.OnPermissionDenied(
    activityPermissionResult: ActivityResultLauncher<String>,
) {
    val showWeatherUI = remember { mutableStateOf(false) }
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

@Composable
fun Context.CheckForPermissions(
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
