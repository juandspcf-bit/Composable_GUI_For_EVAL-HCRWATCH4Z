package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.database.BloodPressureDao
import com.icxcu.adsmartbandapp.database.DatabaseHelperImpl
import com.icxcu.adsmartbandapp.database.PhysicalActivityDao
import com.icxcu.adsmartbandapp.database.SWRoomDatabase
import com.icxcu.adsmartbandapp.repositories.DBRepository
import com.icxcu.adsmartbandapp.repositories.MyBloodPressureAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MyHeartRateAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MySpO2AlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.MyTemperatureAlertDialogDataHandler
import com.icxcu.adsmartbandapp.repositories.SWRepository
import com.icxcu.adsmartbandapp.screens.mainNavBar.DayHealthDataStateForDashBoard
import com.icxcu.adsmartbandapp.screens.mainNavBar.SWReadingStatus
import com.icxcu.adsmartbandapp.screens.mainNavBar.SmartWatchState
import com.icxcu.adsmartbandapp.screens.mainNavBar.StatusMainTitleScaffold
import com.icxcu.adsmartbandapp.screens.mainNavBar.StatusReadingDbForDashboard
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainNavigationViewModelWithParam (var application: Application) : ViewModel() {


    private val myFormatObj: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val todayLocalDateTime: LocalDateTime = LocalDateTime.now()
    var todayFormattedDate: String = todayLocalDateTime.format(myFormatObj)
    private val yesterdayLocalDateTime: LocalDateTime = todayLocalDateTime.minusDays(1)
    var yesterdayFormattedDate: String = yesterdayLocalDateTime.format(myFormatObj)
    private val pastYesterdayLocalDateTime: LocalDateTime = todayLocalDateTime.minusDays(2)
    var pastYesterdayFormattedDate: String = pastYesterdayLocalDateTime.format(myFormatObj)

    private var dbRepository: DBRepository


    var selectedDay by mutableStateOf("")

    var statusStartedReadingDataLasThreeDaysData by mutableStateOf(false)

    var dayHealthDataStateForDashBoard = DayHealthDataStateForDashBoard()

    var personalInfoListReadFromDB = listOf<PersonalInfo>()


    var macAddressDeviceBluetooth: String = ""
    var nameDeviceBluetooth: String = ""

    //DatePicker for physical activity plots
    var stateShowDialogDatePicker by mutableStateOf(false)
    var stateMiliSecondsDateDialogDatePicker by mutableStateOf(0L)

    var smartWatchState by mutableStateOf(
        SmartWatchState(
            todayFormattedDate,
            yesterdayFormattedDate
        )
    )
    var swRepository: SWRepository = SWRepository()

    var collectDataScope: Job? = null



    var statusReadingDbForDashboard: StatusReadingDbForDashboard =
        StatusReadingDbForDashboard.NoRead
    var stateEnabledDatePickerMainScaffold by mutableStateOf(false)
    var stateShowMainTitleScaffold by mutableStateOf<StatusMainTitleScaffold>(
        StatusMainTitleScaffold.Fields
    )

    private val dbHelper: DatabaseHelperImpl
    private var physicalActivityDao: PhysicalActivityDao
    private var bloodPressureDao: BloodPressureDao

    init {
        val swDb = SWRoomDatabase.getInstance(application)
        physicalActivityDao = swDb.physicalActivityDao()
        bloodPressureDao = swDb.bloodPressureDao()
        val heartRateDao = swDb.heartRateDao()
        val personalInfoDao = swDb.personalInfoDao()
        dbRepository = DBRepository(
            physicalActivityDao,
            bloodPressureDao,
            heartRateDao,
            personalInfoDao
        )

        dbHelper = DatabaseHelperImpl(swDb)

        dayHealthDataStateForDashBoard.dayHealthResultsFromDBForDashBoard =
            dbRepository.dayHealthResultsFromDBFForDashBoard

    }

    fun listenDataFromSmartWatch() {
        Log.d("FetchingDataFromSWStatusX", "listenDataFromSmartWatch")
        collectDataScope = viewModelScope.launch {
            swRepository.sharedStepsFlow.collect {
                when (it.date) {
                    todayFormattedDate -> {

                        smartWatchState.todayDateValuesReadFromSW = it
                        updateOrInsertPhysicalActivityDataBase(
                            it,
                            todayFormattedDate,
                            macAddressDeviceBluetooth,
                            this@MainNavigationViewModel,
                            dbRepository
                        )

                        updateOrInsertBloodPressureDataBase(
                            it,
                            todayFormattedDate,
                            macAddressDeviceBluetooth,
                            this@MainNavigationViewModel,
                            dbRepository
                        )

                        updateOrInsertHeartRateDataBase(
                            it,
                            todayFormattedDate,
                            macAddressDeviceBluetooth,
                            this@MainNavigationViewModel,
                            dbRepository
                        )

                    }

                    yesterdayFormattedDate -> {

                        smartWatchState.yesterdayDateValuesFromSW = it
                        updateOrInsertPhysicalActivityDataBase(
                            it,
                            yesterdayFormattedDate,
                            macAddressDeviceBluetooth,
                            this@MainNavigationViewModel,
                            dbRepository
                        )

                        updateOrInsertBloodPressureDataBase(
                            it,
                            yesterdayFormattedDate,
                            macAddressDeviceBluetooth,
                            this@MainNavigationViewModel,
                            dbRepository
                        )

                        updateOrInsertHeartRateDataBase(
                            it,
                            yesterdayFormattedDate,
                            macAddressDeviceBluetooth,
                            this@MainNavigationViewModel,
                            dbRepository
                        )

                        smartWatchState.progressbarForFetchingDataFromSW = false
                        smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.READ
                    }
                }
            }
        }
    }

    fun requestSmartWatchData(name: String = "", macAddress: String = "") {
        swRepository.requestSmartWatchData()

        //starListeningDB(macAddress = macAddressDeviceBluetooth)

        viewModelScope.launch {
            delay(1000)
            smartWatchState.progressbarForFetchingDataFromSW = true
        }

    }


    fun getDayHealthData(
        queryDate: String,
        queryMacAddress: String,
    ) {
        dbRepository.getDayHealthData(
            queryDate,
            queryMacAddress
        )
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


    // data fields db handling
    fun insertPhysicalActivityData(physicalActivity: PhysicalActivity) {
        dbRepository.insertPhysicalActivityData(physicalActivity)
    }

    fun updatePhysicalActivityData(physicalActivity: PhysicalActivity) {
        dbRepository.updatePhysicalActivityData(physicalActivity)
    }

    fun insertBloodPressureData(bloodPressure: BloodPressure) {
        dbRepository.insertBloodPressureData(bloodPressure)
    }

    fun updateBloodPressureData(bloodPressure: BloodPressure) {
        dbRepository.updateBloodPressureData(bloodPressure)
    }

    fun insertHeartRateData(heartRate: HeartRate) {
        dbRepository.insertHeartRateData(heartRate)
    }

    fun updateHeartRateData(heartRate: HeartRate) {
        dbRepository.updateHeartRateData(heartRate)
    }

}
