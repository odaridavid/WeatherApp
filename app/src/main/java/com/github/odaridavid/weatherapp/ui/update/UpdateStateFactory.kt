package com.github.odaridavid.weatherapp.ui.update

import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.InstallStatus
import javax.inject.Inject

class UpdateStateFactory @Inject constructor() {

    fun getUpdateStateListener(
        onDownloading: ((bytesDownloaded: Long, totalBytesToDownload: Long) -> Unit)? = null,
        onDownloaded: (() -> Unit)? = null,
    ) = InstallStateUpdatedListener { state ->
        when (state.installStatus()) {
            InstallStatus.DOWNLOADING -> {
                val bytesDownloaded = state.bytesDownloaded()
                val totalBytesToDownload = state.totalBytesToDownload()
                if (onDownloading != null) {
                    onDownloading(bytesDownloaded, totalBytesToDownload)
                }
                // Show update progress bar.
            }
            InstallStatus.DOWNLOADED -> {
                // Notify the user that the update is ready to be installed.
                if (onDownloaded != null) {
                    onDownloaded()
                }

            }
            InstallStatus.INSTALLING,
            InstallStatus.INSTALLED,
            InstallStatus.FAILED,
            InstallStatus.CANCELED,
            InstallStatus.PENDING,
            InstallStatus.UNKNOWN -> {
                // No-op
            }
            else -> {
                // No-op
            }
        }
    }

}
