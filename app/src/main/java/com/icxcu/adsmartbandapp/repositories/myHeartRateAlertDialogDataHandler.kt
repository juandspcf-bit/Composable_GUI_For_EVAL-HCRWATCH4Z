package com.icxcu.adsmartbandapp.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MyHeartRateAlertDialogDataHandler {

    private var jobHeartRate: Job? = null
    private val _sharedFlowHeartRate = MutableSharedFlow<Int>(
        replay = 10,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val sharedFlow = _sharedFlowHeartRate.asSharedFlow()

    fun getSharedFlowHeartRate(): SharedFlow<Int> {
        return sharedFlow
    }

    fun requestSmartWatchDataHeartRate(){

        jobHeartRate = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                _sharedFlowHeartRate.emit((90..100).random())
                delay(1000)
            }
        }

        jobHeartRate?.invokeOnCompletion {
            CoroutineScope(Dispatchers.Default).launch {
                _sharedFlowHeartRate.emit(0)
            }
        }
    }

    fun stopRequestSmartWatchDataHeartRate(){
        jobHeartRate?.cancel()
    }


}