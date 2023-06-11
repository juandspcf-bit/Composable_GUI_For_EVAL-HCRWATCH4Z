package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun PersonalInfoInitDBHandlerAD(
    dataViewModel: DataViewModel,
) {

    val getUpdateAlertDialogState = {
        dataViewModel.updateAlertDialogState
    }

    //Data Sources
    val personalInfoAlertDialogUStateDB by
    getUpdateAlertDialogState().personalInfoAlertDialogUVLiveData
        .observeAsState(initial = false)


    getUpdateAlertDialogState().alertDialogUPersonalFieldVisibility = personalInfoAlertDialogUStateDB?:false
    Log.d("Steps", "updatePersonalInfo: Second")


}