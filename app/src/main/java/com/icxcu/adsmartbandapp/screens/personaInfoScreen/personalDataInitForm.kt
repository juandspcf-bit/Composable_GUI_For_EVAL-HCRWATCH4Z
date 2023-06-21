package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.screens.BluetoothScannerNestedRoute
import com.icxcu.adsmartbandapp.screens.PersonalInfoNestedRoute
import com.icxcu.adsmartbandapp.viewModels.PersonalInfoViewModel

@Composable
fun PersonalInfoDataInitScreenRoot(
    personalInfoViewModel: PersonalInfoViewModel,
    navMainController: NavHostController
) {

    val navLambdaBackToMainNavigationBarFromPersonalInfo = remember(navMainController) {
        {
            navMainController.navigate(BluetoothScannerNestedRoute.BluetoothScannerScreen().route){
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
    }

    val getPersonalInfoDataState = remember(personalInfoViewModel) {{personalInfoViewModel.personalInfoDataState}}
    val getInvalidAlertDialogState = remember(personalInfoViewModel) {{personalInfoViewModel.invalidAlertDialogState}}
    val validatePersonalInfo = remember(personalInfoViewModel, getPersonalInfoDataState) {{personalInfoViewModel.validatePersonalInfo( getPersonalInfoDataState )}}
    val getUpdateAlertDialogVisibilityState = remember(personalInfoViewModel) {{personalInfoViewModel.updateAlertDialogPersonalFieldVisibilityState}}
    val getInsertAlertDialogVisibilityState = remember(personalInfoViewModel) {{personalInfoViewModel.insertAlertDialogPersonalFieldVisibilityState}}

    val getPersonalInfoListReadFromDB = remember(personalInfoViewModel) {
        {
            personalInfoViewModel.personalInfoDataStateC
        }
    }

    val dataFromDB = getPersonalInfoListReadFromDB()

    if (dataFromDB.isEmpty().not() && dataFromDB[0].id!=-1) {
        val filter = dataFromDB.filter { it.typesTable == TypesTable.PERSONAL_INFO }
        getPersonalInfoDataState().id = filter[0].id
        getPersonalInfoDataState().name = filter[0].name
        getPersonalInfoDataState().date = filter[0].birthdate
        getPersonalInfoDataState().weight = filter[0].weight.toString()
        getPersonalInfoDataState().height = filter[0].height.toString()
        Log.d("AVATAR", "dataFromDB: ${getPersonalInfoDataState().name}")
    }

    val insertPersonalData= remember(personalInfoViewModel){
        { personalInfo: PersonalInfo ->
            personalInfoViewModel.insertPersonalInfoDataWithCoroutine(
                personalInfo,
            )
        }
    }

    val updatePersonalData= remember(personalInfoViewModel){
        { personalInfo: PersonalInfo ->
            personalInfoViewModel.updatePersonalInfoDataWithCoroutine(
                personalInfo,
            )
        }
    }

    PersonalInfoInitFormScaffold(
        navLambdaBackToMainNavigationBarFromPersonalInfo,
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



