package com.icxcu.adsmartbandapp.viewModels

import android.Manifest
import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.icxcu.adsmartbandapp.REQUEST_ENABLE_BT
import com.icxcu.adsmartbandapp.data.BasicBluetoothAdapter
import com.icxcu.adsmartbandapp.screens.bluetoothScanner.BluetoothListScreenNavigationStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class BluetoothScannerViewModel(var application: Application) : ViewModel() {

    var selectedBluetoothDeviceName = ""
    var selectedBluetoothDeviceAddress = ""

    var stateBluetoothListScreenNavigationStatus by mutableStateOf(
        BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_BLUETOOTH_SCREEN
    )

    var bluetoothAdaptersList by mutableStateOf<List<BasicBluetoothAdapter>>(
        mutableListOf()
    )
    var scanningBluetoothAdaptersStatus by mutableStateOf(ScanningBluetoothAdapterStatus.NO_SCANNING_WELCOME_SCREEN)
    var partialList: MutableList<BasicBluetoothAdapter> = mutableListOf()

    private val leScanCallback: ScanCallback = object : ScanCallback() {
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




    private var scanning = false
    private var statusResults = ScanningBluetoothAdapterStatus.SCANNING_FORCIBLY_STOPPED
    private var jobs: Job? = null


    fun scanLocalBluetooth(activity: Activity): BluetoothLeScanner? {


        val bluetoothManager: BluetoothManager? =
            ContextCompat.getSystemService(activity, BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter

        bluetoothAdapter.let {

            activity.let {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    return if (ContextCompat.checkSelfPermission(
                            activity,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        bluetoothAdapter?.bluetoothLeScanner
                    } else {
                        null
                    }
                }

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    return bluetoothAdapter?.bluetoothLeScanner
                } else {
                    return null
                }

            }

        }

    }

    fun enableBluetooth(
        activity: Activity,
    ) {
        val bluetoothManager: BluetoothManager? =
            ContextCompat.getSystemService(activity, BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityCompat.startActivityForResult(
                activity,
                enableBtIntent,
                REQUEST_ENABLE_BT,
                null
            )
        }

    }


    private val SCAN_PERIOD: Long = 10000


    fun scanLeDevice(
        activity: Activity,
        bluetoothLeScanner: BluetoothLeScanner?,
    ) {
        if (!scanning) {
            val coroutineScope = CoroutineScope(Dispatchers.Main)

            if (jobs?.isActive == true)
                return

            jobs = coroutineScope.launch {
                delay(SCAN_PERIOD)
                if (isActive) {
                    scanning = false
                    statusResults = ScanningBluetoothAdapterStatus.SCANNING_FINISHED_WITH_RESULTS
                    scanningBluetoothAdaptersStatus = statusResults


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        if (ContextCompat.checkSelfPermission(
                                activity,
                                Manifest.permission.BLUETOOTH_SCAN
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return@launch
                        }
                        bluetoothLeScanner?.stopScan(leScanCallback)
                    }
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                        bluetoothLeScanner?.stopScan(leScanCallback)
                    }

                }


            }

            scanning = true
            statusResults = ScanningBluetoothAdapterStatus.SCANNING
            scanningBluetoothAdaptersStatus = statusResults
            bluetoothAdaptersList = mutableListOf()
            bluetoothLeScanner?.startScan(leScanCallback)

        } else {
            scanning = false
            statusResults = ScanningBluetoothAdapterStatus.SCANNING_FORCIBLY_STOPPED
            scanningBluetoothAdaptersStatus = statusResults
            jobs?.cancel()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (ContextCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.BLUETOOTH_SCAN
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }

                bluetoothLeScanner?.stopScan(leScanCallback)

            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                bluetoothLeScanner?.stopScan(leScanCallback)
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