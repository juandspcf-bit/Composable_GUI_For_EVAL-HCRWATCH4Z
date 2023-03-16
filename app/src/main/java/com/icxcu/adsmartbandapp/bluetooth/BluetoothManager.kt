package com.icxcu.adsmartbandapp.bluetooth

import android.bluetooth.le.BluetoothLeScanner

interface BluetoothManager {
    fun scanLocalBluetooth(): BluetoothLeScanner?
    fun enableBluetooth()
    fun scanLeDevice(bluetoothLeScanner: BluetoothLeScanner?)
}