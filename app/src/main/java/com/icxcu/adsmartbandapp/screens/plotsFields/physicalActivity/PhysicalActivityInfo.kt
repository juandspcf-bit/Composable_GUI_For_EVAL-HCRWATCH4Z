package com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PhysicalActivity
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PhysicalActivityInfo(
    dataViewModel: DataViewModel,
    navLambda: () -> Unit
) {

    Log.d("PhysicalActivityInfo", "PhysicalActivityInfo: ")
    val getDayPhysicalActivityData = remember(dataViewModel) {
        {
            dataViewModel.dayPhysicalActivityInfoState
        }
    }

    //Data state
    val dayPhysicalActivityResultsFromDB by getDayPhysicalActivityData().dayPhysicalActivityResultsFromDB.observeAsState(
        MutableList(0) { PhysicalActivity() }.toList()
    )

    if(dayPhysicalActivityResultsFromDB.isEmpty().not()){
        dataViewModel.selectedDay = dayPhysicalActivityResultsFromDB[0].dateData
    }

    getDayPhysicalActivityData().dayStepListFromDB = if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = dayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.STEPS }
        getIntegerListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0 }.toList()
    }

    getDayPhysicalActivityData().dayDistanceListFromDB = if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = dayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.DISTANCE }
        if (filter.isEmpty()) {
            MutableList(48) { 0.0 }.toList()
        } else {
            getDoubleListFromStringMap(filter[0].data)
        }
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    getDayPhysicalActivityData().dayCaloriesListFromDB = if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = dayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.CALORIES }
        if (filter.isEmpty()) {
            MutableList(48) { 0.0 }.toList()
        } else {
            getDoubleListFromStringMap(filter[0].data)
        }
    } else {
        MutableList(48) { 0.0 }.toList()
    }


    val stepsListFromDB = {
        getDayPhysicalActivityData().dayStepListFromDB
    }
    val distanceListFromDB = {
        getDayPhysicalActivityData().dayDistanceListFromDB
    }
    val caloriesListFromDB = {
        getDayPhysicalActivityData().dayCaloriesListFromDB
    }

    val getSelectedDay = {
        dataViewModel.selectedDay
    }

    //DialogDatePicker State
    val stateShowDialogDatePickerValue = {
        dataViewModel.stateShowDialogDatePicker
    }
    val stateShowDialogDatePickerSetter:(Boolean) -> Unit = { value ->
        dataViewModel.stateShowDialogDatePicker = value
    }

    val stateMiliSecondsDateDialogDatePicker = {
        dataViewModel.stateMiliSecondsDateDialogDatePicker
    }
    val stateMiliSecondsDateDialogDatePickerSetter:(Long) -> Unit = { value ->
        dataViewModel.stateMiliSecondsDateDialogDatePicker = value

        val date = Date(value)
        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateData = formattedDate.format(date)

        dataViewModel.getDayPhysicalActivityData(dateData,
            dataViewModel.macAddressDeviceBluetooth)

    }

    Log.d("PhysicalActivityInfo", "PhysicalActivityInfo: 2")

    PhysicalActivityLayoutScaffold(
        stepsListFromDB,
        distanceListFromDB,
        caloriesListFromDB,
        getSelectedDay,
        stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePicker,
        stateMiliSecondsDateDialogDatePickerSetter,
        navLambda
    )

}


