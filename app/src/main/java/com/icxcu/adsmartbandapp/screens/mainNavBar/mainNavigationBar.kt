package com.icxcu.adsmartbandapp.screens.mainNavBar


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.MainNavigationBarScaffold
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.TodayBloodPressureDBHandler
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.TodayHeartRateDBHandler
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.TodayPhysicalActivityDBHandler
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.YesterdayBloodPressureDBHandler
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.YesterdayHeartRateDBHandler
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.YesterdayPhysicalActivityDBHandler
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun MainNavigationBar(
    bluetoothName: String,
    bluetoothAddress: String,
    dataViewModel: DataViewModel,
    splashViewModel: SplashViewModel,
    navMainController: NavHostController
) {

    TodayPhysicalActivityDBHandler(dataViewModel = dataViewModel)
    YesterdayPhysicalActivityDBHandler(
        dataViewModel = dataViewModel,
        splashViewModel = splashViewModel
    )
    TodayBloodPressureDBHandler(dataViewModel = dataViewModel, splashViewModel = splashViewModel)
    YesterdayBloodPressureDBHandler(
        dataViewModel = dataViewModel,
        splashViewModel = splashViewModel
    )
    TodayHeartRateDBHandler(dataViewModel = dataViewModel, splashViewModel = splashViewModel)
    YesterdayHeartRateDBHandler(dataViewModel = dataViewModel, splashViewModel = splashViewModel)

    val getSmartWatchState = remember(dataViewModel) {
        {
            dataViewModel.smartWatchState
        }
    }


    val dayDateValuesReadFromSW = {
        getSmartWatchState().todayDateValuesReadFromSW
    }

    val getVisibilityProgressbarForFetchingData = {
        getSmartWatchState().progressbarForFetchingDataFromSW
    }


    val getMyHeartRateAlertDialogDataHandler = {
        dataViewModel.getMyHeartRateAlertDialogDataHandler()
    }

    val heartRate by getMyHeartRateAlertDialogDataHandler().getSharedFlowHeartRate()
        .collectAsState(initial = 0)

    val getMyHeartRate = {
        heartRate
    }


    val getMyBloodPressureDialogDataHandler = {
        dataViewModel.getMyBloodPressureAlertDialogDataHandler()
    }
    val bloodPressure by getMyBloodPressureDialogDataHandler().getSharedFlowBloodPressure()
        .collectAsState(initial = BloodPressureData(0, 0))

    val circularProgressBloodPressure by getMyBloodPressureDialogDataHandler().getStateFlowCircularProgressBloodPressure()
        .collectAsState()

    val getCircularProgressBloodPressure = {
        circularProgressBloodPressure
    }

    val getRealTimeBloodPressure = {
        bloodPressure
    }


    val getMySpO2AlertDialogDataHandler = {
        dataViewModel.getMySpO2AlertDialogDataHandler()
    }

    val spO2 by getMySpO2AlertDialogDataHandler().getSharedFlowSpO2().collectAsState(initial = 0.0)

    val getMySpO2 = {
        spO2
    }

    val getMyTemperatureAlertDialogDataHandler = {
        dataViewModel.getMyTemperatureAlertDialogDataHandler()
    }

    val temperature by getMyTemperatureAlertDialogDataHandler().getSharedFlowTemperature()
        .collectAsState(initial = TemperatureData(0.0, 0.0))

    val circularProgressTemperature by getMyTemperatureAlertDialogDataHandler().getStateFlowCircularProgressTemperature()
        .collectAsState()

    val getCircularProgressTemperature = {
        circularProgressTemperature
    }

    val getRealTimeTemperature = {
        temperature
    }

    val clearState = remember(dataViewModel) {
        {
            dataViewModel.smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.CLEARED
            dataViewModel.stateBluetoothListScreenNavigationStatus =
                BluetoothListScreenNavigationStatus.NO_IN_PROGRESS
        }
    }


    //DialogDatePicker State
    val stateShowDialogDatePickerValue = remember(dataViewModel) {
        {
            dataViewModel.stateShowDialogDatePicker
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

            dataViewModel.getDayPhysicalActivityData(
                dateData,
                dataViewModel.macAddressDeviceBluetooth
            )

            dataViewModel.getDayBloodPressureData(
                dateData,
                dataViewModel.macAddressDeviceBluetooth
            )

            dataViewModel.getDayHeartRateData(
                dateData,
                dataViewModel.macAddressDeviceBluetooth
            )

            navMainController.navigate(Routes.CircularProgressLoading.route){
                popUpTo(Routes.DataHome.route)
            }

        }
    }

    MainNavigationBarScaffold(
        bluetoothName,
        bluetoothAddress,
        dayDateValuesReadFromSW,
        getVisibilityProgressbarForFetchingData,
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


class TodayPhysicalActivityInfoState {

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

class YesterdayPhysicalActivityInfoState {
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

class DayPhysicalActivityInfoState {
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

class SmartWatchState(
    todayFormattedDate: String,
    yesterdayFormattedDate: String
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