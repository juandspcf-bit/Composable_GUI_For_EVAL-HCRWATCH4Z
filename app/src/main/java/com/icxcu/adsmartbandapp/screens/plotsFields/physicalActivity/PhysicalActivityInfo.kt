package com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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

    //Data state
    val dayPhysicalActivityResultsFromDB by dataViewModel.dayPhysicalActivityResultsFromDB.observeAsState(
        MutableList(0) { PhysicalActivity() }.toList()
    )

    if(dayPhysicalActivityResultsFromDB.isEmpty().not()){
        dataViewModel.selectedDay = dayPhysicalActivityResultsFromDB[0].dateData
    }

    dataViewModel.dayStepListFromDB = if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = dayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.STEPS }
        getIntegerListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0 }.toList()
    }

    dataViewModel.dayDistanceListFromDB = if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
        val filter = dayPhysicalActivityResultsFromDB.filter { it.typesTable == TypesTable.DISTANCE }
        if (filter.isEmpty()) {
            MutableList(48) { 0.0 }.toList()
        } else {
            getDoubleListFromStringMap(filter[0].data)
        }
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    dataViewModel.dayCaloriesListFromDB = if (dayPhysicalActivityResultsFromDB.isEmpty().not()) {
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
        dataViewModel.dayStepListFromDB
    }
    val distanceListFromDB = {
        dataViewModel.dayDistanceListFromDB
    }
    val caloriesListFromDB = {
        dataViewModel.dayCaloriesListFromDB
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
            dataViewModel.macAddress)

    }

    PhysicalActivityLayoutScaffold(
        stepsListFromDB,
        distanceListFromDB,
        caloriesListFromDB,
        dataViewModel.selectedDay,
        stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePicker,
        stateMiliSecondsDateDialogDatePickerSetter,
        navLambda
    )

}






