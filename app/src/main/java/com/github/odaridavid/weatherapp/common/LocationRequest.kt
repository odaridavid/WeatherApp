package com.github.odaridavid.weatherapp.common

import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

fun createLocationRequest(
    activity: Activity,
    locationRequestLauncher: ActivityResultLauncher<IntentSenderRequest>,
    onLocationRequestSuccessful: () -> Unit
) {
    val locationRequest = LocationRequest.Builder(30_000L)
        .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
        .build()

    val locationSettingsRequest = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

    val task: Task<LocationSettingsResponse> =
        LocationServices.getSettingsClient(activity)
            .checkLocationSettings(locationSettingsRequest.build())

    task.addOnCompleteListener { response ->
        try {
            val result = response.getResult(ApiException::class.java)
            val hasLocationAccess = result.locationSettingsStates?.isLocationUsable == true
            if (hasLocationAccess) {
                onLocationRequestSuccessful()
            }
        } catch (exception: ApiException) {
            when (exception.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    val resolvable = exception as ResolvableApiException
                    val intentSender =
                        IntentSenderRequest.Builder(resolvable.resolution).build()
                    locationRequestLauncher.launch(intentSender)
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    // Do nothing, location settings can't be changed
                }
            }
        }
    }
}
