package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.BloodPressure
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BloodPressureInfo(
    dataViewModel: DataViewModel,
    navLambda: () -> Unit
) {

    val getDayPhysicalActivityData = remember(dataViewModel) {
        {
            dataViewModel.dayPhysicalActivityInfoState
        }
    }

    val dayBloodPressureResultsFromDB by getDayPhysicalActivityData().dayBloodPressureResultsFromDB.observeAsState(
        MutableList(0) { BloodPressure() }.toList()
    )

    if (dayBloodPressureResultsFromDB.isEmpty().not()) {
        Log.d("date", "BloodPressureInfo: $dayBloodPressureResultsFromDB")
        dataViewModel.selectedDay = dayBloodPressureResultsFromDB[0].dateData
    }

    getDayPhysicalActivityData().daySystolicListFromDB = if (dayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = dayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.SYSTOLIC }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    getDayPhysicalActivityData().dayDiastolicListFromDB = if (dayBloodPressureResultsFromDB.isEmpty().not()) {
        val filter = dayBloodPressureResultsFromDB.filter { it.typesTable == TypesTable.DIASTOLIC }
        if (filter.isEmpty()) {
            MutableList(48) { 0.0 }.toList()
        } else {
            getDoubleListFromStringMap(filter[0].data)
        }
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val systolicListFromDB = {
        getDayPhysicalActivityData().daySystolicListFromDB
    }
    val diastolicListFromDB = {
        getDayPhysicalActivityData().dayDiastolicListFromDB
    }

    val getSelectedDay = {
        dataViewModel.selectedDay
    }

    //DialogDatePicker State
    val stateShowDialogDatePickerValue = {
        dataViewModel.stateShowDialogDatePicker
    }
    val stateShowDialogDatePickerSetter: (Boolean) -> Unit = { value ->
        dataViewModel.stateShowDialogDatePicker = value
    }

    val stateMiliSecondsDateDialogDatePicker = {
        dataViewModel.stateMiliSecondsDateDialogDatePicker
    }
    val stateMiliSecondsDateDialogDatePickerSetter: (Long) -> Unit = { value ->
        dataViewModel.stateMiliSecondsDateDialogDatePicker = value

        val date = Date(value)
        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateData = formattedDate.format(date)

        Log.d("date", "BloodPressureInfo: $dateData")
        dataViewModel.getDayBloodPressureData(
            dateData,
            dataViewModel.macAddressDeviceBluetooth
        )

    }

    BloodPressureLayoutScaffold(
        systolicList = systolicListFromDB,
        diastolicList = diastolicListFromDB,
        getSelectedDay = getSelectedDay,
        stateShowDialogDatePickerSetter = stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue = stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePickerS = stateMiliSecondsDateDialogDatePicker,
        stateMiliSecondsDateDialogDatePickerSetterS = stateMiliSecondsDateDialogDatePickerSetter,
        navLambda = navLambda
    )

}