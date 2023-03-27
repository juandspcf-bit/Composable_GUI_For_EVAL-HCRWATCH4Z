package com.icxcu.adsmartbandapp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class PermissionsViewModel: ViewModel() {
    var permissionAccessFineLocationGranted by mutableStateOf(false)
    var permissionBluetoothConnectGranted by mutableStateOf(false)
    }