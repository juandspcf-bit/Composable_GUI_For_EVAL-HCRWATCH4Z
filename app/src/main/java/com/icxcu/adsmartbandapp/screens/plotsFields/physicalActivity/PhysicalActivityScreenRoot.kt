package com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PhysicalActivityScreenRoot(
    dataViewModel: DataViewModel,
    navLambda: () -> Unit
) {

    Log.d("PhysicalActivityInfo", "PhysicalActivityInfo: ")
    val getDayPhysicalActivityData = remember(dataViewModel) {
        {
            dataViewModel.dayHealthDataState
        }
    }

    val dayPhysicalActivityResultsFromDB = dataViewModel.dayPhysicalActivityState

    if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
        dataViewModel.selectedDay = dayPhysicalActivityResultsFromDB[0].dateData
    }

    getDayPhysicalActivityData().dayStepListFromDB =
        if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
            val filter =
                dayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.STEPS }
            getIntegerListFromStringMap(filter[0].data)
        } else {
            MutableList(48) { 0 }.toList()
        }

    getDayPhysicalActivityData().dayDistanceListFromDB =
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

    getDayPhysicalActivityData().dayCaloriesListFromDB =
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


    val stepsListFromDB = remember(dataViewModel) {
        {
            dataViewModel.dayHealthDataState.dayStepListFromDB
        }
    }
    val distanceListFromDB = remember(dataViewModel) {
        {
            dataViewModel.dayHealthDataState.dayDistanceListFromDB
        }
    }
    val caloriesListFromDB = remember(dataViewModel) {
        {
            dataViewModel.dayHealthDataState.dayCaloriesListFromDB
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

            dataViewModel.jobPhysicalActivityState?.cancel()
            dataViewModel.starListeningDayPhysicalActivityDB(dateData, macAddress = dataViewModel.macAddressDeviceBluetooth, )
            dataViewModel.selectedDay = dateData
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
        navLambda
    )

}


