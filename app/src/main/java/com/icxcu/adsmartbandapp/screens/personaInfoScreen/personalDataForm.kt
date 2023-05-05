package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PersonalDataForm() {
    var name by remember {
        mutableStateOf("")
    }
    val currentName = {
        name
    }

    val onTextChange = { valueFromTextField: String ->
        name = valueFromTextField
    }

    var nameTextFieldVisibility by remember {
        mutableStateOf(false)
    }

    val currentNameTextFieldVisibility = {
        nameTextFieldVisibility
    }

    val onNameTextFieldVisibilityChange = { visibility: Boolean ->
        nameTextFieldVisibility = visibility
    }


    var date by remember {
        mutableStateOf("")
    }
    val currentDate = {
        date
    }

    val onDateTextChange = { valueFromDateTextField: String ->
        date = valueFromDateTextField
    }

    var dateTextFieldVisibility by remember {
        mutableStateOf(false)
    }

    val currentDateTextFieldVisibility = {
        dateTextFieldVisibility
    }

    val onDateTextFieldVisibilityChange = { visibility: Boolean ->
        dateTextFieldVisibility = visibility
    }


    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            NameTextFieldComposable(
                currentName = currentName,
                currentNameTextFieldVisibility = currentNameTextFieldVisibility,
                onTextChange = onTextChange,
                onNameTextFieldVisibilityChange = onNameTextFieldVisibilityChange
            )

            DateTextFieldComposable(
                currentDate = currentDate,
                currentDateTextFieldVisibility = currentDateTextFieldVisibility,
                onDateTextChange = onDateTextChange,
                onDateTextFieldVisibilityChange = onDateTextFieldVisibilityChange
            )


        }
    }


}



@Preview(showBackground = true)
@Composable
fun PersonalDataFormPreview2() {
    PersonalDataForm()
}