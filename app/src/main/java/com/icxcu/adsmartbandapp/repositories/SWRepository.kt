package com.icxcu.adsmartbandapp.repositories

import android.util.Log
import com.icxcu.adsmartbandapp.data.MockData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.cancel
import kotlinx.coroutines.launch

class SWRepository {
    private val _sharedStepsFlow = MutableSharedFlow<Values>(
        replay = 2,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val sharedStepsFlow = _sharedStepsFlow.asSharedFlow()

    lateinit var jobSW:Job




    fun requestSmartWatchData(name: String = "", macAddress: String = "") {
        Log.d("DATAX", "requestSmartWatchDataRep: ")
        jobSW = CoroutineScope(Dispatchers.Default).launch {
            delay(5000)
            _sharedStepsFlow.emit(MockData.valuesToday)
            delay(5000)
            _sharedStepsFlow.emit(MockData.valuesYesterday)
            Log.d("DATAX", "requestSmartWatchDataRep:  CoroutineScope(Dispatchers.Default) END")
            jobSW.cancel()
        }

        jobSW.invokeOnCompletion {
            Log.d("DATAX", "requestSmartWatchData: cancelled")
        }
    }
}