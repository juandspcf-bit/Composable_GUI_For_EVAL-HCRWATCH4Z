package com.icxcu.adsmartbandapp.viewModels

import android.Manifest
import android.app.Application
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icxcu.adsmartbandapp.data.BasicBluetoothAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BluetoothScannerViewModel(var application: Application) : ViewModel() {
    var liveBasicBluetoothAdapter by mutableStateOf<List<BasicBluetoothAdapter>>(
        mutableListOf()
    )
/*    val liveBasicBluetoothAdapter = MutableLiveData<MutableList<BasicBluetoothAdapter>>(
        mutableListOf()
    )*/
    var liveStatusResults by mutableStateOf(-2)
    private var partialList: MutableList<BasicBluetoothAdapter> = mutableListOf()

    val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.d("Result", "onScanResult: $callbackType")

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
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    partialList.add(basicBluetoothAdapter)
                    liveBasicBluetoothAdapter = partialList.filter { it.name != "no name" }.toSet().toMutableList()

                }

            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                val basicBluetoothAdapter = BasicBluetoothAdapter(
                    result.device?.name ?: "no name",
                    result.device?.address ?: "no address"
                )
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    partialList.add(basicBluetoothAdapter)
                    liveBasicBluetoothAdapter = partialList.filter { it.name != "no name" }.toSet().toMutableList()

                }
            }
        }
    }




}