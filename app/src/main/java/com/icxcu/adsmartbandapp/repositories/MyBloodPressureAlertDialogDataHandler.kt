package com.icxcu.adsmartbandapp.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyBloodPressureAlertDialogDataHandler {

    private val _circularProgressBloodPressureStateFlow = MutableStateFlow(0)
    private val circularProgressBloodPressureStateFlow = _circularProgressBloodPressureStateFlow.asStateFlow()

    private fun increaseValueCircularProgressBloodPressure() {
        _circularProgressBloodPressureStateFlow.value += 1
    }

    private fun clearValueCircularProgressBloodPressure() {
        _circularProgressBloodPressureStateFlow.value = 0
    }

    fun getStateFlowCircularProgressBloodPressure(): StateFlow<Int> {
        return circularProgressBloodPressureStateFlow
    }

    private var jobBloodPressure: Job? = null
    private val _sharedFlowBloodPressure = MutableSharedFlow<BloodPressureData>(
        replay = 10,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val sharedFlowBloodPressure = _sharedFlowBloodPressure.asSharedFlow()



    fun getSharedFlowBloodPressure(): SharedFlow<BloodPressureData> {
        return sharedFlowBloodPressure
    }

    fun requestSmartWatchDataBloodPressure(){

        jobBloodPressure = CoroutineScope(Dispatchers.Default).launch {
            repeat(10){
                increaseValueCircularProgressBloodPressure()
                delay(400)
            }

            _sharedFlowBloodPressure.emit(
                BloodPressureData((120..140).random(), (75..85).random())
            )
        }

        jobBloodPressure?.invokeOnCompletion {
            clearValueCircularProgressBloodPressure()
        }

    }

    fun stopRequestSmartWatchDataBloodPressure(){
        clearValueCircularProgressBloodPressure()
        CoroutineScope(Dispatchers.Default).launch {
            _sharedFlowBloodPressure.emit(
                BloodPressureData(0, 0)
            )
        }
        jobBloodPressure?.cancel()

    }




}