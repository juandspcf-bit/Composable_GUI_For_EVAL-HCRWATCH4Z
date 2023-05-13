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

    //Data Sources
    val personalInfoAlertDialogUStateDB by dataViewModel
        .personalInfoAlertDialogUVLiveData
        .observeAsState(initial = false)


    dataViewModel.alertDialogUPersonalFieldVisibility = personalInfoAlertDialogUStateDB?:false
    Log.d("Steps", "updatePersonalInfo: Second")


}