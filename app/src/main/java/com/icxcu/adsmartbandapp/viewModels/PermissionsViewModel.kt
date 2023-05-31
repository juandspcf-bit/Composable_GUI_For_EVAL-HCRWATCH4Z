package com.icxcu.adsmartbandapp.viewModels

import android.Manifest
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class PermissionsViewModel: ViewModel() {
    var permissionAccessFineLocationGranted by mutableStateOf(false)
    var permissionBluetoothScanGranted by mutableStateOf(false)
    var permissionBluetoothConnectGranted by mutableStateOf(false)
}

val permissionsRequired = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
    )
} else {
    listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
}