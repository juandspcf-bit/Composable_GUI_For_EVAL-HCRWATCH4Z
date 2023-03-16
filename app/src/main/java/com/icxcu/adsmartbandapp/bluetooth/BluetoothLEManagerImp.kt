package com.icxcu.adsmartbandapp.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.icxcu.adsmartbandapp.MainActivity
import com.icxcu.adsmartbandapp.REQUEST_ENABLE_BT
import com.icxcu.adsmartbandapp.data.BasicBluetoothAdapter
import com.icxcu.adsmartbandapp.viewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BluetoothLEManagerImp(
    private var mainActivity: MainActivity,
    private var mViewModel: MainViewModel
):com.icxcu.adsmartbandapp.bluetooth.BluetoothManager {
    private var scanning = false
    private var statusResults = -1
    private var partialList: MutableList<BasicBluetoothAdapter> = mutableListOf()

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (ActivityCompat.checkSelfPermission(
                        mainActivity,
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


                }

            }
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                val basicBluetoothAdapter = BasicBluetoothAdapter(
                    result.device?.name ?: "no name",
                    result.device?.address ?: "no address"
                )

                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    partialList.add(basicBluetoothAdapter)
                }
            }
        }
    }

    override fun scanLocalBluetooth(): BluetoothLeScanner? {
        val bluetoothManager: BluetoothManager? =
            ContextCompat.getSystemService(mainActivity, BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter
        bluetoothAdapter.let {
            if (ActivityCompat.checkSelfPermission(
                    mainActivity,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
                Toast.makeText(
                    mainActivity,
                    "${bluetoothAdapter?.name},  ${bluetoothAdapter?.address}" ?: "No adapter",
                    Toast.LENGTH_LONG
                ).show()
                return bluetoothLeScanner
            } else {
                return null
            }

        }

    }

    override fun enableBluetooth() {
        val bluetoothManager: BluetoothManager? =
            ContextCompat.getSystemService(mainActivity, BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityCompat.startActivityForResult(
                mainActivity,
                enableBtIntent,
                REQUEST_ENABLE_BT,
                null
            )
        }

    }


    private val SCAN_PERIOD: Long = 10000

    override fun scanLeDevice(bluetoothLeScanner: BluetoothLeScanner?) {
        if (!scanning) {

            val coroutineScope = CoroutineScope(Dispatchers.Main)

            coroutineScope.launch {
                delay(SCAN_PERIOD)
                scanning = false
                statusResults = 1
                mViewModel.liveStatusResults.value = statusResults
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (ActivityCompat.checkSelfPermission(
                            mainActivity,
                            Manifest.permission.BLUETOOTH_SCAN
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return@launch
                    }
                    bluetoothLeScanner?.stopScan(leScanCallback)
                    mViewModel.liveBasicBluetoothAdapter.value = partialList.toSet().toMutableList()

                }
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    bluetoothLeScanner?.stopScan(leScanCallback)
                    partialList.forEach {
                        Log.d("BluetoothData", "onScanResult: ${it.name} ${it.address}")
                    }
                    mViewModel.liveBasicBluetoothAdapter.value = partialList.toSet().toMutableList()

                }

            }

            scanning = true
            statusResults = 0
            mViewModel.liveStatusResults.value = statusResults
            bluetoothLeScanner?.startScan(leScanCallback)

        } else {
            scanning = false
            statusResults = -1
            mViewModel.liveStatusResults.value = statusResults
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (ActivityCompat.checkSelfPermission(
                        mainActivity,
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