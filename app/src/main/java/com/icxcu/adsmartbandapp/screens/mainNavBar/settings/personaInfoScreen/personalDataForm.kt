package com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun PersonalDataForm(
    dataViewModel: DataViewModel,
    navLambda: () -> Unit
) {

    val getPersonalInfoDataState = {
        dataViewModel.personalInfoDataState
    }

    val getInvalidAlertDialogState = {
        dataViewModel.invalidAlertDialogState
    }

    val validatePersonalInfo={
        dataViewModel.validatePersonalInfo( getPersonalInfoDataState )
    }

    val getUpdateAlertDialogState = {
        dataViewModel.updateAlertDialogState
    }

    val getPersonalInfoListReadFromDB = {
        dataViewModel.personalInfoListReadFromDB
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
        getPersonalInfoDataState,
        getPersonalInfoListReadFromDB,
        validatePersonalInfo,
        getInvalidAlertDialogState,
        getUpdateAlertDialogState,
        updatePersonalData,
        insertPersonalData
    )

}

@Preview(showBackground = true)
@Composable
fun PersonalDataFormPreview2() {

    //PersonalDataForm()
}