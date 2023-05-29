package com.icxcu.adsmartbandapp.repositories

import android.util.Log
import com.icxcu.adsmartbandapp.data.MockData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SWRepository {
    private val _sharedStepsFlow = MutableSharedFlow<Values>(
        replay = 30,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sharedStepsFlow = _sharedStepsFlow.asSharedFlow()




    fun requestSmartWatchData(name: String = "", macAddress: String = "") {
        Log.d("Status", "Reading Data")
        CoroutineScope(Dispatchers.Default).launch {
            delay(5000)
            _sharedStepsFlow.emit(MockData.valuesToday)
            delay(5000)
            _sharedStepsFlow.emit(MockData.valuesYesterday)
        }
    }
}