package com.icxcu.adsmartbandapp.screens.mainNavBar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.viewModels.MainNavigationViewModel

@Composable
fun MainNavigationBarRoot(
    mainNavigationViewModel: MainNavigationViewModel,
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

            mainNavigationViewModel.smartWatchState.fetchingDataFromSWStatus = SWReadingStatus.IN_PROGRESS

            mainNavigationViewModel.macAddressDeviceBluetooth = bluetoothAddress
            mainNavigationViewModel.nameDeviceBluetooth = bluetoothName

            mainNavigationViewModel.listenDataFromSmartWatch()
            mainNavigationViewModel.requestSmartWatchData(
                bluetoothName,
                bluetoothAddress
            )


            mainNavigationViewModel.statusStartedReadingDataLasThreeDaysData = true

        }

        SWReadingStatus.IN_PROGRESS -> {


            mainNavigationViewModel.macAddressDeviceBluetooth =
                bluetoothAddress
            mainNavigationViewModel.nameDeviceBluetooth = bluetoothName
        }

        SWReadingStatus.READ -> {

            Log.d("DATAX", "Routes.DataHome.route: READ")

            mainNavigationViewModel.macAddressDeviceBluetooth =
                bluetoothAddress
            mainNavigationViewModel.nameDeviceBluetooth = bluetoothName
        }
    }

/*    PersonalInfoDBHandler(
        dataViewModel
    )*/

    MainNavigationBarWithSwValues(
        bluetoothName = bluetoothName,
        bluetoothAddress = bluetoothAddress,
        mainNavigationViewModel = mainNavigationViewModel,
        getFetchingDataFromSWStatus = getFetchingDataFromSWStatus,
        navMainController = navMainController
    )

}