package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.R

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


    var weight by remember {
        mutableStateOf("")
    }
    val currentWeight = {
        weight
    }

    val onWeightTextChange = { valueFromWeightTextField: String ->
        weight = valueFromWeightTextField
    }

    var weightTextFieldVisibility by remember {
        mutableStateOf(false)
    }

    val currentWeightTextFieldVisibility = {
        weightTextFieldVisibility
    }

    val onWeightTextFieldVisibilityChange = { visibility: Boolean ->
        weightTextFieldVisibility = visibility
    }



    var height by remember {
        mutableStateOf("")
    }
    val currentHeight = {
        height
    }

    val onHeightTextChange = { valueFromHeightTextField: String ->
        height = valueFromHeightTextField
    }

    var heightTextFieldVisibility by remember {
        mutableStateOf(false)
    }

    val currentHeightTextFieldVisibility = {
        heightTextFieldVisibility
    }

    val onHeightTextFieldVisibilityChange = { visibility: Boolean ->
        heightTextFieldVisibility = visibility
    }




    Box(
        modifier = Modifier
            .fillMaxSize().background(Color(0xff1d2a35)),
            //,
        contentAlignment = Alignment.Center
    ) {
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

            NumericUnitTextFieldComposable(
                currentNumericUnit = currentWeight,
                currentNumericUnitTextFieldVisibility = currentWeightTextFieldVisibility,
                onNumericUnitTextChange = onWeightTextChange,
                onNumericUnitTextFieldVisibilityChange = onWeightTextFieldVisibilityChange,
                unit = "Kg",
                contentDescription = "weight",
                resourceIcon1 = R.drawable.baseline_point_of_sale_24
            )

            NumericUnitTextFieldComposable(
                currentNumericUnit = currentHeight,
                currentNumericUnitTextFieldVisibility = currentHeightTextFieldVisibility,
                onNumericUnitTextChange = onHeightTextChange,
                onNumericUnitTextFieldVisibilityChange = onHeightTextFieldVisibilityChange,
                unit = "m",
                contentDescription = "height",
                resourceIcon1 = R.drawable.baseline_boy_24

            )

        }
    }


}


@Preview(showBackground = true)
@Composable
fun PersonalDataFormPreview2() {
    PersonalDataForm()
}