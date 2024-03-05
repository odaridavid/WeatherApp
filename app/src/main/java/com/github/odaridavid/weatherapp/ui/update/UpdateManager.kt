package com.github.odaridavid.weatherapp.ui.update

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UpdateManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val updateStateFactory: UpdateStateFactory,
) {

    private val appUpdateManager: AppUpdateManager by lazy {
        AppUpdateManagerFactory.create(context)
    }

    fun checkForUpdates(
        activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>,
        onUpdateDownloaded: () -> Unit,
        onUpdateFailure: (Throwable) -> Unit,
    ) {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateManager.registerListener(
            updateStateFactory.getUpdateStateListener(
                onDownloaded = {
                    onUpdateDownloaded()
                }
            )
        )

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                update(
                    appUpdateManager = appUpdateManager,
                    appUpdateInfo = appUpdateInfo,
                    activityResultLauncher = activityResultLauncher,
                )
            }
        }.addOnFailureListener { exception ->
            onUpdateFailure(UpdateAppException(exception))
        }
    }

    fun unregisterListeners() {
        appUpdateManager.unregisterListener(updateStateFactory.getUpdateStateListener())
    }

    fun completeUpdate() {
        appUpdateManager.completeUpdate()
    }

    private fun update(
        appUpdateManager: AppUpdateManager,
        appUpdateInfo: AppUpdateInfo,
        activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            activityResultLauncher,
            AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
        )
    }
}
