package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun PersonalInfoDataScreenRoot(
    dataViewModel: DataViewModel,
    navLambda: () -> Unit
) {

    val getPersonalInfoDataState = remember(dataViewModel) {{dataViewModel.personalInfoDataState}}
    val getInvalidAlertDialogState = remember(dataViewModel) {{dataViewModel.invalidAlertDialogState}}
    val validatePersonalInfo = remember(dataViewModel, getPersonalInfoDataState) {{dataViewModel.validatePersonalInfo( getPersonalInfoDataState )}}
    val getUpdateAlertDialogVisibilityState = remember(dataViewModel) {{dataViewModel.updateAlertDialogPersonalFieldVisibilityState}}
    val getInsertAlertDialogVisibilityState = remember(dataViewModel) {{dataViewModel.insertAlertDialogPersonalFieldVisibilityState}}

    val getPersonalInfoListReadFromDB = remember(dataViewModel) {
        {
            dataViewModel.personalInfoDataStateC
        }
    }

    val dataFromDB = getPersonalInfoListReadFromDB()

    if (dataFromDB.isEmpty().not() && dataFromDB[0].id!=-1) {
        val filter = dataFromDB.filter { it.typesTable == TypesTable.PERSONAL_INFO }
        getPersonalInfoDataState().id = filter[0].id
        getPersonalInfoDataState().name = filter[0].name
        getPersonalInfoDataState().macAddress = filter[0].macAddress
        getPersonalInfoDataState().date = filter[0].birthdate
        getPersonalInfoDataState().weight = filter[0].weight.toString()
        getPersonalInfoDataState().height = filter[0].height.toString()
        Log.d("AVATAR", "dataFromDB: ${getPersonalInfoDataState().uri}")
    }

    val insertPersonalData= { personalInfo:PersonalInfo->
        dataViewModel.insertPersonalInfoDataWithCoroutine(personalInfo, dataViewModel.macAddressDeviceBluetooth)
    }

    val updatePersonalData= { personalInfo:PersonalInfo->
        dataViewModel.updatePersonalInfoDataWithCoroutine(personalInfo, dataViewModel.macAddressDeviceBluetooth)
    }

    PersonalInfoFormScaffold(
        navLambda,
        getPersonalInfoDataState,
        getPersonalInfoListReadFromDB,
        validatePersonalInfo,
        getInvalidAlertDialogState,
        getUpdateAlertDialogVisibilityState,
        getInsertAlertDialogVisibilityState,
        updatePersonalData,
        insertPersonalData
    )



}

class PersonalInfoDataState {
    var id by mutableStateOf(0)

    var uri by mutableStateOf("")

    var name by mutableStateOf("")
    var nameTextFieldVisibility by mutableStateOf(false)

    var macAddress by mutableStateOf("")

    var date by mutableStateOf("")
    var dateTextFieldVisibility by mutableStateOf(false)

    var weight by mutableStateOf("")
    var weightTextFieldVisibility by mutableStateOf(false)

    var height by mutableStateOf("")
    var heightTextFieldVisibility by mutableStateOf(false)

    val onTextChange = { valueFromTextField: String ->
        name = valueFromTextField
    }

    val onNameTextFieldVisibilityChange = { visibility: Boolean ->
        nameTextFieldVisibility = visibility
    }

    val onDateTextChange = { valueFromDateTextField: String ->
        date = valueFromDateTextField
    }

    val onDateTextFieldVisibilityChange = { visibility: Boolean ->
        dateTextFieldVisibility = visibility
    }

    val onWeightTextChange = { valueFromWeightTextField: String ->
        weight = valueFromWeightTextField
    }



    val onWeightTextFieldVisibilityChange = { visibility: Boolean ->
        weightTextFieldVisibility = visibility
    }



    val onHeightTextChange = { valueFromHeightTextField: String ->
        height = valueFromHeightTextField
    }


    val onHeightTextFieldVisibilityChange = { visibility: Boolean ->
        heightTextFieldVisibility = visibility
    }

}

sealed class PersonalInfoDataScreenNavStatus{
    object Started: PersonalInfoDataScreenNavStatus()
    object Leaving: PersonalInfoDataScreenNavStatus()
}


