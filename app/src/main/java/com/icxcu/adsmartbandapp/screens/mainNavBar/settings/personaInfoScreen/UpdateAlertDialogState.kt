package com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData

class UpdateAlertDialogState {
    var personalInfoAlertDialogUVLiveData = MutableLiveData(false)
    var alertDialogUPersonalFieldVisibility by mutableStateOf(false)

    val setVisibilityAlertDialogStatusPersonalInfoU = { value:Boolean ->
        alertDialogUPersonalFieldVisibility = value
        personalInfoAlertDialogUVLiveData.value = value

    }

}