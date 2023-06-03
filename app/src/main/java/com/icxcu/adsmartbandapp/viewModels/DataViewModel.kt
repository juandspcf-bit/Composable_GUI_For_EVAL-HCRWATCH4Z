package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import android.util.Log
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
import com.icxcu.adsmartbandapp.repositories.DBRepository
import com.icxcu.adsmartbandapp.repositories.MyBloodPressureAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MyHeartRateAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MySpO2AlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MyTemperatureAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.SWRepository
import com.icxcu.adsmartbandapp.screens.BluetoothListScreenNavigationStatus
import com.icxcu.adsmartbandapp.screens.mainNavBar.DayHealthDataState
import com.icxcu.adsmartbandapp.screens.mainNavBar.DayHealthDataStateForDashBoard
import com.icxcu.adsmartbandapp.screens.mainNavBar.SWReadingStatus
import com.icxcu.adsmartbandapp.screens.mainNavBar.SmartWatchState
import com.icxcu.adsmartbandapp.screens.mainNavBar.TodayHealthsDataState
import com.icxcu.adsmartbandapp.screens.mainNavBar.YesterdayHealthsDataState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.InvalidAlertDialogState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.PersonalInfoDataState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.UpdateAlertDialogState
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.ValidatorsPersonalField
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DataViewModel(var application: Application) : ViewModel() {


    private val myFormatObj:DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val todayLocalDateTime:LocalDateTime = LocalDateTime.now()
    var todayFormattedDate:String = todayLocalDateTime.format(myFormatObj)
    private val yesterdayLocalDateTime:LocalDateTime = todayLocalDateTime.minusDays(1)
    var yesterdayFormattedDate:String = yesterdayLocalDateTime.format(myFormatObj)
    private val pastYesterdayLocalDateTime:LocalDateTime = todayLocalDateTime.minusDays(2)
    var pastYesterdayFormattedDate:String = pastYesterdayLocalDateTime.format(myFormatObj)

    private var dbRepository: DBRepository
    //private var swRepository: SWRepository

    //val smartWatchState = SmartWatchState(todayFormattedDate, yesterdayFormattedDate)

    var selectedDay by mutableStateOf("")

    var statusStartedReadingDataLasThreeDaysData by mutableStateOf(false)
    var dayHealthDataState = DayHealthDataState()
    var todayHealthsDataState = TodayHealthsDataState()
    var yesterdayHealthsDataState = YesterdayHealthsDataState()
    var dayHealthDataStateForDashBoard = DayHealthDataStateForDashBoard()

    var personalInfoFromDB = MutableLiveData<List<PersonalInfo>>()
    var personalInfoListReadFromDB = listOf<PersonalInfo>()

    var personalInfoDataState by mutableStateOf(PersonalInfoDataState())
    var invalidAlertDialogState by mutableStateOf(InvalidAlertDialogState())
    var updateAlertDialogState by mutableStateOf(UpdateAlertDialogState())

    var macAddressDeviceBluetooth: String = ""
    var nameDeviceBluetooth: String = ""

    //DatePicker for physical activity plots
    var stateShowDialogDatePicker by mutableStateOf(false)
    var stateMiliSecondsDateDialogDatePicker by mutableStateOf(0L)

    var smartWatchState by mutableStateOf(SmartWatchState(todayFormattedDate, yesterdayFormattedDate))
    var swRepository: SWRepository = SWRepository()

    var collectDataScope:Job? = null


    var stateBluetoothListScreenNavigationStatus by mutableStateOf(BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_BLUETOOTH_SCREEN)


    init {
        val swDb = SWRoomDatabase.getInstance(application)
        val physicalActivityDao = swDb.physicalActivityDao()
        val bloodPressureDao = swDb.bloodPressureDao()
        val heartRateDao = swDb.heartRateDao()
        val personalInfoDao = swDb.personalInfoDao()
        dbRepository = DBRepository(
            physicalActivityDao,
            bloodPressureDao,
            heartRateDao,
            personalInfoDao
        )
        //swRepository = SWRepository()


        dayHealthDataState.dayPhysicalActivityResultsFromDB = dbRepository.dayPhysicalActivityResultsFromDB
        todayHealthsDataState.todayPhysicalActivityResultsFromDB = dbRepository.todayPhysicalActivityResultsFromDB
        yesterdayHealthsDataState.yesterdayPhysicalActivityResultsFromDB = dbRepository.yesterdayPhysicalActivityResultsFromDB

        dayHealthDataState.dayBloodPressureResultsFromDB = dbRepository.dayBloodPressureResultsFromDB
        todayHealthsDataState.todayBloodPressureResultsFromDB = dbRepository.todayBloodPressureResultsFromDB
        yesterdayHealthsDataState.yesterdayBloodPressureResultsFromDB = dbRepository.yesterdayBloodPressureResultsFromDB

        dayHealthDataState.dayHeartRateResultsFromDB = dbRepository.dayHeartRateResultsFromDB
        todayHealthsDataState.todayHeartRateResultsFromDB = dbRepository.todayHeartRateResultsFromDB
        yesterdayHealthsDataState.yesterdayHeartRateResultsFromDB = dbRepository.yesterdayHeartRateResultsFromDB

        dayHealthDataStateForDashBoard.dayPhysicalActivityResultsFromDBForDashBoard = dbRepository.dayPhysicalActivityResultsFromDBForDashBoard
        dayHealthDataStateForDashBoard.dayBloodPressureResultsFromDBForDashBoard = dbRepository.dayBloodPressureResultsFromDBForDashBoard
        dayHealthDataStateForDashBoard.dayHeartRateResultsFromDBForDashBoard = dbRepository.dayHeartRateResultsFromDBForDashBoard

        personalInfoFromDB = dbRepository.personalInfoFromDB
        updateAlertDialogState.personalInfoAlertDialogUVLiveData =
            dbRepository.personalInfoAlertDialogUVStateLiveData

    }




    fun requestSmartWatchData(name: String = "", macAddress: String = "") {
        Log.d("DATAX", "requestSmartWatchDataModel: ")
         swRepository.requestSmartWatchData()

       viewModelScope.launch {
            delay(1000)
            smartWatchState.progressbarForFetchingDataFromSW = true
        }

    }

    fun listenDataFromSmartWatch(){
        collectDataScope = viewModelScope.launch {
            swRepository.sharedStepsFlow.collect {
                Log.d("DATAX", "SWReadingStatus.STOPPED listenDataFromSmartWatch: ${smartWatchState.todayDateValuesReadFromSW.stepList.sum()}")

                when (it.date) {
                    todayFormattedDate -> {
                        smartWatchState.todayDateValuesReadFromSW = it
                    }
                    yesterdayFormattedDate -> {
                        smartWatchState.yesterdayDateValuesFromSW = it
                        smartWatchState.progressbarForFetchingDataFromSW = false
                        smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.READ
                    }
                }
            }
        }
    }




    fun getMyHeartRateAlertDialogDataHandler(): MyHeartRateAlertDialogDataHandler {
        return dbRepository.myHeartRateAlertDialogDataHandler
    }

    fun getMyBloodPressureAlertDialogDataHandler(): MyBloodPressureAlertDialogDataHandler {
        return dbRepository.myBloodPressureAlertDialogDataHandler
    }

    fun getMyTemperatureAlertDialogDataHandler(): MyTemperatureAlertDialogDataHandler {
        return dbRepository.myTemperatureAlertDialogDataHandler
    }

    fun getMySpO2AlertDialogDataHandler(): MySpO2AlertDialogDataHandler {
        return dbRepository.mySpO2AlertDialogDataHandler
    }


    fun getDayPhysicalActivityData(dateData: String, macAddress: String) {
        dbRepository.getAnyDayPhysicalActivityData(dateData, macAddress)
    }
    fun getDayPhysicalActivityDataForDashBoard(
        dateData: String,
        macAddress: String
    ) {
        dbRepository.getAnyDayPhysicalActivityDataForDashBoard(dateData, macAddress)
    }

    fun getTodayPhysicalActivityData(macAddress: String) {
        dbRepository.getTodayPhysicalActivityData(todayFormattedDate, macAddress)
    }

    fun getYesterdayPhysicalActivityData(macAddress: String) {
        dbRepository.getYesterdayPhysicalActivityData(yesterdayFormattedDate, macAddress)
    }

    fun insertPhysicalActivityData(physicalActivity: PhysicalActivity) {
        dbRepository.insertPhysicalActivityData(physicalActivity)
    }

    fun updatePhysicalActivityData(physicalActivity: PhysicalActivity) {
        dbRepository.updatePhysicalActivityData(physicalActivity)
    }

//Blood Pressure

    fun getDayBloodPressureData(dateData: String, macAddress: String) {
        dbRepository.getAnyDayBloodPressureData(dateData, macAddress)
    }

    fun getDayBloodPressureDataForDashBoard(
        dateData: String,
        macAddress: String
    ) {
        dbRepository.getAnyDayBloodPressureDataForDashBoard(dateData, macAddress)
    }

    fun getTodayBloodPressureData(macAddress: String) {
        dbRepository.getTodayBloodPressureData(todayFormattedDate, macAddress)
    }

    fun getYesterdayBloodPressureData(macAddress: String) {
        dbRepository.getYesterdayBloodPressureData(yesterdayFormattedDate, macAddress)
    }

    fun insertBloodPressureData(bloodPressure: BloodPressure) {
        dbRepository.insertBloodPressureData(bloodPressure)
    }

    fun updateBloodPressureData(bloodPressure: BloodPressure) {
        dbRepository.updateBloodPressureData(bloodPressure)
    }


    //Heart Rate
    fun getDayHeartRateData(dateData: String, macAddress: String) {
        dbRepository.getAnyDayHeartRateData(dateData, macAddress)
    }

    fun getDayHeartRateDataForDashBoard(dateData: String, macAddress: String) {
        dbRepository.getAnyDayHeartRateDataForDashBoard(dateData, macAddress)
    }

    fun getTodayHeartRateData(macAddress: String) {
        dbRepository.getTodayHeartRateData(todayFormattedDate, macAddress)
    }

    fun getYesterdayHeartRateData(macAddress: String) {
        dbRepository.getYesterdayHeartRateData(yesterdayFormattedDate, macAddress)
    }

    fun insertHeartRateData(heartRate: HeartRate) {
        dbRepository.insertHeartRateData(heartRate)
    }

    fun updateHeartRateData(heartRate: HeartRate) {
        dbRepository.updateHeartRateData(heartRate)
    }


    //Personal data
    fun insertPersonalData(personalInfo: PersonalInfo) {
        dbRepository.insertPersonalInfo(personalInfo)
    }

    fun updatePersonalData(personalInfo: PersonalInfo) {
        dbRepository.updatePersonalInfo(personalInfo)
    }

    fun getPersonalInfoData(macAddress: String) {
        dbRepository.getPersonalInfoData(macAddress)
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
