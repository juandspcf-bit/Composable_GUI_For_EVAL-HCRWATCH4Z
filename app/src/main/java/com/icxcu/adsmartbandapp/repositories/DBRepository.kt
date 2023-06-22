package com.icxcu.adsmartbandapp.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.database.BloodPressureDao
import com.icxcu.adsmartbandapp.database.HeartRateDao
import com.icxcu.adsmartbandapp.database.PersonalInfoDao
import com.icxcu.adsmartbandapp.database.PhysicalActivityDao
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getIntegerListFromStringMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DBRepository(
    private val physicalActivityDao: PhysicalActivityDao,
    private val bloodPressureDao: BloodPressureDao,
    private val heartRateDao: HeartRateDao,
    private val personalInfoDao: PersonalInfoDao
) {

    var dayHealthResultsFromDBFForDashBoard = MutableLiveData<Values>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val myHeartRateAlertDialogDataHandler = MyHeartRateAlertDialogDataHandler()
    val myBloodPressureAlertDialogDataHandler = MyBloodPressureAlertDialogDataHandler()
    val myTemperatureAlertDialogDataHandler = MyTemperatureAlertDialogDataHandler()
    val mySpO2AlertDialogDataHandler = MySpO2AlertDialogDataHandler()


    suspend fun getDayPhysicalActivityWithCoroutine(
        date: String,
        macAddress: String
    ): List<PhysicalActivity> {
        return physicalActivityDao.getDayPhysicalActivityWithCoroutine(date, macAddress)
    }

    suspend fun getDayBloodPressureWithCoroutine(
        date: String,
        macAddress: String
    ): List<BloodPressure> {
        return bloodPressureDao.getDayBloodPressureWithCoroutine(date, macAddress)
    }

    suspend fun getDayHeartRateWithCoroutine(
        queryDate: String,
        queryMacAddress: String,
    ): List<HeartRate>  {
        return heartRateDao.getDayHeartRateWithCoroutine(queryDate, queryMacAddress)
    }

    suspend fun getPersonalInfoWithCoroutine(): List<PersonalInfo>{
        return personalInfoDao.getPersonalInfoWithCoroutine()
    }


    fun getDayHeartRateWithFlow(date: String, macAddress: String): Flow<List<HeartRate>>{
        return heartRateDao.getDayHeartRateWithFlow(date, macAddress)
    }

    fun getDayPhysicalActivityWithFlow(date: String, macAddress: String): Flow<List<PhysicalActivity>>{
        return physicalActivityDao.getDayPhysicalActivityWithFlow(date, macAddress)
    }

    fun getDayBloodPressureWithFlow(date: String, macAddress: String): Flow<List<BloodPressure>>{
        return bloodPressureDao.getDayBloodPressureWithFlow(date, macAddress)
    }


    fun getDayHealthData(
        queryDate: String,
        queryMacAddress: String,
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            val resultsPhysicalActivityFromDb =
                asyncDayPhysicalActivityData(queryDate, queryMacAddress)
            val resultsPhysicalActivity = if (resultsPhysicalActivityFromDb.isEmpty().not()
            ) {
                resultsPhysicalActivityFromDb
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
                listOf(stepsActivity, distanceActivity, caloriesListActivity)

            }

            val resultsBloodPressureFromDB = asyncDayBloodPressureData(queryDate, queryMacAddress)
            val resultsBloodPressure = if (resultsBloodPressureFromDB.isEmpty().not()) {
                resultsBloodPressureFromDB
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

                listOf(bloodPressureS, bloodPressureD)

            }


            val resultsHeartRateFromDB = asyncDayHeartRateData(queryDate, queryMacAddress)
            val resultsHeartRate = if (resultsHeartRateFromDB.isEmpty().not()) {
                resultsHeartRateFromDB
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


                listOf(heartRateS)

            }

            val dayStepListFromDB =
                if (resultsPhysicalActivity.isEmpty().not()) {
                    val filter =
                        resultsPhysicalActivity.filter { it.typesTable == TypesTable.STEPS }
                    getIntegerListFromStringMap(filter[0].data)
                } else {
                    List(48) { 0 }.toList()
                }

            val dayDistanceListFromDB =
                if (resultsPhysicalActivity.isEmpty().not()) {
                    val filter =
                        resultsPhysicalActivity.filter { it.typesTable == TypesTable.DISTANCE }
                    if (filter.isEmpty()) {
                        MutableList(48) { 0.0 }.toList()
                    } else {
                        getDoubleListFromStringMap(filter[0].data)
                    }
                } else {
                    List(48) { 0.0 }.toList()
                }

            val dayCaloriesListFromDB =
                if (resultsPhysicalActivity.isEmpty().not()) {
                    val filter =
                        resultsPhysicalActivity.filter { it.typesTable == TypesTable.CALORIES }
                    if (filter.isEmpty()) {
                        MutableList(48) { 0.0 }.toList()
                    } else {
                        getDoubleListFromStringMap(filter[0].data)
                    }
                } else {
                    List(48) { 0.0 }.toList()
                }

            val daySystolicListFromDB =
                if (resultsBloodPressure.isEmpty().not()) {
                    val filter =
                        resultsBloodPressure.filter { it.typesTable == TypesTable.SYSTOLIC }
                    getDoubleListFromStringMap(filter[0].data)
                } else {
                    MutableList(48) { 0.0 }.toList()
                }

            val dayDiastolicListFromDB =
                if (resultsBloodPressure.isEmpty().not()) {
                    val filter =
                        resultsBloodPressure.filter { it.typesTable == TypesTable.DIASTOLIC }
                    if (filter.isEmpty()) {
                        MutableList(48) { 0.0 }.toList()
                    } else {
                        getDoubleListFromStringMap(filter[0].data)
                    }
                } else {
                    MutableList(48) { 0.0 }.toList()
                }


            val dayHeartRateListFromDB =
                if (resultsHeartRate.isEmpty().not()) {
                    val filter = resultsHeartRate.filter { it.typesTable == TypesTable.HEART_RATE }
                    getDoubleListFromStringMap(filter[0].data)
                } else {
                    MutableList(48) { 0.0 }.toList()
                }


            dayHealthResultsFromDBFForDashBoard.value = Values(
                dayStepListFromDB,
                dayDistanceListFromDB,
                dayCaloriesListFromDB,
                dayHeartRateListFromDB,
                daySystolicListFromDB,
                dayDiastolicListFromDB,
                queryDate
            )

            Log.d("DATA_FROM_DATABASE", "DB: ${dayHealthResultsFromDBFForDashBoard.value}")

        }
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

    private suspend fun asyncDayPhysicalActivityData(
        date: String,
        macAddress: String
    ): List<PhysicalActivity> =
        coroutineScope.async(Dispatchers.IO) {
            return@async physicalActivityDao.getDayPhysicalActivityData(date, macAddress)
        }.await()

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

    private suspend fun asyncDayBloodPressureData(
        date: String,
        macAddress: String
    ): List<BloodPressure> =
        coroutineScope.async(Dispatchers.IO) {
            return@async bloodPressureDao.getDayBloodPressureData(date, macAddress)
        }.await()

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

    private suspend fun asyncDayHeartRateData(date: String, macAddress: String): List<HeartRate> =
        coroutineScope.async(Dispatchers.IO) {
            return@async heartRateDao.getDayHeartRateData(date, macAddress)
        }.await()


    suspend fun updatePersonalInfoDataWithCoroutine(personalInfo: PersonalInfo):Boolean {
        personalInfoDao.updatePersonalInfoDataWithCoroutine(personalInfo)
        return true
    }

    suspend fun insertPersonalInfoDataWithCoroutine(personalInfo: PersonalInfo):Boolean {
        personalInfoDao.insertPersonalInfoDataWithCoroutine(personalInfo)
        return true
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