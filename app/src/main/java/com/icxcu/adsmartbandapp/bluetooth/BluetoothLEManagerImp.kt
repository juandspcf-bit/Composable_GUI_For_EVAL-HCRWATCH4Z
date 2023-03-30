package com.icxcu.adsmartbandapp.bluetooth

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.icxcu.adsmartbandapp.REQUEST_ENABLE_BT
import com.icxcu.adsmartbandapp.screens.isPermissionGranted
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import kotlinx.coroutines.*

class BluetoothLEManagerImp(
    private var activity: Activity,
    private var mViewModel: BluetoothScannerViewModel
):com.icxcu.adsmartbandapp.bluetooth.BluetoothManager {
    private var scanning = false
    private var statusResults = -1
    private var jobs:Job? = null


    override fun scanLocalBluetooth(activity: Activity): BluetoothLeScanner? {



        val bluetoothManager: BluetoothManager? =
            ContextCompat.getSystemService(activity, BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter

        if (isPermissionGranted(
                activity,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        ) {
            Toast.makeText(activity, "BluetoothScanScreen permission granted", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(activity, "BluetoothScanScreen permission not granted", Toast.LENGTH_LONG).show()
        }


        bluetoothAdapter.let {

            activity.let {

                if (ContextCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
                    Toast.makeText(
                        activity,
                        "${bluetoothAdapter?.name},  ${bluetoothAdapter?.address}" ?: "No adapter",
                        Toast.LENGTH_LONG
                    ).show()
                    return bluetoothLeScanner
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Toast.makeText(
                            activity,
                            "Asking permission",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                    return null
                }
            }



        }

    }

    override fun enableBluetooth() {
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


    override fun scanLeDevice(bluetoothLeScanner: BluetoothLeScanner?, leScanCallback: ScanCallback) {
        if (!scanning) {

            val coroutineScope = CoroutineScope(Dispatchers.Main)

            if (jobs?.isActive == true)
                return

            jobs = coroutineScope.launch {
                delay(SCAN_PERIOD)
                if(isActive){
                    scanning = false
                    statusResults = 1
                    mViewModel.liveStatusResults = statusResults


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
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                        bluetoothLeScanner?.stopScan(leScanCallback)
                    }

                }


            }

            scanning = true
            statusResults = 0
            mViewModel.liveStatusResults = statusResults
            mViewModel.liveBasicBluetoothAdapter.value = mutableListOf()
            bluetoothLeScanner?.startScan(leScanCallback)

        } else {
            scanning = false
            statusResults = -1
            mViewModel.liveStatusResults = statusResults
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
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

                bluetoothLeScanner?.stopScan(leScanCallback)
            }
        }
    }

}