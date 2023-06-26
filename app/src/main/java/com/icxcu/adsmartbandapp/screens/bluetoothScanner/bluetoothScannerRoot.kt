package com.icxcu.adsmartbandapp.screens.bluetoothScanner

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.screens.MainNavigationNestedRoute
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.ScanningBluetoothAdapterStatus
import com.icxcu.adsmartbandapp.viewModels.SharedViewModel

@Composable
fun BluetoothScannerRoot(
    bluetoothScannerViewModel: BluetoothScannerViewModel,
    sharedViewModel: SharedViewModel,
    activity: Activity,
    navMainController: NavHostController,
    ) {

    val navLambdaToMainNavigationBar = remember(bluetoothScannerViewModel, navMainController) {
        { name: String, address: String ->
            if (bluetoothScannerViewModel.scanningBluetoothAdaptersStatus == ScanningBluetoothAdapterStatus.SCANNING_FINISHED_WITH_RESULTS
                || bluetoothScannerViewModel.scanningBluetoothAdaptersStatus == ScanningBluetoothAdapterStatus.SCANNING_FORCIBLY_STOPPED
            ) {

                navMainController.navigate(
                    MainNavigationNestedRoute.MainNavigationMainRoute().route
                )

                if (bluetoothScannerViewModel.bluetoothAdaptersList.isEmpty()) {
                    bluetoothScannerViewModel.scanningBluetoothAdaptersStatus =
                        ScanningBluetoothAdapterStatus.NO_SCANNING_WELCOME_SCREEN
                } else {
                    bluetoothScannerViewModel.scanningBluetoothAdaptersStatus =
                        ScanningBluetoothAdapterStatus.NO_SCANNING_WITH_RESULTS
                }

            }
        }
    }

    val getLiveBasicBluetoothAdapterList = remember(bluetoothScannerViewModel) {
        {
            bluetoothScannerViewModel.bluetoothAdaptersList
        }
    }

    val setLiveBasicBluetoothAdapterList = remember(bluetoothScannerViewModel) {
        {
            bluetoothScannerViewModel.bluetoothAdaptersList = mutableListOf()
            bluetoothScannerViewModel.partialList = mutableListOf()
        }
    }

    val getScanningBluetoothAdaptersStatus = remember(bluetoothScannerViewModel) {
        {
            bluetoothScannerViewModel.scanningBluetoothAdaptersStatus
        }
    }

    BluetoothScannerScreen(
        getLiveBasicBluetoothAdapterList,
        setLiveBasicBluetoothAdapterList,
        getScanningBluetoothAdaptersStatus,
        bluetoothScannerViewModel,
        sharedViewModel,
        activity,
        navLambdaToMainNavigationBar,
    )

}