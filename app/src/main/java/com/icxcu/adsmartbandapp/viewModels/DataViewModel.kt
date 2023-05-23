package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.database.SWRoomDatabase
import com.icxcu.adsmartbandapp.repositories.myBloodPressureAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MyHeartRateAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MySpO2AlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MyTemperatureAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.SWRepository
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.TodayPhysicalActivityInfoState
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.YesterdayPhysicalActivityInfoState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.InvalidAlertDialogState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.PersonalInfoDataState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.UpdateAlertDialogState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.ValidatorsPersonalField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DataViewModel(var application: Application) : ViewModel() {


    val myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val todayLocalDateTime = LocalDateTime.now()
    val todayFormattedDate = todayLocalDateTime.format(myFormatObj)
    val yesterdayLocalDateTime = todayLocalDateTime.minusDays(1)
    val yesterdayFormattedDate = yesterdayLocalDateTime.format(myFormatObj)
    val pastYesterdayLocalDateTime = todayLocalDateTime.minusDays(2)
    val pastYesterdayFormattedDate = pastYesterdayLocalDateTime.format(myFormatObj)

    var progressbarForFetchingDataFromSW by mutableStateOf(false)
    var isRequestForFetchingDataFromSWBeginning by mutableStateOf(false)

    var selectedDay by mutableStateOf("")

    var dayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var dayStepListFromDB by mutableStateOf(listOf<Int>())
    var dayDistanceListFromDB by mutableStateOf(listOf<Double>())
    var dayCaloriesListFromDB by mutableStateOf(listOf<Double>())

    var todayDateValuesReadFromSW by mutableStateOf(
        Values(
            MutableList(48) { 0 }.toList(),
            MutableList(48) { 0.0 }.toList(),
            MutableList(48) { 0.0 }.toList(),
            MutableList(48) { 0.0 }.toList(),
            MutableList(48) { 0.0 }.toList(),
            MutableList(48) { 0.0 }.toList(),
            todayFormattedDate
        )
    )

    var dayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()
    var daySystolicListFromDB by mutableStateOf(listOf<Double>())
    var dayDiastolicListFromDB by mutableStateOf(listOf<Double>())



    var dayHeartRateResultsFromDB = MutableLiveData<List<HeartRate>>()
    var dayHeartRateListFromDB by mutableStateOf(listOf<Double>())


    val todayPhysicalActivityInfoState = TodayPhysicalActivityInfoState()

    var yesterdayDateValuesFromSW by mutableStateOf(
        Values(
            MutableList(48) { 0 }.toList(),
            MutableList(48) { 0.0 }.toList(),
            MutableList(48) { 0.0 }.toList(),
            MutableList(48) { 0.0 }.toList(),
            MutableList(48) { 0.0 }.toList(),
            MutableList(48) { 0.0 }.toList(),
            yesterdayFormattedDate
        )
    )

    val yesterdayPhysicalActivityInfoState = YesterdayPhysicalActivityInfoState()
/*    var yesterdayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var yesterdayStepListReadFromDB by mutableStateOf(listOf<Int>())
    var isYesterdayStepsListAlreadyInsertedInDB = false
    var isYesterdayStepsListInDBAlreadyUpdated = false

    var yesterdayDistanceListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdayDistanceListAlreadyInsertedInDB = false
    var isYesterdayDistanceListInDBAlreadyUpdated = false

    var yesterdayCaloriesListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdayCaloriesListAlreadyInsertedInDB = false
    var isYesterdayCaloriesListInDBAlreadyUpdated = false

    var yesterdayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()
    var yesterdaySystolicListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdaySystolicListAlreadyInsertedInDB = false
    var isYesterdaySystolicListInDBAlreadyUpdated = false

    var yesterdayDiastolicListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdayDiastolicListAlreadyInsertedInDB = false
    var isYesterdayDiastolicListInDBAlreadyUpdated = false

    var yesterdayHeartRateResultsFromDB = MutableLiveData<List<HeartRate>>()
    var yesterdayHeartRateListReadFromDB by mutableStateOf(listOf<Double>())
    var isYesterdayHeartRateListAlreadyInsertedInDB = false
    var isYesterdayHeartRateListInDBAlreadyUpdated = false*/

    private var swRepository: SWRepository


    var personalInfoFromDB = MutableLiveData<List<PersonalInfo>>()
    var personalInfoListReadFromDB = listOf<PersonalInfo>()

    var personalInfoDataState = PersonalInfoDataState()
    var invalidAlertDialogState = InvalidAlertDialogState()
    var updateAlertDialogState = UpdateAlertDialogState()

    var macAddressDeviceBluetooth: String = ""
    var nameDeviceBluetooth: String = ""

    //DatePicker for physical activity plots
    var stateShowDialogDatePicker by mutableStateOf(false)
    var stateMiliSecondsDateDialogDatePicker by mutableStateOf(0L)




    init {
        val swDb = SWRoomDatabase.getInstance(application)
        val physicalActivityDao = swDb.physicalActivityDao()
        val bloodPressureDao = swDb.bloodPressureDao()
        val heartRateDao = swDb.heartRateDao()
        val personalInfoDao = swDb.personalInfoDao()
        swRepository = SWRepository(
            physicalActivityDao,
            bloodPressureDao,
            heartRateDao,
            personalInfoDao
        )


        dayPhysicalActivityResultsFromDB = swRepository.dayPhysicalActivityResultsFromDB
        todayPhysicalActivityInfoState.todayPhysicalActivityResultsFromDB = swRepository.todayPhysicalActivityResultsFromDB
        yesterdayPhysicalActivityInfoState.yesterdayPhysicalActivityResultsFromDB = swRepository.yesterdayPhysicalActivityResultsFromDB

        dayBloodPressureResultsFromDB = swRepository.dayBloodPressureResultsFromDB
        todayPhysicalActivityInfoState.todayBloodPressureResultsFromDB = swRepository.todayBloodPressureResultsFromDB
        yesterdayPhysicalActivityInfoState.yesterdayBloodPressureResultsFromDB = swRepository.yesterdayBloodPressureResultsFromDB

        dayHeartRateResultsFromDB = swRepository.dayHeartRateResultsFromDB
        todayPhysicalActivityInfoState.todayHeartRateResultsFromDB = swRepository.todayHeartRateResultsFromDB
        yesterdayPhysicalActivityInfoState.yesterdayHeartRateResultsFromDB = swRepository.yesterdayHeartRateResultsFromDB

        personalInfoFromDB = swRepository.personalInfoFromDB
        updateAlertDialogState.personalInfoAlertDialogUVLiveData =
            swRepository.personalInfoAlertDialogUVStateLiveData



        viewModelScope.launch {
            swRepository.sharedStepsFlow.collect {

                when (it.date) {
                    todayFormattedDate -> {
                        todayDateValuesReadFromSW = it
                    }

                    yesterdayFormattedDate -> {
                        yesterdayDateValuesFromSW = it
                        progressbarForFetchingDataFromSW = false
                        isRequestForFetchingDataFromSWBeginning = true
                    }
                }


            }
        }
    }


    fun requestSmartWatchData(name: String = "", macAddress: String = "") {

        swRepository.requestSmartWatchData()

        viewModelScope.launch {
            delay(1000)
            progressbarForFetchingDataFromSW = true
        }

    }

    fun getMyHeartRateAlertDialogDataHandler(): MyHeartRateAlertDialogDataHandler {
        return swRepository.myHeartRateAlertDialogDataHandler
    }

    fun getMyBloodPressureAlertDialogDataHandler(): myBloodPressureAlertDialogDataHandler {
        return swRepository.myBloodPressureAlertDialogDataHandler
    }

    fun getMyTemperatureAlertDialogDataHandler(): MyTemperatureAlertDialogDataHandler {
        return swRepository.myTemperatureAlertDialogDataHandler
    }

    fun getMySpO2AlertDialogDataHandler(): MySpO2AlertDialogDataHandler {
        return swRepository.mySpO2AlertDialogDataHandler
    }


    fun getDayPhysicalActivityData(dateData: String, macAddress: String) {
        swRepository.getAnyDayPhysicalActivityData(dateData, macAddress)
    }

    fun getTodayPhysicalActivityData(macAddress: String) {
        swRepository.getTodayPhysicalActivityData(todayFormattedDate, macAddress)
    }

    fun getYesterdayPhysicalActivityData(macAddress: String) {
        swRepository.getYesterdayPhysicalActivityData(yesterdayFormattedDate, macAddress)
    }

    fun insertPhysicalActivityData(physicalActivity: PhysicalActivity) {
        swRepository.insertPhysicalActivityData(physicalActivity)
    }

    fun updatePhysicalActivityData(physicalActivity: PhysicalActivity) {
        swRepository.updatePhysicalActivityData(physicalActivity)
    }

//Blood Pressure

    fun getDayBloodPressureData(dateData: String, macAddress: String) {
        swRepository.getAnyDayBloodPressureData(dateData, macAddress)
    }

    fun getTodayBloodPressureData(macAddress: String) {
        swRepository.getTodayBloodPressureData(todayFormattedDate, macAddress)
    }

    fun getYesterdayBloodPressureData(macAddress: String) {
        swRepository.getYesterdayBloodPressureData(yesterdayFormattedDate, macAddress)
    }

    fun insertBloodPressureData(bloodPressure: BloodPressure) {
        swRepository.insertBloodPressureData(bloodPressure)
    }

    fun updateBloodPressureData(bloodPressure: BloodPressure) {
        swRepository.updateBloodPressureData(bloodPressure)
    }


    //Heart Rate
    fun getDayHeartRateData(dateData: String, macAddress: String) {
        swRepository.getAnyDayHeartRateData(dateData, macAddress)
    }

    fun getTodayHeartRateData(macAddress: String) {
        swRepository.getTodayHeartRateData(todayFormattedDate, macAddress)
    }

    fun getYesterdayHeartRateData(macAddress: String) {
        swRepository.getYesterdayHeartRateData(yesterdayFormattedDate, macAddress)
    }

    fun insertHeartRateData(heartRate: HeartRate) {
        swRepository.insertHeartRateData(heartRate)
    }

    fun updateHeartRateData(heartRate: HeartRate) {
        swRepository.updateHeartRateData(heartRate)
    }


    //Personal data
    fun insertPersonalData(personalInfo: PersonalInfo) {
        swRepository.insertPersonalInfo(personalInfo)
    }

    fun updatePersonalData(personalInfo: PersonalInfo) {
        swRepository.updatePersonalInfo(personalInfo)
    }

    fun getPersonalInfoData(macAddress: String) {
        swRepository.getPersonalInfoData(macAddress)
    }

    fun validatePersonalInfo(
        getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    ): List<String> {
        val validationFields = mapOf(
            "Name" to getPersonalInfoDataStateState().name.isNotBlank(),
            "Birthday" to ValidatorsPersonalField.dateValidator(getPersonalInfoDataStateState().date).isNotBlank(),
            "Weight" to ValidatorsPersonalField.weightValidator(getPersonalInfoDataStateState().weight).isNotBlank(),
            "Height" to ValidatorsPersonalField.heightValidator(getPersonalInfoDataStateState().height).isNotBlank()
        )

        val invalidFields = mutableListOf<String>()
        validationFields.forEach{ (key, value) ->
            if (value.not()){
                invalidFields.add(key)
            }
        }

        return invalidFields.toList()


    }

}
