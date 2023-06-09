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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SWRepository {
    private val _sharedStepsFlow = MutableSharedFlow<Values>(
        replay = 0,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val sharedStepsFlow = _sharedStepsFlow.asSharedFlow()

    var jobSW:Job? = null




    fun requestSmartWatchData(name: String = "", macAddress: String = "") {
        Log.d("DATAX", "requestSmartWatchDataRep: ")


        jobSW = CoroutineScope(Dispatchers.Default).launch {
            val myFormatObj:DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val todayLocalDateTime:LocalDateTime = LocalDateTime.now()
            var todayFormattedDate:String = todayLocalDateTime.format(myFormatObj)
            val yesterdayLocalDateTime:LocalDateTime = todayLocalDateTime.minusDays(1)
            var yesterdayFormattedDate:String = yesterdayLocalDateTime.format(myFormatObj)
            val pastYesterdayLocalDateTime:LocalDateTime = todayLocalDateTime.minusDays(2)
            var pastYesterdayFormattedDate:String = pastYesterdayLocalDateTime.format(myFormatObj)


            delay(5000)

            val todayValues = Values(
                MockData.valuesToday.stepList,
                MockData.valuesToday.distanceList,
                MockData.valuesToday.caloriesList,
                MockData.valuesToday.heartRateList,
                MockData.valuesToday.systolicList,
                MockData.valuesToday.diastolicList,
                todayFormattedDate,
            )
            _sharedStepsFlow.emit(todayValues)

            delay(5000)
            val yesterdayValues = Values(
                MockData.valuesYesterday.stepList,
                MockData.valuesYesterday.distanceList,
                MockData.valuesYesterday.caloriesList,
                MockData.valuesYesterday.heartRateList,
                MockData.valuesYesterday.systolicList,
                MockData.valuesYesterday.diastolicList,
                yesterdayFormattedDate,
            )
            _sharedStepsFlow.emit(yesterdayValues)

            delay(500)
            Log.d("DATAX", "requestSmartWatchDataRep:  CoroutineScope(Dispatchers.Default) END")
            jobSW?.cancel()
        }

        jobSW?.invokeOnCompletion {
            Log.d("DATAX", "requestSmartWatchData: cancelled")
        }
    }
}