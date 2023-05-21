package com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PersonalInfoDataState {
    var name by mutableStateOf("")
    var nameTextFieldVisibility by mutableStateOf(false)
    var date by mutableStateOf("")
    var dateTextFieldVisibility by mutableStateOf(false)
    var weight by mutableStateOf("")
    var weightTextFieldVisibility by mutableStateOf(false)
    var height by mutableStateOf("")
    var heightTextFieldVisibility by mutableStateOf(false)

    val onTextChange = { valueFromTextField: String ->
        name = valueFromTextField
    }

    val onNameTextFieldVisibilityChange = { visibility: Boolean ->
        nameTextFieldVisibility = visibility
    }

    val onDateTextChange = { valueFromDateTextField: String ->
        date = valueFromDateTextField
    }

    val onDateTextFieldVisibilityChange = { visibility: Boolean ->
        dateTextFieldVisibility = visibility
    }

    val onWeightTextChange = { valueFromWeightTextField: String ->
       weight = valueFromWeightTextField
    }



    val onWeightTextFieldVisibilityChange = { visibility: Boolean ->
        weightTextFieldVisibility = visibility
    }



    val onHeightTextChange = { valueFromHeightTextField: String ->
        height = valueFromHeightTextField
    }


    val onHeightTextFieldVisibilityChange = { visibility: Boolean ->
        heightTextFieldVisibility = visibility
    }



}