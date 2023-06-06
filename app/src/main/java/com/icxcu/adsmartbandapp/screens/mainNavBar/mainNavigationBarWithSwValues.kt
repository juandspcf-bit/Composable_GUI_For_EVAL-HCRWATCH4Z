package com.icxcu.adsmartbandapp.screens.mainNavBar


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.repositories.BloodPressureData
import com.icxcu.adsmartbandapp.repositories.TemperatureData
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.screens.BluetoothListScreenNavigationStatus
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun MainNavigationBarWithSwValues(
    bluetoothName: String,
    bluetoothAddress: String,
    dataViewModel: DataViewModel,
    getFetchingDataFromSWStatus: () -> SWReadingStatus,
    navMainController: NavHostController,
) {

    val getVisibilityProgressbarForFetchingData = remember(dataViewModel) {
        { dataViewModel.smartWatchState.progressbarForFetchingDataFromSW }
    }

    val todayValuesReadFromSW = remember(dataViewModel) {
        { dataViewModel.smartWatchState.todayDateValuesReadFromSW }
    }

    val dayHealthResultsFromDB by dataViewModel
        .dayHealthDataStateForDashBoard
        .dayHealthResultsFromDBForDashBoard
        .observeAsState(
            Values(
                MutableList(48) { 0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                MutableList(48) { 0.0 }.toList(),
                ""
            )
        )

    val dayHealthValuesReadFromDB = remember(dayHealthResultsFromDB) {
        {
            dayHealthResultsFromDB
        }
    }

    val setStateEnabledDatePickerMainScaffold = remember(dataViewModel) {
        { newValue: Boolean ->
            dataViewModel.stateEnabledDatePickerMainScaffold = newValue
        }
    }

    val getStateEnabledDatePickerMainScaffold = remember(dataViewModel) {
        {
            dataViewModel.stateEnabledDatePickerMainScaffold
        }
    }


    val dayValues = remember(
        dataViewModel,
        getFetchingDataFromSWStatus,
        dayHealthValuesReadFromDB,
        todayValuesReadFromSW
    ) {
        {
            val valuesLambda: () -> Values

            if (dataViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.NoRead
                && getFetchingDataFromSWStatus() == SWReadingStatus.IN_PROGRESS
            ) {
                valuesLambda = todayValuesReadFromSW
            } else if (dataViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.NoRead
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ
            ) {
                valuesLambda = todayValuesReadFromSW
            } else if (dataViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.ReadyForNewReadFromDashBoard
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ
            ) {
                valuesLambda = dayHealthValuesReadFromDB
            }else if (dataViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.ReadyForNewReadFromFieldsPlot
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ
            ) {
                valuesLambda = todayValuesReadFromSW
            }else if (dataViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.InProgressReading
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ && dayHealthValuesReadFromDB().stepList.sum() == 0
            ) {
                valuesLambda = dayHealthValuesReadFromDB
            } else if (dataViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.InProgressReading
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ && dayHealthValuesReadFromDB().stepList.sum() != 0
            ) {
                valuesLambda = dayHealthValuesReadFromDB
                dataViewModel.statusReadingDbForDashboard =
                    StatusReadingDbForDashboard.NewValuesRead

            } else if (dataViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.NewValuesRead
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ
            ) {
                valuesLambda = dayHealthValuesReadFromDB
            } else {
                valuesLambda = todayValuesReadFromSW
            }

            valuesLambda
        }
    }


    val getMyHeartRateAlertDialogDataHandler = remember(dataViewModel) {
        {
            dataViewModel.getMyHeartRateAlertDialogDataHandler()
        }
    }

    val heartRate by getMyHeartRateAlertDialogDataHandler().getSharedFlowHeartRate()
        .collectAsState(initial = 0)

    val getMyHeartRate = remember(heartRate) {
        { heartRate }
    }


    val getMyBloodPressureDialogDataHandler = remember(dataViewModel) {
        {
            dataViewModel.getMyBloodPressureAlertDialogDataHandler()
        }
    }

    val bloodPressure by getMyBloodPressureDialogDataHandler().getSharedFlowBloodPressure()
        .collectAsState(initial = BloodPressureData(0, 0))

    val circularProgressBloodPressure by getMyBloodPressureDialogDataHandler().getStateFlowCircularProgressBloodPressure()
        .collectAsState()

    val getCircularProgressBloodPressure = remember(circularProgressBloodPressure) {
        { circularProgressBloodPressure }
    }

    val getRealTimeBloodPressure = remember(bloodPressure) {
        { bloodPressure }
    }


    val getMySpO2AlertDialogDataHandler = remember(dataViewModel) {
        {
            dataViewModel.getMySpO2AlertDialogDataHandler()
        }
    }

    val spO2 by getMySpO2AlertDialogDataHandler().getSharedFlowSpO2().collectAsState(initial = 0.0)

    val getMySpO2 = remember(spO2) {
        { spO2 }
    }

    val getMyTemperatureAlertDialogDataHandler = remember(dataViewModel) {
        {
            dataViewModel.getMyTemperatureAlertDialogDataHandler()
        }
    }

    val temperature by getMyTemperatureAlertDialogDataHandler().getSharedFlowTemperature()
        .collectAsState(initial = TemperatureData(0.0, 0.0))

    val circularProgressTemperature by getMyTemperatureAlertDialogDataHandler().getStateFlowCircularProgressTemperature()
        .collectAsState()

    val getCircularProgressTemperature = remember(circularProgressTemperature) {
        { circularProgressTemperature }
    }

    val getRealTimeTemperature = remember(temperature) {
        { temperature }
    }

    val clearState = remember(dataViewModel) {
        {
            setStateEnabledDatePickerMainScaffold(false)
            dataViewModel.smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.CLEARED
            dataViewModel.stateBluetoothListScreenNavigationStatus =
                BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_BLUETOOTH_SCREEN
        }
    }


    //DialogDatePicker State
    val stateShowDialogDatePickerValue = remember(dataViewModel) {
        {
            dataViewModel.stateShowDialogDatePicker
        }
    }

    val setStateShowDialogDatePickerValue = remember(dataViewModel) {
        { newValue:StatusMainTitleScaffold ->
            dataViewModel.stateShowMainTitleScaffold = newValue
        }
    }

    val stateShowMainTitleScaffold = remember(dataViewModel) {
        {
            dataViewModel.stateShowMainTitleScaffold
        }
    }

    val stateShowDialogDatePickerSetter = remember(dataViewModel) {
        { value: Boolean ->
            dataViewModel.stateShowDialogDatePicker = value
        }
    }

    val stateMiliSecondsDateDialogDatePickerSetter = remember(dataViewModel) {
        { value: Long ->
            dataViewModel.stateMiliSecondsDateDialogDatePicker = value

            val date = Date(value)
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateData = formattedDate.format(date)

            dataViewModel.statusReadingDbForDashboard =
                StatusReadingDbForDashboard.InProgressReading

            dataViewModel.getDayHealthData(
                dateData,
                dataViewModel.macAddressDeviceBluetooth
            )
        }
    }


    MainNavigationBarScaffold(
        bluetoothName,
        bluetoothAddress,
        dayValues(),
        setStateEnabledDatePickerMainScaffold,
        getStateEnabledDatePickerMainScaffold,
        getVisibilityProgressbarForFetchingData,
        stateShowMainTitleScaffold,
        setStateShowDialogDatePickerValue,
        getFetchingDataFromSWStatus,
        getMyHeartRateAlertDialogDataHandler,
        getMyHeartRate,
        getMyBloodPressureDialogDataHandler,
        getRealTimeBloodPressure,
        getCircularProgressBloodPressure,
        getMySpO2AlertDialogDataHandler,
        getMySpO2,
        getMyTemperatureAlertDialogDataHandler,
        getRealTimeTemperature,
        getCircularProgressTemperature,
        clearState,
        stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePickerSetter,
        navMainController,
    )
}


class TodayHealthsDataState {

    var todayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var todayStepListReadFromDB by mutableStateOf(listOf<Int>())
    var isTodayStepsListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayStepsListInDBAlreadyUpdated by mutableStateOf(false)

    var todayDistanceListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodayDistanceListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayDistanceListInDBAlreadyUpdated by mutableStateOf(false)

    var todayCaloriesListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodayCaloriesListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayCaloriesListInDBAlreadyUpdated by mutableStateOf(false)

    var todayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()
    var todaySystolicListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodaySystolicListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodaySystolicListInDBAlreadyUpdated by mutableStateOf(false)

    var todayDiastolicListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodayDiastolicListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayDiastolicListInDBAlreadyUpdated by mutableStateOf(false)


    var todayHeartRateResultsFromDB = MutableLiveData<List<HeartRate>>()
    var todayHeartRateListReadFromDB by mutableStateOf(listOf<Double>())
    var isTodayHeartRateListAlreadyInsertedInDB by mutableStateOf(false)
    var isTodayHeartRateListInDBAlreadyUpdated by mutableStateOf(false)
}

class YesterdayHealthsDataState {
    var yesterdayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
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
    var isYesterdayHeartRateListInDBAlreadyUpdated = false
}

class DayHealthDataState {
    var dayPhysicalActivityResultsFromDB = MutableLiveData<List<PhysicalActivity>>()
    var dayStepListFromDB by mutableStateOf(listOf<Int>())
    var dayDistanceListFromDB by mutableStateOf(listOf<Double>())
    var dayCaloriesListFromDB by mutableStateOf(listOf<Double>())

    var dayBloodPressureResultsFromDB = MutableLiveData<List<BloodPressure>>()
    var daySystolicListFromDB by mutableStateOf(listOf<Double>())
    var dayDiastolicListFromDB by mutableStateOf(listOf<Double>())

    var dayHeartRateResultsFromDB = MutableLiveData<List<HeartRate>>()
    var dayHeartRateListFromDB by mutableStateOf(listOf<Double>())
}

class DayHealthDataStateForDashBoard {
    var dayHealthResultsFromDBForDashBoard = MutableLiveData<Values>()
}

class SmartWatchState(
    todayFormattedDate: String,
    yesterdayFormattedDate: String,
) {

    var progressbarForFetchingDataFromSW by mutableStateOf(false)
    var fetchingDataFromSWStatus by mutableStateOf(SWReadingStatus.STOPPED)

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
}

enum class SWReadingStatus {
    IN_PROGRESS,
    READ,
    STOPPED,
    CLEARED,
}

sealed class StatusReadingDbForDashboard {
    object NoRead : StatusReadingDbForDashboard()
    object ReadyForNewReadFromDashBoard : StatusReadingDbForDashboard()
    object ReadyForNewReadFromFieldsPlot : StatusReadingDbForDashboard()
    object InProgressReading : StatusReadingDbForDashboard()
    object NewValuesRead : StatusReadingDbForDashboard()
}

sealed class StatusMainTitleScaffold {
    object Fields : StatusMainTitleScaffold()
    object CheckHealth : StatusMainTitleScaffold()

    object Settings : StatusMainTitleScaffold()

}