package com.github.odaridavid.weatherapp.ui

import android.app.Activity
import android.content.IntentSender
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

fun createLocationRequest(
    activity: Activity,
    activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>,
    onLocationSettingsResponseReceived: () -> Unit
) {
    val locationRequestHighAccuracy =
        LocationRequest.Builder(30_000L)
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .build()

    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequestHighAccuracy)

    val task: Task<LocationSettingsResponse> =
        LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())

    task.addOnCompleteListener { lsr: Task<LocationSettingsResponse> ->
        try {
            val response = lsr.getResult(ApiException::class.java)
            val hasLocationAccess = response.locationSettingsStates?.isLocationUsable == true

            if (hasLocationAccess)
                onLocationSettingsResponseReceived()

        } catch (e: ApiException) {
            when (e.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    try {
                        val resolvable: ResolvableApiException = e as ResolvableApiException

                        val intentSender =
                            IntentSenderRequest.Builder(resolvable.resolution).build()
                        activityResultLauncher.launch(intentSender)
                    } catch (e: IntentSender.SendIntentException) {
                        // Ignore the error.
                    } catch (e: ClassCastException) {
                        // Ignore, should be an impossible error.
                    }
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    // Location settings are not satisfied. However, we have no way to fix the
                    // settings so we won't show the dialog.
                }
            }
        }
    }
}
