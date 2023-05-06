package com.icxcu.adsmartbandapp.screens.plotsFields.heartRate

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HeartRateInfo(
    dataViewModel: DataViewModel,
    navLambda: () -> Unit
){

    val dayHeartRateResultsFromDB by dataViewModel.dayHeartRateResultsFromDB.observeAsState(
        MutableList(0) { HeartRate() }.toList()
    )

    if(dayHeartRateResultsFromDB.isEmpty().not()){
       dataViewModel.selectedDay = dayHeartRateResultsFromDB[0].dateData
    }

    dataViewModel.dayHeartRateListFromDB = if (dayHeartRateResultsFromDB.isEmpty().not()) {
        val filter = dayHeartRateResultsFromDB.filter { it.typesTable == TypesTable.HEART_RATE }
        getDoubleListFromStringMap(filter[0].data)
    } else {
        MutableList(48) { 0.0 }.toList()
    }

    val heartRateListFromDB = {
        dataViewModel.dayHeartRateListFromDB
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

        Log.d("date", "BloodPressureInfo: $dateData")
        dataViewModel.getDayHeartRateData(dateData,
            dataViewModel.macAddressDeviceBluetooth)

    }

    HeartRateLayoutScaffold(
        heartRateList = heartRateListFromDB,
        selectedDay = dataViewModel.selectedDay,
        stateShowDialogDatePickerSetter = stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue = stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePickerS = stateMiliSecondsDateDialogDatePicker,
        stateMiliSecondsDateDialogDatePickerSetterS= stateMiliSecondsDateDialogDatePickerSetter,
        navLambda= navLambda
    )

}


