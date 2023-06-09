package com.icxcu.adsmartbandapp.viewModels

import android.Manifest
import android.app.Application
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.icxcu.adsmartbandapp.data.BasicBluetoothAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BluetoothScannerViewModel(var application: Application) : ViewModel() {
    var bluetoothAdaptersList by mutableStateOf<List<BasicBluetoothAdapter>>(
        mutableListOf()
    )
    var scanningBluetoothAdaptersStatus by mutableStateOf(ScanningBluetoothAdapterStatus.NO_SCANNING_WELCOME_SCREEN)
    var partialList: MutableList<BasicBluetoothAdapter> = mutableListOf()

    val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (ActivityCompat.checkSelfPermission(
                        application,
                        Manifest.permission.BLUETOOTH_SCAN
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                val basicBluetoothAdapter = BasicBluetoothAdapter(
                    result.device?.name ?: "no name",
                    result.device?.address ?: "no address"
                )

                Log.d("Result", "onScanResult: $basicBluetoothAdapter")
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    partialList.add(basicBluetoothAdapter)
                    bluetoothAdaptersList = partialList.filter { it.name != "no name" }.toSet().toMutableList()

                }

            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                val basicBluetoothAdapter = BasicBluetoothAdapter(
                    result.device?.name ?: "no name",
                    result.device?.address ?: "no address"
                )
                Log.d("Result", "onScanResult: $basicBluetoothAdapter")
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    partialList.add(basicBluetoothAdapter)
                    bluetoothAdaptersList = partialList.filter { it.name != "no name" }.toSet().toMutableList()

                }
            }
        }
    }
}



enum class ScanningBluetoothAdapterStatus {
    SCANNING,
    SCANNING_FINISHED_WITH_RESULTS,
    SCANNING_FORCIBLY_STOPPED,
    NO_SCANNING_WELCOME_SCREEN,
    NO_SCANNING_WITH_RESULTS
}