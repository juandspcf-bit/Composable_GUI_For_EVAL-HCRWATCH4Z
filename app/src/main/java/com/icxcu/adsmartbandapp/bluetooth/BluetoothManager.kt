package com.icxcu.adsmartbandapp.bluetooth

import android.app.Activity
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback

interface BluetoothManager {
    fun scanLocalBluetooth(activity: Activity): BluetoothLeScanner?
    fun enableBluetooth()
    fun scanLeDevice(activity: Activity, bluetoothLeScanner: BluetoothLeScanner?, leScanCallback: ScanCallback)
}