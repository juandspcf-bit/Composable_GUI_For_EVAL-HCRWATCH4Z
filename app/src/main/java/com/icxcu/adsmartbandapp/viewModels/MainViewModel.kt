package com.icxcu.adsmartbandapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icxcu.adsmartbandapp.data.BasicBluetoothAdapter

class MainViewModel:ViewModel() {
    val liveBasicBluetoothAdapter = MutableLiveData<MutableList<BasicBluetoothAdapter>>(
        mutableListOf())
    val liveStatusResults = MutableLiveData<Int>(-1)

}