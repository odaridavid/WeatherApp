package com.github.odaridavid.weatherapp.ui.update

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.github.odaridavid.weatherapp.core.api.Logger
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
    private val logger: Logger,
    private val updateStateFactory: UpdateStateFactory,
) {

    private val appUpdateManager: AppUpdateManager by lazy {
        AppUpdateManagerFactory.create(context)
    }

    fun checkForUpdates(
        activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateManager.registerListener(updateStateFactory.getUpdateStateListener(
            onDownloaded = {
                // TODO: Notify the user that the update is ready to be installed.Don't do it this way.
                appUpdateManager.completeUpdate()
            }
        ))

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                update(
                    appUpdateManager = appUpdateManager,
                    appUpdateInfo = appUpdateInfo,
                    activityResultLauncher = activityResultLauncher,
                )
            }
        }.addOnFailureListener { exception ->
            logger.logException(UpdateAppException(exception))
        }
    }

    fun unregisterListeners() {
        appUpdateManager.unregisterListener(updateStateFactory.getUpdateStateListener())
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