package com.icxcu.adsmartbandapp

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.icxcu.adsmartbandapp.bluetooth.BluetoothLEManagerImp
import com.icxcu.adsmartbandapp.data.BluetoothLEManagerImp2
import com.icxcu.adsmartbandapp.viewModels.AppMainViewModel
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel

class MainActivityXml : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_xml)
        val viewModelProvider = ViewModelProvider(this@MainActivityXml)[AppMainViewModel::class.java]
        var bluetoothLEManager =
            BluetoothLEManagerImp2(this@MainActivityXml, viewModelProvider, requestMultiplePermissions)
        val buttonPermissions = findViewById<Button>(R.id.buttonEnablePer)
        buttonPermissions.setOnClickListener { setupPermissions() }
        val buttonScan = findViewById<Button>(R.id.buttonScan)
        buttonScan.setOnClickListener {

            val scanLocalBluetooth = bluetoothLEManager.scanLocalBluetooth()
            bluetoothLEManager.scanLeDevice(
                scanLocalBluetooth,
                viewModelProvider.leScanCallback
            )
        }

    }

    private val TAG = "PermissionDemo"
    private val RECORD_REQUEST_CODE = 101
    private fun setupPermissions() {
        var permission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest_ACCESS_COARSE_LOCATION()
        }

        permission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest_ACCESS_FINE_LOCATION()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

/*            permission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_SCAN
            )

            if (permission != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission to record denied")
                makeRequest_BLUETOOTH_SCAN()
            }
            permission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_CONNECT
            )

            if (permission != PackageManager.PERMISSION_GRANTED) {
                Log.i("BLUETOOTH_CONNECT", "Permission to bluetooth connect denied")
                makeRequest_BLUETOOTH_CONNECT()
            }*/
        }
        makeRequest_BLUETOOTH_CONNECT()

    }


    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.d("test006", "${it.key} = ${it.value}")
            }}

    private fun makeRequest_BLUETOOTH_CONNECT() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
/*            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT),
                512
            )*/

            requestMultiplePermissions.launch(arrayOf(
                android.Manifest.permission.BLUETOOTH_SCAN,
                android.Manifest.permission.BLUETOOTH_CONNECT))
        }
    }

    private fun makeRequest_BLUETOOTH_SCAN() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.BLUETOOTH_SCAN),
                RECORD_REQUEST_CODE
            )
        }
    }

    private fun makeRequest_ACCESS_COARSE_LOCATION() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
            RECORD_REQUEST_CODE
        )
    }

    private fun makeRequest_ACCESS_FINE_LOCATION() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            RECORD_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] !=
                    PackageManager.PERMISSION_GRANTED
                ) {
                    Log.i(TAG, "Permission has been denied by user")
                }else
                {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
            512->{
                if (grantResults.isEmpty() || grantResults[0] !=
                    PackageManager.PERMISSION_GRANTED
                ) {
                    Log.i("BLUETOOTH_CONNECT", "Permission bluetooth connect has been denied by user")
                }else
                {
                    Log.i("BLUETOOTH_CONNECT", "Permission bluetooth connect has been granted by user")
                }
            }
        }

    }

}