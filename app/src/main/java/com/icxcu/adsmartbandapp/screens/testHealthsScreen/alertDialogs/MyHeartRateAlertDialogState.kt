package com.icxcu.adsmartbandapp.screens.testHealthsScreen.alertDialogs

import androidx.compose.runtime.Composable
import com.icxcu.adsmartbandapp.R

@Composable
fun MyHeartRateAlertDialogState(
    imageResource: Int = R.drawable.ic_launcher_foreground,
    requestRealTimeHeartRate: () -> Unit,
    getRealTimeHeartRate: () -> Int,
    stopRequestRealTimeHeartRate: () -> Unit,
    setDialogStatus: (Boolean) -> Unit

){



    MyHeartRateAlertDialogContent(
        imageResource = imageResource,
        requestRealTimeHeartRate,
        getRealTimeHeartRate,
        stopRequestRealTimeHeartRate,
        setDialogStatus
    )

}