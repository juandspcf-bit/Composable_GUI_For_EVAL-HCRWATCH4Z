package com.icxcu.adsmartbandapp.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.icxcu.adsmartbandapp.data.MockData
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.database.BloodPressureDao
import com.icxcu.adsmartbandapp.database.PhysicalActivityDao
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SWRepository(private val physicalActivityDao: PhysicalActivityDao, private val bloodPressureDao: BloodPressureDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var dayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var todayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var yesterdayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()

    var dayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()
    var todayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()
    var yesterdayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()

    private val _sharedStepsFlow = MutableSharedFlow<Values>(
        replay = 30,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sharedStepsFlow = _sharedStepsFlow.asSharedFlow()




    fun requestSmartWatchData(name:String="", macAddress: String="") {
        CoroutineScope(Dispatchers.Default).launch {
            delay(5000)
            _sharedStepsFlow.emit(MockData.valuesToday)
        }
    }



    fun getDayPhysicalActivityData(queryDate: String, queryMacAddress:String) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncDayPhysicalActivityData(queryDate, queryMacAddress)
            Log.d("Results", "getDayPhysicalActivityData: $results")
            if(results!=null && results.isEmpty().not()){
                dayPhysicalActivityResultsFromDB.value = results
            }else{

                val stepsActivity = PhysicalActivity().apply {
                    id=-1
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
                    id=-1
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
                    id=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0.0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.CALORIES
                }
                dayPhysicalActivityResultsFromDB.value =  listOf(stepsActivity, distanceActivity, caloriesListActivity)

            }


        }
    }




    fun getTodayPhysicalActivityData(queryDate: String, queryMacAddress:String) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncDayPhysicalActivityData(queryDate, queryMacAddress)

            if(results!=null && results.isEmpty().not()){
                todayPhysicalActivityResultsFromDB.value = results
            }else{

                val stepsActivity = PhysicalActivity().apply {
                    id=-1
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
                    id=-1
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
                    id=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0.0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.CALORIES
                }
                todayPhysicalActivityResultsFromDB.value =  listOf(stepsActivity, distanceActivity, caloriesListActivity)

            }


        }
    }

    fun getYesterdayPhysicalActivityData(queryDate: String, queryMacAddress:String) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncDayPhysicalActivityData(queryDate, queryMacAddress)

            if(results!=null && results.isEmpty().not()){
                yesterdayPhysicalActivityResultsFromDB.value = results
            }else{

                val stepsActivity = PhysicalActivity().apply {
                    id=-1
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
                    id=-1
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
                    id=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0.0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.CALORIES
                }
                yesterdayPhysicalActivityResultsFromDB.value =  listOf(stepsActivity, distanceActivity, caloriesListActivity)

            }


        }
    }

    private suspend fun asyncDayPhysicalActivityData(date: String, macAddress:String): List<PhysicalActivity>? =
        coroutineScope.async(Dispatchers.IO) {
            return@async physicalActivityDao.getDayPhysicalActivityData(date, macAddress)
        }.await()

    fun updatePhysicalActivityData(physicalActivity: PhysicalActivity){
        coroutineScope.launch(Dispatchers.IO) {
            physicalActivityDao.updatePhysicalActivityData(physicalActivity)
        }
    }

    fun insertPhysicalActivityData(physicalActivity: PhysicalActivity) {
        coroutineScope.launch(Dispatchers.IO) {
            physicalActivityDao.insertPhysicalActivityData(physicalActivity)
        }
    }

    fun getDayBloodPressureData(queryDate: String, queryMacAddress:String) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncDayBloodPressureData(queryDate, queryMacAddress)

            if(results.isEmpty().not()){
                dayBloodPressureResultsFromDB.value = results
            }else{

                val bloodPressureS = BloodPressure().apply {
                    id=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0.0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.SYSTOLIC
                }

                val bloodPressureD = BloodPressure().apply {
                    id=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0.0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.DIASTOLIC
                }


                dayBloodPressureResultsFromDB.value =  listOf(bloodPressureS, bloodPressureD)

            }


        }
    }

    fun getTodayBloodPressureData(queryDate: String, queryMacAddress:String) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncDayBloodPressureData(queryDate, queryMacAddress)

            if(results.isEmpty().not()){
                todayBloodPressureResultsFromDB.value = results
            }else{

                val bloodPressureS = BloodPressure().apply {
                    id=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0.0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.SYSTOLIC
                }

                val bloodPressureD = BloodPressure().apply {
                    id=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0.0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.DIASTOLIC
                }


                todayBloodPressureResultsFromDB.value =  listOf(bloodPressureS, bloodPressureD)

            }


        }
    }

    fun getYesterdayBloodPressureData(queryDate: String, queryMacAddress:String) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncDayBloodPressureData(queryDate, queryMacAddress)

            if(results.isEmpty().not()){
                yesterdayBloodPressureResultsFromDB.value = results
            }else{

                val bloodPressureS = BloodPressure().apply {
                    id=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0.0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.SYSTOLIC
                }

                val bloodPressureD = BloodPressure().apply {
                    id=-1
                    macAddress = queryMacAddress
                    dateData= queryDate

                    val newValuesList = mutableMapOf<String,String>()
                    MutableList(48) { 0.0 }.forEachIndexed{ index, i ->
                        newValuesList[index.toString()]= i.toString()
                    }
                    data= newValuesList.toString()
                    typesTable = TypesTable.DIASTOLIC
                }


                yesterdayBloodPressureResultsFromDB.value =  listOf(bloodPressureS, bloodPressureD)

            }


        }
    }
    private suspend fun asyncDayBloodPressureData(date: String, macAddress:String): List<BloodPressure> =
        coroutineScope.async(Dispatchers.IO) {
            return@async bloodPressureDao.getDayBloodPressureData(date, macAddress)
        }.await()

    fun updateBloodPressureData(bloodPressure: BloodPressure){
        coroutineScope.launch(Dispatchers.IO) {
            bloodPressureDao.updateBloodPressureData(bloodPressure = bloodPressure)
        }
    }

    fun insertBloodPressureData(bloodPressure: BloodPressure) {
        coroutineScope.launch(Dispatchers.IO) {
            bloodPressureDao.insertBloodPressureData(bloodPressure = bloodPressure)
        }
    }

}

data class Values(
    var stepList: List<Int>,
    var distanceList: List<Double>,
    var caloriesList: List<Double>,
    var systolic: List<Double>,
    var diastolic: List<Double>,
    var date: String
)