package com.icxcu.adsmartbandapp.screens.dashBoard


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.viewModels.DataViewModel


@Composable
fun DashBoard(
    bluetoothName: String,
    bluetoothAddress: String,
    dataViewModel: DataViewModel,
    navMainController: NavHostController,
    navLambda: () -> Unit
) {

    TodayPhysicalActivityDBHandler(dataViewModel = dataViewModel)
    YesterdayPhysicalActivityDBHandler(dataViewModel = dataViewModel)
    TodayBloodPressureDBHandler(dataViewModel = dataViewModel)
    YesterdayBloodPressureDBHandler(dataViewModel = dataViewModel)
    TodayHeartRateDBHandler(dataViewModel = dataViewModel)
    YesterdayHeartRateDBHandler(dataViewModel = dataViewModel)

    val dayDateValuesReadFromSW = {
        dataViewModel.todayDateValuesReadFromSW
    }
    val getVisibilityProgressbarForFetchingData = {
        dataViewModel.progressbarForFetchingDataFromSW
    }


    val heartRate by dataViewModel.getSharedFlowHeartRate().collectAsState(initial = 0)

    val getRealTimeHeartRate={
        heartRate
    }

    val requestRealTimeHeartRate = {
        dataViewModel.requestSmartWatchDataHeartRate()
    }

    val stopRequestRealTimeHeartRate = {
        dataViewModel.stopRequestSmartWatchDataHeartRate()
    }


    val bloodPressure by dataViewModel.getSharedFlowBloodPressure().collectAsState(initial = mapOf(
        "systolic" to 0,
        "diastolic" to 0
    ))

    val getRealTimeBloodPressure={
        bloodPressure
    }

    val requestRealTimeBloodPressure = {
        dataViewModel.requestSmartWatchDataBloodPressure()
    }

    val stopRequestRealTimeBloodPressure = {
        dataViewModel.stopRequestSmartWatchDataBloodPressure()
    }


    DashBoardScaffold(
        bluetoothName,
        bluetoothAddress,
        dayDateValuesReadFromSW,
        getVisibilityProgressbarForFetchingData,
        requestRealTimeHeartRate,
        getRealTimeHeartRate,
        stopRequestRealTimeHeartRate,
        getRealTimeBloodPressure,
        requestRealTimeBloodPressure,
        stopRequestRealTimeBloodPressure,
        navMainController,
        navLambda,
    )


}



