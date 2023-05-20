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

class MyTemperatureAlertDialogDataHandler {

    private val _circularProgressTemperatureStateFlow = MutableStateFlow(0)
    private val circularProgressTemperatureStateFlow = _circularProgressTemperatureStateFlow.asStateFlow()

    private fun increaseValueCircularProgressTemperature() {
        _circularProgressTemperatureStateFlow.value += 1
    }


    private fun clearValueCircularProgressTemperature() {
        _circularProgressTemperatureStateFlow.value = 0
    }

    fun getStateFlowCircularProgressTemperature(): StateFlow<Int> {
        return circularProgressTemperatureStateFlow
    }

    private var jobTemperature: Job? = null
    private val _sharedFlowTemperature = MutableSharedFlow<TemperatureData>(
        replay = 10,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val sharedFlowTemperature = _sharedFlowTemperature.asSharedFlow()



    fun getSharedFlowTemperature(): SharedFlow<TemperatureData> {
        return sharedFlowTemperature
    }

    fun requestSmartWatchDataTemperature(){

        jobTemperature = CoroutineScope(Dispatchers.Default).launch {
            repeat(10){
                increaseValueCircularProgressTemperature()
                delay(400)
            }

            _sharedFlowTemperature.emit(
                TemperatureData((35..36).random().toDouble(), (35..36).random().toDouble())
            )
        }

        jobTemperature?.invokeOnCompletion {
            clearValueCircularProgressTemperature()
        }

    }

    fun stopRequestSmartWatchDataTemperature(){
        clearValueCircularProgressTemperature()
        CoroutineScope(Dispatchers.Default).launch {
            _sharedFlowTemperature.emit(
                TemperatureData(0.0, 0.0)
            )
        }
        jobTemperature?.cancel()
    }

}