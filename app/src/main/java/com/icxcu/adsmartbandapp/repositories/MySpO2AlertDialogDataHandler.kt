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

class MySpO2AlertDialogDataHandler {

    private var jobSpO2: Job? = null
    private val _sharedFlowSpO2 = MutableSharedFlow<Double>(
        replay = 10,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val sharedFlow = _sharedFlowSpO2.asSharedFlow()

    fun getSharedFlowSpO2(): SharedFlow<Double> {
        return sharedFlow
    }

    fun requestSmartWatchDataSpO2(){

        jobSpO2 = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                _sharedFlowSpO2.emit((94..96).random().toDouble())
                delay(1000)
            }
        }

        jobSpO2?.invokeOnCompletion {
            CoroutineScope(Dispatchers.Default).launch {
                _sharedFlowSpO2.emit(0.0)
            }
        }
    }

    fun stopRequestSmartWatchDataSpO2(){
        jobSpO2?.cancel()
    }

}