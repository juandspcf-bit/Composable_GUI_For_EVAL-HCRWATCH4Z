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
import com.icxcu.adsmartbandapp.repositories.BloodPressureData
import com.icxcu.adsmartbandapp.repositories.TemperatureData
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.screens.bluetoothScanner.BluetoothListScreenNavigationStatus
import com.icxcu.adsmartbandapp.viewModels.BluetoothScannerViewModel
import com.icxcu.adsmartbandapp.viewModels.MainNavigationViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun MainNavigationBarWithSwValues(
    bluetoothName: String,
    bluetoothAddress: String,
    mainNavigationViewModel: MainNavigationViewModel,
    bluetoothScannerViewModel: BluetoothScannerViewModel,
    getFetchingDataFromSWStatus: () -> SWReadingStatus,
    navMainController: NavHostController,
) {

    val getVisibilityProgressbarForFetchingData = remember(mainNavigationViewModel) {
        { mainNavigationViewModel.smartWatchState.progressbarForFetchingDataFromSW }
    }

    val todayValuesReadFromSW = remember(mainNavigationViewModel) {
        { mainNavigationViewModel.smartWatchState.todayDateValuesReadFromSW }
    }






    val dayHealthResultsFromDB by mainNavigationViewModel
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

    val setStateEnabledDatePickerMainScaffold = remember(mainNavigationViewModel) {
        { newValue: Boolean ->
            mainNavigationViewModel.stateEnabledDatePickerMainScaffold = newValue
        }
    }

    val getStateEnabledDatePickerMainScaffold = remember(mainNavigationViewModel) {
        {
            mainNavigationViewModel.stateEnabledDatePickerMainScaffold
        }
    }


    val dayValues = remember(
        mainNavigationViewModel,
        getFetchingDataFromSWStatus,
        dayHealthValuesReadFromDB,
        todayValuesReadFromSW
    ) {
        {
            val valuesLambda: () -> Values

            if (mainNavigationViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.NoRead
                && getFetchingDataFromSWStatus() == SWReadingStatus.IN_PROGRESS
            ) {
                valuesLambda = todayValuesReadFromSW
            } else if (mainNavigationViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.NoRead
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ
            ) {
                valuesLambda = todayValuesReadFromSW
            } else if (mainNavigationViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.ReadyForNewReadFromDashBoard
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ
            ) {
                valuesLambda = dayHealthValuesReadFromDB
            }else if (mainNavigationViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.ReadyForNewReadFromFieldsPlot
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ
            ) {
                valuesLambda = todayValuesReadFromSW
            }else if (mainNavigationViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.InProgressReading
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ && dayHealthValuesReadFromDB().stepList.sum() == 0
            ) {
                valuesLambda = dayHealthValuesReadFromDB
            } else if (mainNavigationViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.InProgressReading
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ && dayHealthValuesReadFromDB().stepList.sum() != 0
            ) {
                valuesLambda = dayHealthValuesReadFromDB
                mainNavigationViewModel.statusReadingDbForDashboard =
                    StatusReadingDbForDashboard.NewValuesRead

            } else if (mainNavigationViewModel.statusReadingDbForDashboard == StatusReadingDbForDashboard.NewValuesRead
                && getFetchingDataFromSWStatus() == SWReadingStatus.READ
            ) {
                valuesLambda = dayHealthValuesReadFromDB
            } else {
                valuesLambda = todayValuesReadFromSW
            }

            valuesLambda
        }
    }


    val getMyHeartRateAlertDialogDataHandler = remember(mainNavigationViewModel) {
        {
            mainNavigationViewModel.getMyHeartRateAlertDialogDataHandler()
        }
    }

    val heartRate by getMyHeartRateAlertDialogDataHandler().getSharedFlowHeartRate()
        .collectAsState(initial = 0)

    val getMyHeartRate = remember(heartRate) {
        { heartRate }
    }


    val getMyBloodPressureDialogDataHandler = remember(mainNavigationViewModel) {
        {
            mainNavigationViewModel.getMyBloodPressureAlertDialogDataHandler()
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


    val getMySpO2AlertDialogDataHandler = remember(mainNavigationViewModel) {
        {
            mainNavigationViewModel.getMySpO2AlertDialogDataHandler()
        }
    }

    val spO2 by getMySpO2AlertDialogDataHandler().getSharedFlowSpO2().collectAsState(initial = 0.0)

    val getMySpO2 = remember(spO2) {
        { spO2 }
    }

    val getMyTemperatureAlertDialogDataHandler = remember(mainNavigationViewModel) {
        {
            mainNavigationViewModel.getMyTemperatureAlertDialogDataHandler()
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

    val clearState = remember(mainNavigationViewModel) {
        {
            setStateEnabledDatePickerMainScaffold(false)
            mainNavigationViewModel.smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.CLEARED
            bluetoothScannerViewModel.stateBluetoothListScreenNavigationStatus =
                BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_BLUETOOTH_SCREEN

        }
    }


    //DialogDatePicker State
    val stateShowDialogDatePickerValue = remember(mainNavigationViewModel) {
        {
            mainNavigationViewModel.stateShowDialogDatePicker
        }
    }

    val setStateShowDialogDatePickerValue = remember(mainNavigationViewModel) {
        { newValue:StatusMainTitleScaffold ->
            mainNavigationViewModel.stateShowMainTitleScaffold = newValue
        }
    }

    val stateShowMainTitleScaffold = remember(mainNavigationViewModel) {
        {
            mainNavigationViewModel.stateShowMainTitleScaffold
        }
    }

    val stateShowDialogDatePickerSetter = remember(mainNavigationViewModel) {
        { value: Boolean ->
            mainNavigationViewModel.stateShowDialogDatePicker = value
        }
    }

    val stateMiliSecondsDateDialogDatePickerSetter = remember(mainNavigationViewModel) {
        { value: Long ->
            mainNavigationViewModel.stateMiliSecondsDateDialogDatePicker = value

            val date = Date(value)
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateData = formattedDate.format(date)

            mainNavigationViewModel.statusReadingDbForDashboard =
                StatusReadingDbForDashboard.InProgressReading

            mainNavigationViewModel.getDayHealthData(
                dateData,
                mainNavigationViewModel.macAddressDeviceBluetooth
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