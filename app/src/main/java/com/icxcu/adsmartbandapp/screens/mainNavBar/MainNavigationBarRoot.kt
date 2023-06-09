package com.icxcu.adsmartbandapp.screens.mainNavBar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.PersonalInfoDBHandler
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun MainNavigationBarRoot(
    dataViewModel: DataViewModel,
    getFetchingDataFromSWStatus: () -> SWReadingStatus,
    bluetoothAddress: String,
    bluetoothName: String,
    navMainController: NavHostController
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

            dataViewModel.getPersonalInfoData(dataViewModel.macAddressDeviceBluetooth)
            dataViewModel.statusStartedReadingDataLasThreeDaysData = true

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