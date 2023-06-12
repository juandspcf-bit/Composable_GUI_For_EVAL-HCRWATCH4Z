package com.icxcu.adsmartbandapp.viewModels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icxcu.adsmartbandapp.data.TypesTable
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
import com.icxcu.adsmartbandapp.screens.BluetoothListScreenNavigationStatus
import com.icxcu.adsmartbandapp.screens.mainNavBar.DayHealthDataState
import com.icxcu.adsmartbandapp.screens.mainNavBar.DayHealthDataStateForDashBoard
import com.icxcu.adsmartbandapp.screens.mainNavBar.SWReadingStatus
import com.icxcu.adsmartbandapp.screens.mainNavBar.SmartWatchState
import com.icxcu.adsmartbandapp.screens.mainNavBar.StatusMainTitleScaffold
import com.icxcu.adsmartbandapp.screens.mainNavBar.StatusReadingDbForDashboard
import com.icxcu.adsmartbandapp.screens.mainNavBar.TodayHealthsDataState
import com.icxcu.adsmartbandapp.screens.mainNavBar.YesterdayHealthsDataState
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.InvalidAlertDialogState
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataScreenNavStatus
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.PersonalInfoDataState
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.UpdateAlertDialogState
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.ValidatorsPersonalField
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.BloodPressureScreenNavStatus
import com.icxcu.adsmartbandapp.screens.plotsFields.heartRate.HeartRateScreenNavStatus
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.PhysicalActivityScreenNavStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DataViewModel(var application: Application) : ViewModel() {


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
    var dayHealthDataState = DayHealthDataState()
    var todayHealthsDataState = TodayHealthsDataState()
    var yesterdayHealthsDataState = YesterdayHealthsDataState()
    var dayHealthDataStateForDashBoard = DayHealthDataStateForDashBoard()

    var personalInfoFromDB = MutableLiveData<List<PersonalInfo>>()
    var personalInfoListReadFromDB = listOf<PersonalInfo>()

    var personalInfoDataState = PersonalInfoDataState()
    var invalidAlertDialogState by mutableStateOf(InvalidAlertDialogState())
    var updateAlertDialogState by mutableStateOf(UpdateAlertDialogState())

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


    var stateBluetoothListScreenNavigationStatus by mutableStateOf(
        BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_BLUETOOTH_SCREEN
    )

    var statusReadingDbForDashboard: StatusReadingDbForDashboard =
        StatusReadingDbForDashboard.NoRead
    var stateEnabledDatePickerMainScaffold by mutableStateOf(false)
    var stateShowMainTitleScaffold by mutableStateOf<StatusMainTitleScaffold>(
        StatusMainTitleScaffold.Fields
    )

    private val dbHelper: DatabaseHelperImpl
    private var physicalActivityDao: PhysicalActivityDao
    private var bloodPressureDao: BloodPressureDao

    var dayPhysicalActivityState by mutableStateOf<List<PhysicalActivity>>(listOf())
    var jobPhysicalActivityState: Job? = null
    var physicalActivityScreenNavStatus: PhysicalActivityScreenNavStatus = PhysicalActivityScreenNavStatus.Leaving

    var dayBloodPressureState by mutableStateOf<List<BloodPressure>>(listOf())
    var jobBloodPressureState: Job? = null
    var bloodPressureScreenNavStatus: BloodPressureScreenNavStatus = BloodPressureScreenNavStatus.Leaving

    var dayHeartRateState by mutableStateOf<List<HeartRate>>(listOf())
    var jobHeartRateState: Job? = null
    var heartRateScreenNavStatus: HeartRateScreenNavStatus = HeartRateScreenNavStatus.Leaving

    var personalInfoDataStateC by mutableStateOf<List<PersonalInfo>>(listOf())
    var jobPersonalInfoDataState: Job? = null
    var personalInfoDataScreenNavStatus: PersonalInfoDataScreenNavStatus = PersonalInfoDataScreenNavStatus.Leaving

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
        //swRepository = SWRepository()
        dbHelper = DatabaseHelperImpl(swDb)

        dayHealthDataState.dayPhysicalActivityResultsFromDB =
            dbRepository.dayPhysicalActivityResultsFromDB
        dayHealthDataState.dayBloodPressureResultsFromDB =
            dbRepository.dayBloodPressureResultsFromDB
        dayHealthDataState.dayHeartRateResultsFromDB = dbRepository.dayHeartRateResultsFromDB

        dayHealthDataStateForDashBoard.dayHealthResultsFromDBForDashBoard =
            dbRepository.dayHealthResultsFromDBFForDashBoard


        personalInfoFromDB = dbRepository.personalInfoFromDB
        updateAlertDialogState.personalInfoAlertDialogUVLiveData =
            dbRepository.personalInfoAlertDialogUVStateLiveData


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
                            this@DataViewModel,
                            dbRepository
                        )

                        updateOrInsertBloodPressureDataBase(
                            it,
                            todayFormattedDate,
                            macAddressDeviceBluetooth,
                            this@DataViewModel,
                            dbRepository
                        )

                        updateOrInsertHeartRateDataBase(
                            it,
                            todayFormattedDate,
                            macAddressDeviceBluetooth,
                            this@DataViewModel,
                            dbRepository
                        )

                    }

                    yesterdayFormattedDate -> {

                        smartWatchState.yesterdayDateValuesFromSW = it
                        updateOrInsertPhysicalActivityDataBase(
                            it,
                            yesterdayFormattedDate,
                            macAddressDeviceBluetooth,
                            this@DataViewModel,
                            dbRepository
                        )

                        updateOrInsertBloodPressureDataBase(
                            it,
                            yesterdayFormattedDate,
                            macAddressDeviceBluetooth,
                            this@DataViewModel,
                            dbRepository
                        )

                        updateOrInsertHeartRateDataBase(
                            it,
                            yesterdayFormattedDate,
                            macAddressDeviceBluetooth,
                            this@DataViewModel,
                            dbRepository
                        )

                        smartWatchState.progressbarForFetchingDataFromSW = false
                        smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.READ
                    }
                }
            }
        }
    }


    fun starListeningDayPhysicalActivityDB(dateData: String="", macAddress: String = "", ) {
        jobPhysicalActivityState = viewModelScope.launch {
            dbRepository.getDayPhysicalActivityWithFlow(dateData, macAddress)
                .distinctUntilChanged()
                .collect {
                    Log.d("DB_FLOW", "starListeningDB of $macAddress: $it")
                    dayPhysicalActivityState = it
                }
        }
    }

    fun starListeningBloodPressureDB(dateData: String="", macAddress: String = "", ) {
        jobBloodPressureState = viewModelScope.launch {
            dbRepository.getDayBloodPressureWithFlow(dateData, macAddress)
                .distinctUntilChanged()
                .collect {
                    Log.d("DB_FLOW", "starListeningDB of $macAddress: $it")
                    dayBloodPressureState = it
                }
        }
    }

    fun starListeningHeartRateDB(dateData: String="", macAddress: String = "", ) {
        jobHeartRateState = viewModelScope.launch {
            dbRepository.getDayHeartRateWithFlow(dateData, macAddress)
                .distinctUntilChanged()
                .collect {
                    Log.d("DB_FLOW", "starListeningDB of $macAddress: $it")
                    dayHeartRateState = it
                }
        }
    }

    fun starListeningPersonalInfoDB(macAddress: String = "") {
        jobHeartRateState = viewModelScope.launch {

            val dataDeferred = async {
                dbRepository.getPersonalInfoWithCoroutine(macAddress)
            }

            val dataCoroutineFromDB = dataDeferred.await()

            personalInfoDataStateC = dataCoroutineFromDB.ifEmpty {
                MutableList(1) { PersonalInfo(
                    id = -1,
                    macAddress = "",
                    typesTable= TypesTable.PERSONAL_INFO,
                    name = "",
                    birthdate = "",
                    weight = 0.0,
                    height = 0.0 ) }.toList()
            }

            personalInfoDataState.apply {
                name = personalInfoDataStateC[0].name
                date = personalInfoDataStateC[0].birthdate
                height = personalInfoDataStateC[0].height.toString()
                weight = personalInfoDataStateC[0].weight.toString()
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


    fun getDayPhysicalActivityData(dateData: String, macAddress: String) {
        dbRepository.getAnyDayPhysicalActivityData(dateData, macAddress)
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
            "Birthday" to ValidatorsPersonalField.dateValidator(getPersonalInfoDataStateState().date)
                .isNotBlank(),
            "Weight" to ValidatorsPersonalField.weightValidator(getPersonalInfoDataStateState().weight)
                .isNotBlank(),
            "Height" to ValidatorsPersonalField.heightValidator(getPersonalInfoDataStateState().height)
                .isNotBlank()
        )

        val invalidFields = mutableListOf<String>()
        validationFields.forEach { (key, value) ->
            if (value.not()) {
                invalidFields.add(key)
            }
        }

        return invalidFields.toList()


    }

}
