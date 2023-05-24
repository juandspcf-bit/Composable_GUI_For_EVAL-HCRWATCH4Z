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
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreConstants
import com.icxcu.adsmartbandapp.data.local.dataPrefrerences.PreferenceDataStoreHelper
import com.icxcu.adsmartbandapp.database.SWRoomDatabase
import com.icxcu.adsmartbandapp.repositories.MyBloodPressureAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MyHeartRateAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MySpO2AlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MyTemperatureAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.SWRepository
import com.icxcu.adsmartbandapp.screens.mainNavBar.DayPhysicalActivityInfoState
import com.icxcu.adsmartbandapp.screens.mainNavBar.SmartWatchState
import com.icxcu.adsmartbandapp.screens.mainNavBar.TodayPhysicalActivityInfoState
import com.icxcu.adsmartbandapp.screens.mainNavBar.YesterdayPhysicalActivityInfoState
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

    private var swRepository: SWRepository

    val smartWatchState = SmartWatchState(todayFormattedDate, yesterdayFormattedDate)

    var selectedDay by mutableStateOf("")

    val dayPhysicalActivityInfoState = DayPhysicalActivityInfoState()
    val todayPhysicalActivityInfoState = TodayPhysicalActivityInfoState()
    val yesterdayPhysicalActivityInfoState = YesterdayPhysicalActivityInfoState()


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


    var lastDeviceAccessedName by mutableStateOf("not fetched")
    var lastDeviceAccessedAddress by mutableStateOf("not fetched")

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


        dayPhysicalActivityInfoState.dayPhysicalActivityResultsFromDB = swRepository.dayPhysicalActivityResultsFromDB
        todayPhysicalActivityInfoState.todayPhysicalActivityResultsFromDB = swRepository.todayPhysicalActivityResultsFromDB
        yesterdayPhysicalActivityInfoState.yesterdayPhysicalActivityResultsFromDB = swRepository.yesterdayPhysicalActivityResultsFromDB

        dayPhysicalActivityInfoState.dayBloodPressureResultsFromDB = swRepository.dayBloodPressureResultsFromDB
        todayPhysicalActivityInfoState.todayBloodPressureResultsFromDB = swRepository.todayBloodPressureResultsFromDB
        yesterdayPhysicalActivityInfoState.yesterdayBloodPressureResultsFromDB = swRepository.yesterdayBloodPressureResultsFromDB

        dayPhysicalActivityInfoState.dayHeartRateResultsFromDB = swRepository.dayHeartRateResultsFromDB
        todayPhysicalActivityInfoState.todayHeartRateResultsFromDB = swRepository.todayHeartRateResultsFromDB
        yesterdayPhysicalActivityInfoState.yesterdayHeartRateResultsFromDB = swRepository.yesterdayHeartRateResultsFromDB

        personalInfoFromDB = swRepository.personalInfoFromDB
        updateAlertDialogState.personalInfoAlertDialogUVLiveData =
            swRepository.personalInfoAlertDialogUVStateLiveData



        viewModelScope.launch {
            swRepository.sharedStepsFlow.collect {

                when (it.date) {
                    todayFormattedDate -> {
                        smartWatchState.todayDateValuesReadFromSW = it
                    }

                    yesterdayFormattedDate -> {
                        smartWatchState.yesterdayDateValuesFromSW = it
                        smartWatchState.progressbarForFetchingDataFromSW = false
                        smartWatchState.isRequestForFetchingDataFromSWBeginning = true
                    }
                }


            }
        }
    }


    fun readFromStorageLastDeviceAccessedName(preferenceDataStoreHelper:PreferenceDataStoreHelper){
        viewModelScope.launch {
            lastDeviceAccessedName =  preferenceDataStoreHelper
                .getFirstPreference(PreferenceDataStoreConstants.LAST_DEVICE_KEY_NAME,"fetched but without data")
        }
    }

    fun readFromStorageLastDeviceAccessedAddress(preferenceDataStoreHelper:PreferenceDataStoreHelper){
        viewModelScope.launch {
            lastDeviceAccessedAddress =  preferenceDataStoreHelper
                .getFirstPreference(PreferenceDataStoreConstants.LAST_DEVICE_KEY_ADDRESS,"fetched but without data")
        }
    }


    fun writeInStorageLastDeviceAccessedName(preferenceDataStoreHelper: PreferenceDataStoreHelper, value:String) {
        viewModelScope.launch {
            preferenceDataStoreHelper.putPreference(PreferenceDataStoreConstants.LAST_DEVICE_KEY_NAME, value)
        }
    }

    fun writeInStorageLastDeviceAccessedAddress(preferenceDataStoreHelper: PreferenceDataStoreHelper, value:String) {
        viewModelScope.launch {
            preferenceDataStoreHelper.putPreference(PreferenceDataStoreConstants.LAST_DEVICE_KEY_ADDRESS, value)
        }
    }



    fun requestSmartWatchData(name: String = "", macAddress: String = "") {

        swRepository.requestSmartWatchData()

        viewModelScope.launch {
            delay(1000)
            smartWatchState.progressbarForFetchingDataFromSW = true
        }

    }

    fun getMyHeartRateAlertDialogDataHandler(): MyHeartRateAlertDialogDataHandler {
        return swRepository.myHeartRateAlertDialogDataHandler
    }

    fun getMyBloodPressureAlertDialogDataHandler(): MyBloodPressureAlertDialogDataHandler {
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
