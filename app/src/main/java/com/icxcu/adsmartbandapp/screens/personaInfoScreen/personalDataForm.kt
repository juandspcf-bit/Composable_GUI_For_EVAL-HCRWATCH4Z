package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun PersonalDataForm(
    dataViewModel: DataViewModel,
    navLambda: () -> Unit
) {

    val currentName = {
        dataViewModel.name
    }

    val onTextChange = { valueFromTextField: String ->
        dataViewModel.name = valueFromTextField
    }

    val currentNameTextFieldVisibility = {
        dataViewModel.nameTextFieldVisibility
    }

    val onNameTextFieldVisibilityChange = { visibility: Boolean ->
        dataViewModel.nameTextFieldVisibility = visibility
    }

    val currentDate = {
        dataViewModel.date
    }

    val onDateTextChange = { valueFromDateTextField: String ->
        dataViewModel.date = valueFromDateTextField
    }


    val currentDateTextFieldVisibility = {
        dataViewModel.dateTextFieldVisibility
    }

    val onDateTextFieldVisibilityChange = { visibility: Boolean ->
        dataViewModel.dateTextFieldVisibility = visibility
    }

    val currentWeight = {
        dataViewModel.weight
    }

    val onWeightTextChange = { valueFromWeightTextField: String ->
        dataViewModel.weight = valueFromWeightTextField
    }

    val currentWeightTextFieldVisibility = {
        dataViewModel.weightTextFieldVisibility
    }

    val onWeightTextFieldVisibilityChange = { visibility: Boolean ->
        dataViewModel.weightTextFieldVisibility = visibility
    }

    val currentHeight = {
        dataViewModel.height
    }

    val onHeightTextChange = { valueFromHeightTextField: String ->
        dataViewModel.height = valueFromHeightTextField
    }

    val currentHeightTextFieldVisibility = {
        dataViewModel.heightTextFieldVisibility
    }

    val onHeightTextFieldVisibilityChange = { visibility: Boolean ->
        dataViewModel.heightTextFieldVisibility = visibility
    }

    val getPersonalInfoListReadFromDB = {
        dataViewModel.personalInfoListReadFromDB
    }

    val isValidPersonalInfo={
        dataViewModel.isValidPersonalInfo( currentName,currentDate, currentWeight, currentHeight)
    }

    val insertPersonalData= { personalInfo:PersonalInfo->
        personalInfo.macAddress = dataViewModel.macAddressDeviceBluetooth
        dataViewModel.insertPersonalData(personalInfo)
    }

    val updatePersonalData= { personalInfo:PersonalInfo->
        dataViewModel.updatePersonalData(personalInfo)
    }

    PersonalInfoFormScaffold(
        navLambda,
        currentName,
        currentNameTextFieldVisibility,
        onTextChange,
        onNameTextFieldVisibilityChange,
        currentDate,
        currentDateTextFieldVisibility,
        onDateTextChange,
        onDateTextFieldVisibilityChange,
        currentWeight,
        currentWeightTextFieldVisibility,
        onWeightTextChange,
        onWeightTextFieldVisibilityChange,
        currentHeight,
        currentHeightTextFieldVisibility,
        onHeightTextChange,
        onHeightTextFieldVisibilityChange,
        getPersonalInfoListReadFromDB,
        isValidPersonalInfo,
        updatePersonalData,
        insertPersonalData
    )

}

@Preview(showBackground = true)
@Composable
fun PersonalDataFormPreview2() {

    //PersonalDataForm()
}