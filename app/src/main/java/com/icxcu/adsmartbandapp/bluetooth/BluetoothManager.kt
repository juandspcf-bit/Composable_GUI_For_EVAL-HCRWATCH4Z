package com.icxcu.adsmartbandapp.bluetooth

import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback

interface BluetoothManager {
    fun scanLocalBluetooth(): BluetoothLeScanner?
    fun enableBluetooth()
    fun scanLeDevice(bluetoothLeScanner: BluetoothLeScanner?, leScanCallback: ScanCallback)
}