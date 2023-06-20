package com.icxcu.adsmartbandapp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreConstants.LAST_DEVICE_ACCESSED_ADDRESS
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreConstants.LAST_DEVICE_ACCESSED_NAME
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreHelper
import com.icxcu.adsmartbandapp.screens.Routes
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SplashViewModel: ViewModel() {


    val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val todayLocalDateTime = LocalDateTime.now()
    val todayFormattedDate = todayLocalDateTime.format(myFormatObj)
    val yesterdayLocalDateTime = todayLocalDateTime.minusDays(1)
    val yesterdayFormattedDate = yesterdayLocalDateTime.format(myFormatObj)
    val pastYesterdayLocalDateTime = todayLocalDateTime.minusDays(2)
    val pastYesterdayFormattedDate = pastYesterdayLocalDateTime.format(myFormatObj)



    //var lastAccessedDevice by mutableStateOf(listOf<String>())
    var lastAccessedDevice = listOf<String>()

    init {

    }


    private val _stateFlow = MutableStateFlow(false)
    val stateFlow = _stateFlow.asStateFlow()

fun startDelay(preferenceDataStoreHelper: PreferenceDataStoreHelper){
    val deferred = viewModelScope.async {
        val prefRoute = mutableListOf<String>()
        val name = preferenceDataStoreHelper.getFirstPreference(LAST_DEVICE_ACCESSED_NAME, "")
        val address = preferenceDataStoreHelper.getFirstPreference(LAST_DEVICE_ACCESSED_ADDRESS, "")

        val route = if(name==""){
            prefRoute.add("")
            prefRoute.add(Routes.BluetoothScanner.route)
            prefRoute.toList()
        }else{
            prefRoute.add("")
            prefRoute.add(Routes.DataHome.route)
            prefRoute.add(name)
            prefRoute.add(address)
            prefRoute.toList()
        }
        route
    }

    viewModelScope.launch {
        lastAccessedDevice = deferred.await()
        delay(3000)
        _stateFlow.value = true

    }
}

    fun writeDataPreferences(preferenceDataStoreHelper: PreferenceDataStoreHelper, name: String, address:String) {
        viewModelScope.launch {
            preferenceDataStoreHelper.putPreference(LAST_DEVICE_ACCESSED_NAME, name)
            preferenceDataStoreHelper.putPreference(LAST_DEVICE_ACCESSED_ADDRESS, address)
        }

    }

}