package com.icxcu.adsmartbandapp.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.icxcu.adsmartbandapp.data.MockData
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.database.PhysicalActivityDao
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SWRepository(private val physicalActivityDao: PhysicalActivityDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var todayPhysicalActivityResults = MutableLiveData<List<PhysicalActivity>>()

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



    fun getTodayPhysicalActivityData(queryDate: String, queryMacAddress:String) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncTodayPhysicalActivityData(queryDate, queryMacAddress)

            if(results!=null && results.isEmpty().not()){
                todayPhysicalActivityResults.value = results
            }else{

                val stepsActivity = PhysicalActivity().apply {
                    physicalActivityId=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.STEPS
                }

                val distanceActivity = PhysicalActivity().apply {
                    physicalActivityId=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0.0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.DISTANCE
                }

                val caloriesListActivity = PhysicalActivity().apply {
                    physicalActivityId=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0.0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.CALORIES
                }
                todayPhysicalActivityResults.value =  listOf(stepsActivity, distanceActivity, caloriesListActivity)
                Log.d("Data From database", "getToDayPhysicalActivityData: ${todayPhysicalActivityResults.value}")
            }


        }
    }

    private suspend fun asyncGetSingleDayPhysicalActivityData(date: String, macAddress:String): List<PhysicalActivity>? =
        coroutineScope.async(Dispatchers.IO) {
            return@async physicalActivityDao.getDayPhysicalActivityData(date, macAddress)
        }.await()

    private suspend fun asyncTodayPhysicalActivityData(date: String, macAddress:String): List<PhysicalActivity>? =
        coroutineScope.async(Dispatchers.IO) {
            return@async physicalActivityDao.getDayPhysicalActivityData(date, macAddress)
        }.await()

    fun updatePhysicalActivityData(physicalActivity: PhysicalActivity){
        coroutineScope.launch(Dispatchers.IO) {
            physicalActivityDao.updatePhysicalActivityData(physicalActivity)
        }
    }


}

data class Values(
    var stepList: List<Int>,
    var distanceList: List<Double>,
    var caloriesList: List<Double>,
    var bloodPressureValuesList: List<List<Double>>,
    var date: String
)