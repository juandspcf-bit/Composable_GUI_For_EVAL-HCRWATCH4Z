package com.icxcu.adsmartbandapp.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.icxcu.adsmartbandapp.data.MockData
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.database.BloodPressureDao
import com.icxcu.adsmartbandapp.database.HeartRateDao
import com.icxcu.adsmartbandapp.database.PersonalInfoDao
import com.icxcu.adsmartbandapp.database.PhysicalActivityDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SWRepository(
    private val physicalActivityDao: PhysicalActivityDao,
    private val bloodPressureDao: BloodPressureDao,
    private val heartRateDao: HeartRateDao,
    private val personalInfoDao: PersonalInfoDao
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var dayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var todayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var yesterdayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()

    var dayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()
    var todayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()
    var yesterdayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()

    var dayHeartRateResultsFromDB = MutableLiveData<List<HeartRate>>()
    var todayHeartRateResultsFromDB = MutableLiveData<List<HeartRate>>()
    var yesterdayHeartRateResultsFromDB = MutableLiveData<List<HeartRate>>()

    var personalInfoFromDB = MutableLiveData<List<PersonalInfo>>()
    var personalInfoAlertDialogUVStateLiveData = MutableLiveData(false)

    private val _sharedStepsFlow = MutableSharedFlow<Values>(
        replay = 30,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sharedStepsFlow = _sharedStepsFlow.asSharedFlow()




    fun requestSmartWatchData(name: String = "", macAddress: String = "") {
        CoroutineScope(Dispatchers.Default).launch {
            delay(5000)
            _sharedStepsFlow.emit(MockData.valuesToday)
            delay(5000)
            _sharedStepsFlow.emit(MockData.valuesYesterday)
        }
    }


    val myHeartRateAlertDialogDataHandler = MyHeartRateAlertDialogDataHandler()
    val myBloodPressureAlertDialogDataHandler = MyBloodPressureAlertDialogDataHandler()
    val myTemperatureAlertDialogDataHandler = MyTemperatureAlertDialogDataHandler()
    val mySpO2AlertDialogDataHandler = MySpO2AlertDialogDataHandler()









    fun getAnyDayPhysicalActivityData(queryDate: String, queryMacAddress: String) {
        getDayPhysicalActivityData(queryDate, queryMacAddress, dayPhysicalActivityResultsFromDB)
    }

    fun getTodayPhysicalActivityData(queryDate: String, queryMacAddress: String) {
        getDayPhysicalActivityData(queryDate, queryMacAddress, todayPhysicalActivityResultsFromDB)
    }

    fun getYesterdayPhysicalActivityData(queryDate: String, queryMacAddress: String) {
        getDayPhysicalActivityData(
            queryDate,
            queryMacAddress,
            yesterdayPhysicalActivityResultsFromDB
        )
    }

    fun updatePhysicalActivityData(physicalActivity: PhysicalActivity) {
        coroutineScope.launch(Dispatchers.IO) {
            physicalActivityDao.updatePhysicalActivityData(physicalActivity)
        }
    }

    fun insertPhysicalActivityData(physicalActivity: PhysicalActivity) {
        coroutineScope.launch(Dispatchers.IO) {
            physicalActivityDao.insertPhysicalActivityData(physicalActivity)
        }
    }

    private fun getDayPhysicalActivityData(
        queryDate: String,
        queryMacAddress: String,
        dayPhysicalActivityMLVData: MutableLiveData<List<PhysicalActivity>>
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncDayPhysicalActivityData(queryDate, queryMacAddress)
            if (results != null && results.isEmpty().not()) {
                dayPhysicalActivityMLVData.value = results
            } else {

                val stepsActivity = PhysicalActivity().apply {
                    id = -1
                    macAddress = queryMacAddress
                    dateData = queryDate

                    val newValuesList = mutableMapOf<String, String>()
                    MutableList(48) { 0 }.forEachIndexed { index, i ->
                        newValuesList[index.toString()] = i.toString()
                    }
                    data = newValuesList.toString()
                    typesTable = TypesTable.STEPS
                }

                val distanceActivity = PhysicalActivity().apply {
                    id = -1
                    macAddress = queryMacAddress
                    dateData = queryDate

                    val newValuesList = mutableMapOf<String, String>()
                    MutableList(48) { 0.0 }.forEachIndexed { index, i ->
                        newValuesList[index.toString()] = i.toString()
                    }
                    data = newValuesList.toString()
                    typesTable = TypesTable.DISTANCE
                }

                val caloriesListActivity = PhysicalActivity().apply {
                    id = -1
                    macAddress = queryMacAddress
                    dateData = queryDate

                    val newValuesList = mutableMapOf<String, String>()
                    MutableList(48) { 0.0 }.forEachIndexed { index, i ->
                        newValuesList[index.toString()] = i.toString()
                    }
                    data = newValuesList.toString()
                    typesTable = TypesTable.CALORIES
                }
                dayPhysicalActivityMLVData.value =
                    listOf(stepsActivity, distanceActivity, caloriesListActivity)

            }
        }
    }


    private suspend fun asyncDayPhysicalActivityData(
        date: String,
        macAddress: String
    ): List<PhysicalActivity>? =
        coroutineScope.async(Dispatchers.IO) {
            return@async physicalActivityDao.getDayPhysicalActivityData(date, macAddress)
        }.await()


    fun getAnyDayBloodPressureData(
        queryDate: String,
        queryMacAddress: String
    ) {
        getDayBloodPressureData(queryDate, queryMacAddress, dayBloodPressureResultsFromDB)
    }

    fun getTodayBloodPressureData(queryDate: String, queryMacAddress: String) {
        getDayBloodPressureData(queryDate, queryMacAddress, todayBloodPressureResultsFromDB)
    }

    fun getYesterdayBloodPressureData(queryDate: String, queryMacAddress: String) {
        getDayBloodPressureData(queryDate, queryMacAddress, yesterdayBloodPressureResultsFromDB)
    }

    fun updateBloodPressureData(bloodPressure: BloodPressure) {
        coroutineScope.launch(Dispatchers.IO) {
            bloodPressureDao.updateBloodPressureData(bloodPressure = bloodPressure)
        }
    }

    fun insertBloodPressureData(bloodPressure: BloodPressure) {
        coroutineScope.launch(Dispatchers.IO) {
            bloodPressureDao.insertBloodPressureData(bloodPressure = bloodPressure)
        }
    }

    private fun getDayBloodPressureData(
        queryDate: String,
        queryMacAddress: String,
        dayBloodPressureMLVData: MutableLiveData<List<BloodPressure>>
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncDayBloodPressureData(queryDate, queryMacAddress)

            if (results.isEmpty().not()) {
                dayBloodPressureMLVData.value = results
            } else {

                val bloodPressureS = BloodPressure().apply {
                    id = -1
                    macAddress = queryMacAddress
                    dateData = queryDate

                    val newValuesList = mutableMapOf<String, String>()
                    MutableList(48) { 0.0 }.forEachIndexed { index, i ->
                        newValuesList[index.toString()] = i.toString()
                    }
                    data = newValuesList.toString()
                    typesTable = TypesTable.SYSTOLIC
                }

                val bloodPressureD = BloodPressure().apply {
                    id = -1
                    macAddress = queryMacAddress
                    dateData = queryDate

                    val newValuesList = mutableMapOf<String, String>()
                    MutableList(48) { 0.0 }.forEachIndexed { index, i ->
                        newValuesList[index.toString()] = i.toString()
                    }
                    data = newValuesList.toString()
                    typesTable = TypesTable.DIASTOLIC
                }


                dayBloodPressureMLVData.value = listOf(bloodPressureS, bloodPressureD)

            }


        }
    }

    private suspend fun asyncDayBloodPressureData(
        date: String,
        macAddress: String
    ): List<BloodPressure> =
        coroutineScope.async(Dispatchers.IO) {
            return@async bloodPressureDao.getDayBloodPressureData(date, macAddress)
        }.await()


    fun getAnyDayHeartRateData(
        queryDate: String,
        queryMacAddress: String
    ) {
        getDayHeartRateData(queryDate, queryMacAddress, dayHeartRateResultsFromDB)
    }

    fun getTodayHeartRateData(queryDate: String, queryMacAddress: String) {
        getDayHeartRateData(queryDate, queryMacAddress, todayHeartRateResultsFromDB)
    }

    fun getYesterdayHeartRateData(queryDate: String, queryMacAddress: String) {
        getDayHeartRateData(queryDate, queryMacAddress, yesterdayHeartRateResultsFromDB)
    }

    fun updateHeartRateData(heartRate: HeartRate) {
        coroutineScope.launch(Dispatchers.IO) {
            heartRateDao.updateHeartRateData(heartRate = heartRate)
        }
    }

    fun insertHeartRateData(heartRate: HeartRate) {
        coroutineScope.launch(Dispatchers.IO) {
            heartRateDao.insertHeartRateData(heartRate = heartRate)
        }
    }

    private fun getDayHeartRateData(
        queryDate: String,
        queryMacAddress: String,
        dayHeartRateMLVData: MutableLiveData<List<HeartRate>>
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncDayHeartRateData(queryDate, queryMacAddress)

            if (results.isEmpty().not()) {
                dayHeartRateMLVData.value = results
            } else {

                val heartRateS = HeartRate().apply {
                    id = -1
                    macAddress = queryMacAddress
                    dateData = queryDate

                    val newValuesList = mutableMapOf<String, String>()
                    MutableList(48) { 0.0 }.forEachIndexed { index, i ->
                        newValuesList[index.toString()] = i.toString()
                    }
                    data = newValuesList.toString()
                    typesTable = TypesTable.HEART_RATE
                }


                dayHeartRateMLVData.value = listOf(heartRateS)

            }


        }
    }

    private suspend fun asyncDayHeartRateData(date: String, macAddress: String): List<HeartRate> =
        coroutineScope.async(Dispatchers.IO) {
            return@async heartRateDao.getDayHeartRateData(date, macAddress)
        }.await()


    fun getPersonalInfoData(queryMacAddress: String,){
        getPersonalInfoDataP(queryMacAddress, personalInfoFromDB)
    }

    private fun getPersonalInfoDataP(
        queryMacAddress: String,
        data: MutableLiveData<List<PersonalInfo>>
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            val results = asyncPersonalDataData(queryMacAddress)
            if (results.isEmpty().not()) {
                data.value = results
            } else {
                val personalInfoS = PersonalInfo().apply {
                    id = -1
                    macAddress = queryMacAddress
                    typesTable = TypesTable.PERSONAL_INFO
                    name = ""
                    birthdate = ""
                    weight = 0.0
                    height = 0.0
                }


                data.value = listOf(personalInfoS)

            }


        }
    }

    private suspend fun asyncPersonalDataData(macAddress: String): List<PersonalInfo> =
        coroutineScope.async(Dispatchers.IO) {
            return@async personalInfoDao.getPersonalInfo(macAddress)
        }.await()

    fun insertPersonalInfo(personalInfo: PersonalInfo) {
        coroutineScope.launch(Dispatchers.IO) {
            personalInfoDao.insertPersonalInfoData(personalInfo = personalInfo)
            withContext(Dispatchers.Main){
                personalInfoAlertDialogUVStateLiveData.value = !(personalInfoAlertDialogUVStateLiveData.value?:true)
                Log.d("Steps", "insertPersonalInfo: Firsts ${personalInfoAlertDialogUVStateLiveData.value}")
            }
        }
    }

    fun updatePersonalInfo(personalInfo: PersonalInfo) {
        coroutineScope.launch(Dispatchers.IO) {
            personalInfoDao.updatePersonalInfoData(personalInfo = personalInfo)
            withContext(Dispatchers.Main){
                personalInfoAlertDialogUVStateLiveData.value = !(personalInfoAlertDialogUVStateLiveData.value?:true)
                Log.d("Steps", "updatePersonalInfo: Firsts ${personalInfoAlertDialogUVStateLiveData.value}")
            }
        }
    }


}

data class Values(
    val stepList: List<Int>,
    val distanceList: List<Double>,
    val caloriesList: List<Double>,
    val heartRateList: List<Double>,
    val systolicList: List<Double>,
    val diastolicList: List<Double>,
    val date: String
)