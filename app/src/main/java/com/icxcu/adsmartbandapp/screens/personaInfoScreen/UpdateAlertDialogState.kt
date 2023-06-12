package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData

class UpdateAlertDialogState {
    var personalInfoAlertDialogUVLiveData = MutableLiveData(false)
    var updatePersonalInfoDataWithCoroutineAD by mutableStateOf(false)
    var alertDialogUPersonalFieldVisibility by mutableStateOf(false)

    val setVisibilityAlertDialogStatusPersonalInfoU = { value:Boolean ->
        alertDialogUPersonalFieldVisibility = value
        personalInfoAlertDialogUVLiveData.value = value

    }

}