package com.icxcu.adsmartbandapp.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MockRepository {
    private val _sharedStepsFlow = MutableSharedFlow<List<Int>>(replay = 30,
        onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val sharedStepsFlow = _sharedStepsFlow.asSharedFlow()

    fun requestStepsData(){
        CoroutineScope(Dispatchers.Default).launch {

            var stepValue = listOf(
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                141,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                239,
                110,
                1455,
                3177,
                2404,
                246,
                315,
                65,
                25,
                74,
                0,
                0,
                0,
                47,
                77,
                1025,
                1600,
                164,
                252,
                37,
                51,
                79,
                0,
                11,
                0,
                17,
                43,
                311,
                0
            )

            _sharedStepsFlow.emit(stepValue)
        }
    }

}