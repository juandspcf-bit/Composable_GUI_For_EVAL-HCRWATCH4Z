package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class UpdateAlertDialogPersonalFieldVisibilityState {
    var updateAlertDialogPersonalFieldVisibility by mutableStateOf(false)

    val setUpdateAlertDialogPersonalFieldVisibility = { value:Boolean ->
        updateAlertDialogPersonalFieldVisibility = value
    }

}

class InsertAlertDialogPersonalFieldVisibilityState {
    var insertAlertDialogPersonalFieldVisibility by mutableStateOf(false)

    val setInsertAlertDialogPersonalFieldVisibility = { value:Boolean ->
        insertAlertDialogPersonalFieldVisibility = value
    }

}