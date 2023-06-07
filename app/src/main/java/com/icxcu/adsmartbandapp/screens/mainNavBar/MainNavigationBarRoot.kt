package com.icxcu.adsmartbandapp.screens.mainNavBar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.PersonalInfoDBHandler
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import com.icxcu.adsmartbandapp.viewModels.SplashViewModel

@Composable
fun MainNavigationBarRoot(
    dataViewModel: DataViewModel,
    getFetchingDataFromSWStatus: () -> SWReadingStatus,
    bluetoothAddress: String,
    bluetoothName: String,
    navMainController: NavHostController,
    splashViewModel: SplashViewModel
){
    when (getFetchingDataFromSWStatus()) {
        SWReadingStatus.CLEARED -> {
            Log.d("DATAX", "Routes.DataHome.route: CLEARED")
        }
        SWReadingStatus.STOPPED -> {

            Log.d("DATAX", "Routes.DataHome.route: STOPPED")

            dataViewModel.smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.IN_PROGRESS

            dataViewModel.listenDataFromSmartWatch()
            dataViewModel.requestSmartWatchData(
                bluetoothName,
                bluetoothAddress
            )

            dataViewModel.macAddressDeviceBluetooth =
                bluetoothAddress
            dataViewModel.nameDeviceBluetooth = bluetoothName

            dataViewModel.getTodayPhysicalActivityData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getYesterdayPhysicalActivityData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getTodayBloodPressureData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getYesterdayBloodPressureData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getTodayHeartRateData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getYesterdayHeartRateData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.getPersonalInfoData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.statusStartedReadingDataLasThreeDaysData = true


            TodayPhysicalActivityDBHandler(
                dataViewModel = dataViewModel
            )
            YesterdayPhysicalActivityDBHandler(
                dataViewModel = dataViewModel,
                splashViewModel = splashViewModel
            )
            TodayBloodPressureDBHandler(
                dataViewModel = dataViewModel
            )
            YesterdayBloodPressureDBHandler(
                dataViewModel = dataViewModel,
                splashViewModel = splashViewModel
            )
            TodayHeartRateDBHandler(
                dataViewModel = dataViewModel
            )
            YesterdayHeartRateDBHandler(
                dataViewModel = dataViewModel,
                splashViewModel = splashViewModel
            )

        }

        SWReadingStatus.IN_PROGRESS -> {

            dataViewModel.macAddressDeviceBluetooth =
                bluetoothAddress
            dataViewModel.nameDeviceBluetooth = bluetoothName
        }

        SWReadingStatus.READ -> {

            Log.d("DATAX", "Routes.DataHome.route: READ")

            dataViewModel.macAddressDeviceBluetooth =
                bluetoothAddress
            dataViewModel.nameDeviceBluetooth = bluetoothName
        }
    }

    PersonalInfoDBHandler(
        dataViewModel
    )
    MainNavigationBarWithSwValues(
        bluetoothName = bluetoothName,
        bluetoothAddress = bluetoothAddress,
        dataViewModel = dataViewModel,
        getFetchingDataFromSWStatus = getFetchingDataFromSWStatus,
        navMainController = navMainController
    )

}