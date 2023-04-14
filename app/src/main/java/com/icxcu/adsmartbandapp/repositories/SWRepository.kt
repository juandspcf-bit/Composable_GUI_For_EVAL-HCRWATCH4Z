package com.icxcu.adsmartbandapp.repositories

import androidx.lifecycle.MutableLiveData
import com.icxcu.adsmartbandapp.data.MockData
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.database.PhysicalActivityDao
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow

class SWRepository(private val physicalActivityDao: PhysicalActivityDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var searchResults = MutableLiveData<List<PhysicalActivity>>()
    var dayPhysicalActivityData = MutableLiveData<List<PhysicalActivity>>()
    private val _sharedStepsFlow = MutableSharedFlow<Values>(
        replay = 30,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sharedStepsFlow = _sharedStepsFlow.asSharedFlow()


    fun insertPhysicalActivityData(physicalActivity: PhysicalActivity) {
        coroutineScope.launch(Dispatchers.IO) {
            physicalActivityDao.insertPhysicalActivityData(physicalActivity)
        }
    }

    fun requestStepsData() {
        CoroutineScope(Dispatchers.Default).launch {
            _sharedStepsFlow.emit(MockData.values)
        }
    }

    fun getThreeLatestDaysPhysicalActivityData(date: String, macAddress:String) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncGetDaysPhysicalActivityData(date, macAddress)
            results?.let{
                searchResults.value = results
            }
        }
    }
    private suspend fun asyncGetDaysPhysicalActivityData(date: String, macAddress:String): List<PhysicalActivity>? =
        coroutineScope.async(Dispatchers.IO) {
            return@async physicalActivityDao.getThreeLatestDaysPhysicalActivityData(date, macAddress)
        }.await()



    fun getDayDayPhysicalActivityData(date: String, macAddress:String) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncGetDaysPhysicalActivityData(date, macAddress)
            results?.let{
                searchResults.value = results
            }
        }
    }

    fun getDayPhysicalActivityData(date: String, macAddress:String) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncGetSingleDayPhysicalActivityData(date, macAddress)
            results?.let{
                dayPhysicalActivityData.value = results
            }
        }
    }

    private suspend fun asyncGetSingleDayPhysicalActivityData(date: String, macAddress:String): List<PhysicalActivity>? =
        coroutineScope.async(Dispatchers.IO) {
            return@async physicalActivityDao.getDayPhysicalActivityData(date, macAddress)
        }.await()

    fun getUsersPhysicalActivityFlow(): Flow<List<PhysicalActivity>> = flow {
        emit(physicalActivityDao.getThreeLatestDaysPhysicalActivityFlow())
    }

}

data class Values(
    var stepList: List<Int>,
    var distanceList: List<Double>,
    var caloriesList: List<Double>,
    var bloodPressureValuesList: List<List<Double>>
)