package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.screens.BluetoothScannerNestedRoute
import com.icxcu.adsmartbandapp.viewModels.PersonalInfoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PersonalInfoDataInitScreenRoot(
    personalInfoViewModel: PersonalInfoViewModel,
    navMainController: NavHostController
) {

    val scope = rememberCoroutineScope()

    val navLambdaBackToMainNavigationBarFromPersonalInfo = remember(navMainController) {
        {
            scope.launch {
                delay(500)
                navMainController.navigate(BluetoothScannerNestedRoute.BluetoothScannerScreen().route){
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
            Unit

        }
    }

    val getPersonalInfoDataState = remember(personalInfoViewModel) {{personalInfoViewModel.personalInfoDataState}}
    val getInvalidAlertDialogState = remember(personalInfoViewModel) {{personalInfoViewModel.invalidAlertDialogState}}
    val validatePersonalInfo = remember(personalInfoViewModel, getPersonalInfoDataState) {{personalInfoViewModel.validatePersonalInfo( getPersonalInfoDataState )}}
    val getUpdateAlertDialogVisibilityState = remember(personalInfoViewModel) {{personalInfoViewModel.updateAlertDialogPersonalFieldVisibilityState}}
    val getInsertAlertDialogVisibilityState = remember(personalInfoViewModel) {{personalInfoViewModel.insertAlertDialogPersonalFieldVisibilityState}}


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
        validatePersonalInfo,
        getInvalidAlertDialogState,
        getUpdateAlertDialogVisibilityState,
        getInsertAlertDialogVisibilityState,
        updatePersonalData,
        insertPersonalData
    )
}



