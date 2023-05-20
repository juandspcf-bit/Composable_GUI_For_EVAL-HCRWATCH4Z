package com.icxcu.adsmartbandapp.screens.dashBoard


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.repositories.BloodPressureData
import com.icxcu.adsmartbandapp.repositories.TemperatureData
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





    val getMyHeartRateAlertDialogDataHandler = {
         dataViewModel.getMyHeartRateAlertDialogDataHandler()
    }

    val heartRate by getMyHeartRateAlertDialogDataHandler().getSharedFlowHeartRate().collectAsState(initial = 0)

    val getMyHeartRate = {
        heartRate
    }



    val bloodPressure by dataViewModel.getSharedFlowBloodPressure().collectAsState(initial = BloodPressureData(0 ,0 ))

    val circularProgressBloodPressure by dataViewModel.getStateFlowCircularProgressBloodPressure().collectAsState()

    val getCircularProgressBloodPressure={
        circularProgressBloodPressure
    }

    val getRealTimeBloodPressure={
        bloodPressure
    }

    val requestRealTimeBloodPressure = {
        dataViewModel.requestSmartWatchDataBloodPressure()
    }

    val stopRequestRealTimeBloodPressure = {
        dataViewModel.stopRequestSmartWatchDataBloodPressure()
    }


    val temperature by dataViewModel.getSharedFlowTemperature().collectAsState(initial = TemperatureData(0.0, 0.0))

    val circularProgressTemperature by dataViewModel.getStateFlowCircularProgressTemperature().collectAsState()

    val getCircularProgressTemperature={
        circularProgressTemperature
    }

    val getRealTimeTemperature={
        temperature
    }

    val requestRealTimeTemperature = {
        dataViewModel.requestSmartWatchDataTemperature()
    }

    val stopRequestRealTimeTemperature = {
        dataViewModel.stopRequestSmartWatchDataTemperature()
    }



    DashBoardScaffold(
        bluetoothName,
        bluetoothAddress,
        dayDateValuesReadFromSW,
        getVisibilityProgressbarForFetchingData,
        getMyHeartRateAlertDialogDataHandler,
        getMyHeartRate,
        getRealTimeBloodPressure,
        requestRealTimeBloodPressure,
        stopRequestRealTimeBloodPressure,
        getCircularProgressBloodPressure,
        getRealTimeTemperature,
        requestRealTimeTemperature,
        stopRequestRealTimeTemperature,
        getCircularProgressTemperature,
        navMainController,
        navLambda,
    )


}



