package com.icxcu.adsmartbandapp.screens.navigationGraphs

import android.app.Activity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.bluetooth.BluetoothManager
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreHelper
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.MainNavigationViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel

fun NavGraphBuilder.bluetoothScannerGraph(
    bluetoothScannerViewModel: BluetoothScannerViewModel,
    bluetoothLEManager: BluetoothManager,
    activity: Activity,
    splashViewModel: SplashViewModel,
    mainNavigationViewModel: MainNavigationViewModel,
    preferenceDataStoreHelper: PreferenceDataStoreHelper,
    navMainController: NavHostController,
){

}