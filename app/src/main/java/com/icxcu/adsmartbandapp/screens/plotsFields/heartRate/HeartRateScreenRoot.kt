package com.icxcu.adsmartbandapp.screens.plotsFields.heartRate

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.screens.HeartRateNestedRoute
import com.icxcu.adsmartbandapp.screens.personaInfoScreen.ValidatorsPersonalField
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getDoubleListFromStringMap
import com.icxcu.adsmartbandapp.viewModels.HeartRateViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Locale

@Composable
fun HeartRateScreenRoot(
    heartRateViewModel: HeartRateViewModel,
    macAddressDeviceBluetooth: String,
    navMainController: NavController,
) {

    val navLambdaBackToMainNavigationBar = remember(navMainController) {
        {
            navMainController.popBackStack(HeartRateNestedRoute.HeartRateMainRoute().route, true)
            Unit
        }
    }

    val dayHeartRateResultsFromDB = heartRateViewModel.dayHeartRateState
    val dateFromDB = heartRateViewModel.personalInfoDataStateC

    val dateBirth = if (dateFromDB.isEmpty().not() && dateFromDB[0].id != -1) {
        val filter = dateFromDB.filter { it.typesTable == TypesTable.PERSONAL_INFO }
        filter[0].birthdate
    } else {
        ""
    }

    val ageCalculated by remember(heartRateViewModel) {
        derivedStateOf {
            val date = ValidatorsPersonalField.dateValidator(dateBirth)
            val age = try {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val myBirthsDate = LocalDate.parse(date, formatter)
                Log.d("VerifyingDate", "HeartRateInfo: Error $myBirthsDate")
                LocalDate.now().year - myBirthsDate.year
            } catch (e: DateTimeParseException) {
                Log.d("VerifyingDate", "HeartRateInfo: Error")
                0
            }
            age
        }
    }

    val getAgeCalculated = remember {
        { ageCalculated }
    }

    if (dayHeartRateResultsFromDB.isEmpty().not()) {
        heartRateViewModel.selectedDay = dayHeartRateResultsFromDB[0].dateData
    }

    heartRateViewModel.dayHeartRateListFromDB =
        if (dayHeartRateResultsFromDB.isEmpty().not()) {
            val filter = dayHeartRateResultsFromDB.filter { it.typesTable == TypesTable.HEART_RATE }
            getDoubleListFromStringMap(filter[0].data)
        } else {
            MutableList(48) { 0.0 }.toList()
        }

    val heartRateListFromDB = remember(heartRateViewModel) {
        { heartRateViewModel.dayHeartRateListFromDB }
    }

    val getSelectedDay = remember(heartRateViewModel) {
        { heartRateViewModel.selectedDay }
    }

    //DialogDatePicker State
    val stateShowDialogDatePickerValue = remember(heartRateViewModel) {
        {
            heartRateViewModel.stateShowDialogDatePicker
        }
    }

    val stateShowDialogDatePickerSetter = remember(heartRateViewModel) {
        { value: Boolean ->
            heartRateViewModel.stateShowDialogDatePicker = value
        }
    }

    val stateMiliSecondsDateDialogDatePickerSetter = remember(heartRateViewModel) {
        { value: Long ->
            heartRateViewModel.stateMiliSecondsDateDialogDatePicker = value

            val date = Date(value)
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateData = formattedDate.format(date)

            /*            dataViewModel.getDayHeartRateData(
                            dateData,
                            dataViewModel.macAddressDeviceBluetooth
                        )*/
            heartRateViewModel.jobHeartRateState?.cancel()
            heartRateViewModel.starListeningHeartRateDB(
                dateData,
                macAddress = macAddressDeviceBluetooth,
            )
            heartRateViewModel.selectedDay = dateData
        }
    }


    HeartRateLayoutScaffold(
        heartRateList = heartRateListFromDB,
        getSelectedDay = getSelectedDay,
        stateShowDialogDatePickerSetter = stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue = stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePickerSetter = stateMiliSecondsDateDialogDatePickerSetter,
        getAgeCalculated = getAgeCalculated,
        navLambda = navLambdaBackToMainNavigationBar
    )

}

sealed class HeartRateScreenNavStatus {
    object Started : HeartRateScreenNavStatus()
    object Leaving : HeartRateScreenNavStatus()
}


