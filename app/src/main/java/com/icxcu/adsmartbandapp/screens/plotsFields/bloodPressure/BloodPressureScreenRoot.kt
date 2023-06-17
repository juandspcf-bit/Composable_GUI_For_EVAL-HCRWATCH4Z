package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.screens.BloodPressureNestedRoute
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.BloodPressureViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BloodPressureScreenRoot(
    dataViewModel: BloodPressureViewModel,
    macAddressDeviceBluetooth:String,
    navMainController: NavController
) {


    val navLambdaBackToMainNavigationBar = remember(navMainController) {
        {
            navMainController.popBackStack(BloodPressureNestedRoute.BloodPressureMainRoute().route, true)
            Unit
        }
    }

    val dayBloodPressureResultsFromDB = dataViewModel.dayBloodPressureState

    if (dayBloodPressureResultsFromDB.isEmpty().not()) {
        Log.d("date", "BloodPressureInfo: $dayBloodPressureResultsFromDB")
        dataViewModel.selectedDay = dayBloodPressureResultsFromDB[0].dateData
    }

    dataViewModel.daySystolicListFromDB =
        if (dayBloodPressureResultsFromDB.isEmpty().not()) {
            val filter =
                dayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.SYSTOLIC }
            getDoubleListFromStringMap(filter[0].data)
        } else {
            MutableList(48) { 0.0 }.toList()
        }

    dataViewModel.dayDiastolicListFromDB =
        if (dayBloodPressureResultsFromDB.isEmpty().not()) {
            val filter =
                dayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.DIASTOLIC }
            if (filter.isEmpty()) {
                MutableList(48) { 0.0 }.toList()
            } else {
                getDoubleListFromStringMap(filter[0].data)
            }
        } else {
            MutableList(48) { 0.0 }.toList()
        }

    val systolicListFromDB = remember(dataViewModel) {
        {
            dataViewModel.daySystolicListFromDB
        }
    }
    val diastolicListFromDB = remember(dataViewModel) {
        {
            dataViewModel.dayDiastolicListFromDB
        }
    }

    val getSelectedDay = remember(dataViewModel) {
        {
            dataViewModel.selectedDay
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

            dataViewModel.jobBloodPressureState?.cancel()
            dataViewModel.starListeningBloodPressureDB(dateData, macAddressDeviceBluetooth, )
            dataViewModel.selectedDay = dateData
        }
    }

    BloodPressureLayoutScaffold(
        systolicList = systolicListFromDB,
        diastolicList = diastolicListFromDB,
        getSelectedDay = getSelectedDay,
        stateShowDialogDatePickerSetter = stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue = stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePickerSetter = stateMiliSecondsDateDialogDatePickerSetter,
        navLambda = navLambdaBackToMainNavigationBar
    )

}

sealed class BloodPressureScreenNavStatus{
    object Started: BloodPressureScreenNavStatus()
    object Leaving: BloodPressureScreenNavStatus()
}