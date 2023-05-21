package com.icxcu.adsmartbandapp.screens.plotsFields.heartRate

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.HeartRate
import com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen.ValidatorsPersonalField
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
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

    val ageCalculated by remember(dayHeartRateResultsFromDB) {
        derivedStateOf {
            val date = ValidatorsPersonalField.dateValidator(dataViewModel.date)
            Log.d("VerifyingDate", "HeartRateInfo: date ${dataViewModel.date}")
            val age = try {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val myBirthsDate = LocalDate.parse(date, formatter)
                Log.d("VerifyingDate", "HeartRateInfo: Error $myBirthsDate")
                LocalDate.now().year -myBirthsDate.year
            }catch (e: DateTimeParseException){
                Log.d("VerifyingDate", "HeartRateInfo: Error")
                0
            }
        age
        }
    }

    val getAgeCalculated = {
        ageCalculated
    }

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


        dataViewModel.getDayHeartRateData(dateData,
            dataViewModel.macAddressDeviceBluetooth)

    }

    HeartRateLayoutScaffold(
        heartRateList = heartRateListFromDB,
        getSelectedDay = getSelectedDay,
        stateShowDialogDatePickerSetter = stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue = stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePickerS = stateMiliSecondsDateDialogDatePicker,
        stateMiliSecondsDateDialogDatePickerSetterS= stateMiliSecondsDateDialogDatePickerSetter,
        getAgeCalculated = getAgeCalculated,
        navLambda= navLambda
    )

}


