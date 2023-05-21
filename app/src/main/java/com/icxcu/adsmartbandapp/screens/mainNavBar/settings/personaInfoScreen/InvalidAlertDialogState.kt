package com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class InvalidAlertDialogState {
    var alertDialogPersonalFieldVisibility by mutableStateOf(false)
    var invalidFields by mutableStateOf(listOf<String>())
}