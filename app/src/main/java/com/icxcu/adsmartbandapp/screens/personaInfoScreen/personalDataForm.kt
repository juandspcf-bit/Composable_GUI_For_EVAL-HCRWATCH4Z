package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun PersonalDataFormScreenRoot(
    dataViewModel: DataViewModel,
    navLambda: () -> Unit
) {


    //Data Sources




    val getPersonalInfoDataState = remember(dataViewModel) {{dataViewModel.personalInfoDataState}}
    val getInvalidAlertDialogState = remember(dataViewModel) {{dataViewModel.invalidAlertDialogState}}
    val validatePersonalInfo = remember(dataViewModel, getPersonalInfoDataState) {{dataViewModel.validatePersonalInfo( getPersonalInfoDataState )}}
    val getUpdateAlertDialogState = remember(dataViewModel) {{dataViewModel.updateAlertDialogState}}
    val getPersonalInfoListReadFromDB = remember(dataViewModel) {{dataViewModel.personalInfoListReadFromDB}}

    val personalInfoAlertDialogUStateDB by
    getUpdateAlertDialogState().personalInfoAlertDialogUVLiveData
        .observeAsState(initial = false)

    Log.d("State", "PersonalDataForm: $personalInfoAlertDialogUStateDB")

    getUpdateAlertDialogState().alertDialogUPersonalFieldVisibility = personalInfoAlertDialogUStateDB?:false

    val insertPersonalData= { personalInfo:PersonalInfo->
        personalInfo.macAddress = dataViewModel.macAddressDeviceBluetooth
        dataViewModel.insertPersonalData(personalInfo)
    }

    val updatePersonalData= { personalInfo:PersonalInfo->
        dataViewModel.updatePersonalData(personalInfo)
    }

    PersonalInfoFormScaffold(
        navLambda,
        getPersonalInfoDataState,
        getPersonalInfoListReadFromDB,
        validatePersonalInfo,
        getInvalidAlertDialogState,
        getUpdateAlertDialogState,
        updatePersonalData,
        insertPersonalData
    )



}

class PersonalInfoDataState {
    var name by mutableStateOf("")
    var nameTextFieldVisibility by mutableStateOf(false)
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




@Preview(showBackground = true)
@Composable
fun PersonalDataFormPreview2() {

    //PersonalDataForm()
}