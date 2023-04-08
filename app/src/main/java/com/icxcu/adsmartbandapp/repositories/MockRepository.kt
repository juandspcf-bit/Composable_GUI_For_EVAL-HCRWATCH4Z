package com.icxcu.adsmartbandapp.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
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

            val stepValue = listOf(
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

            var disValue = listOf(
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.109,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.185,
                0.077,
                1.127,
                2.512,
                1.849,
                0.185,
                0.249,
                0.053,
                0.02,
                0.058,
                0.0,
                0.0,
                0.0,
                0.039,
                0.061,
                0.788,
                1.201,
                0.131,
                0.193,
                0.03,
                0.04,
                0.062,
                0.0,
                0.009,
                0.0,
                0.014,
                0.034,
                0.204,
                0.0
            )

            var caloriesValues = listOf(
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                7.1,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                12.1,
                5.1,
                73.7,
                164.2,
                121.0,
                12.0,
                16.4,
                3.4,
                1.3,
                3.8,
                0.0,
                0.0,
                0.0,
                2.6,
                3.9,
                51.6,
                78.6,
                8.5,
                12.7,
                1.9,
                2.6,
                4.1,
                0.0,
                0.6,
                0.0,
                0.8,
                2.3,
                13.3,
                0.0
            )

            val values = Values(stepValue, disValue, caloriesValues)
            _sharedStepsFlow.emit(values)
        }
    }

}

data class Values(
    var stepList: List<Int>,
    var distanceList: List<Double>,
    var caloriesList: List<Double>
)