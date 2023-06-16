package com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.screens.PhysicalActivityNestedRoute
import com.icxcu.adsmartbandapp.viewModels.PhysicalActivityViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PhysicalActivityScreenRoot(
    physicalActivityViewModel: PhysicalActivityViewModel,
    macAddressDeviceBluetooth:String,
    navMainController: NavHostController
) {

    val navLambdaBackToMainNavigationBar = remember(navMainController) {
        {
            navMainController.popBackStack(PhysicalActivityNestedRoute.PhysicalActivityRoute.route, true)
            Unit
        }
    }

    Log.d("PhysicalActivityInfo", "PhysicalActivityInfo: ")


    val dayPhysicalActivityResultsFromDB = physicalActivityViewModel.dayPhysicalActivityState

    if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
        physicalActivityViewModel.selectedDay = dayPhysicalActivityResultsFromDB[0].dateData
    }

    physicalActivityViewModel.dayStepListFromDB =
        if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
            val filter =
                dayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.STEPS }
            getIntegerListFromStringMap(filter[0].data)
        } else {
            MutableList(48) { 0 }.toList()
        }

    physicalActivityViewModel.dayDistanceListFromDB =
        if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
            val filter =
                dayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.DISTANCE }
            if (filter.isEmpty()) {
                MutableList(48) { 0.0 }.toList()
            } else {
                getDoubleListFromStringMap(filter[0].data)
            }
        } else {
            MutableList(48) { 0.0 }.toList()
        }

    physicalActivityViewModel.dayCaloriesListFromDB =
        if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
            val filter =
                dayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.CALORIES }
            if (filter.isEmpty()) {
                MutableList(48) { 0.0 }.toList()
            } else {
                getDoubleListFromStringMap(filter[0].data)
            }
        } else {
            MutableList(48) { 0.0 }.toList()
        }


    val stepsListFromDB = remember(physicalActivityViewModel) {
        {
            physicalActivityViewModel.dayStepListFromDB
        }
    }
    val distanceListFromDB = remember(physicalActivityViewModel) {
        {
            physicalActivityViewModel.dayDistanceListFromDB
        }
    }
    val caloriesListFromDB = remember(physicalActivityViewModel) {
        {
            physicalActivityViewModel.dayCaloriesListFromDB
        }
    }

    val getSelectedDay = remember(physicalActivityViewModel) {
        {
            physicalActivityViewModel.selectedDay
        }
    }

    //DialogDatePicker State
    val stateShowDialogDatePickerValue = remember(physicalActivityViewModel) {
        {
            physicalActivityViewModel.stateShowDialogDatePicker
        }
    }

    val stateShowDialogDatePickerSetter = remember(physicalActivityViewModel) {
        { value: Boolean ->
            physicalActivityViewModel.stateShowDialogDatePicker = value
        }
    }

    val stateMiliSecondsDateDialogDatePickerSetter = remember(physicalActivityViewModel) {
        { value: Long ->
            physicalActivityViewModel.stateMiliSecondsDateDialogDatePicker = value

            val date = Date(value)
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateData = formattedDate.format(date)

            physicalActivityViewModel.jobPhysicalActivityState?.cancel()
            physicalActivityViewModel.starListeningDayPhysicalActivityDB(dateData, macAddress = macAddressDeviceBluetooth, )
            physicalActivityViewModel.selectedDay = dateData
        }
    }


    PhysicalActivityLayoutScaffold(
        stepsListFromDB,
        distanceListFromDB,
        caloriesListFromDB,
        getSelectedDay,
        stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePickerSetter,
        navLambdaBackToMainNavigationBar
    )

}


sealed class PhysicalActivityScreenNavStatus{
    object Started: PhysicalActivityScreenNavStatus()
    object Leaving: PhysicalActivityScreenNavStatus()
}


