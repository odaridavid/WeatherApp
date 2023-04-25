package com.github.odaridavid.weatherapp.ui

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
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
