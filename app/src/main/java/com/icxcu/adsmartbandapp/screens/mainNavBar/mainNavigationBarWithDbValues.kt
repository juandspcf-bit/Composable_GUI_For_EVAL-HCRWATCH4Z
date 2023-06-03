package com.icxcu.adsmartbandapp.screens.mainNavBar


import android.util.Log
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
fun MainNavigationBarWithDbValues(
    bluetoothName: String,
    bluetoothAddress: String,
    dataViewModel: DataViewModel,
    navMainController: NavHostController
) {



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


    val dayPhysicalActivityResultsFromDB by dataViewModel
        .dayHealthDataStateForDashBoard
        .dayPhysicalActivityResultsFromDBForDashBoard
        .observeAsState(
        MutableList(0) { PhysicalActivity() }.toList()
        )


    Log.d("DATA_FROM_DATABASE", "MainNavigationBar: $dayPhysicalActivityResultsFromDB")






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
                BluetoothListScreenNavigationStatus.IN_PROGRESS_TO_BLUETOOTH_SCREEN
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

            dataViewModel.getDayPhysicalActivityDataForDashBoard(
                dateData,
                dataViewModel.macAddressDeviceBluetooth
            )

            dataViewModel.getDayBloodPressureDataForDashBoard(
                dateData,
                dataViewModel.macAddressDeviceBluetooth
            )

            dataViewModel.getDayHeartRateDataForDashBoard(
                dateData,
                dataViewModel.macAddressDeviceBluetooth
            )

/*            navMainController.navigate(Routes.CircularProgressLoading.route){
                popUpTo(Routes.DataHome.route)
            }*/

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

