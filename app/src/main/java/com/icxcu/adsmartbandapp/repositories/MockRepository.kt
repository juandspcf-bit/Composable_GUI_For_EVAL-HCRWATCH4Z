package com.icxcu.adsmartbandapp.repositories

import com.icxcu.adsmartbandapp.data.MockData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MockRepository {
    private val _sharedStepsFlow = MutableSharedFlow<Values>(
        replay = 30,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sharedStepsFlow = _sharedStepsFlow.asSharedFlow()

    fun requestStepsData() {
        CoroutineScope(Dispatchers.Default).launch {
            _sharedStepsFlow.emit(MockData.values)
        }
    }

}

data class Values(
    var stepList: List<Int>,
    var distanceList: List<Double>,
    var caloriesList: List<Double>,
    var bloodPressureValuesList: List<List<Double>>
)