package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.screens.Routes
import com.icxcu.adsmartbandapp.screens.plotsFields.DatePickerDialogSample
import com.icxcu.adsmartbandapp.screens.plotsFields.heartRate.HeartRateInfoContent
import com.icxcu.adsmartbandapp.viewModels.DataViewModel

@Composable
fun PersonalDataForm(
    dataViewModel: DataViewModel,
    navLambda: () -> Unit
) {

    val currentName = {
        dataViewModel.name
    }

    val onTextChange = { valueFromTextField: String ->
        dataViewModel.name = valueFromTextField
    }

    val currentNameTextFieldVisibility = {
        dataViewModel.nameTextFieldVisibility
    }

    val onNameTextFieldVisibilityChange = { visibility: Boolean ->
        dataViewModel.nameTextFieldVisibility = visibility
    }

    val currentDate = {
        dataViewModel.date
    }

    val onDateTextChange = { valueFromDateTextField: String ->
        dataViewModel.date = valueFromDateTextField
    }


    val currentDateTextFieldVisibility = {
        dataViewModel.dateTextFieldVisibility
    }

    val onDateTextFieldVisibilityChange = { visibility: Boolean ->
        dataViewModel.dateTextFieldVisibility = visibility
    }

    val currentWeight = {
        dataViewModel.weight
    }

    val onWeightTextChange = { valueFromWeightTextField: String ->
        dataViewModel.weight = valueFromWeightTextField
    }

    val currentWeightTextFieldVisibility = {
        dataViewModel.weightTextFieldVisibility
    }

    val onWeightTextFieldVisibilityChange = { visibility: Boolean ->
        dataViewModel.weightTextFieldVisibility = visibility
    }

    val currentHeight = {
        dataViewModel.height
    }

    val onHeightTextChange = { valueFromHeightTextField: String ->
        dataViewModel.height = valueFromHeightTextField
    }

    val currentHeightTextFieldVisibility = {
        dataViewModel.heightTextFieldVisibility
    }

    val onHeightTextFieldVisibilityChange = { visibility: Boolean ->
        dataViewModel.heightTextFieldVisibility = visibility
    }

    val storePersonalData = {
        dataViewModel.storePersonalData()
    }

    PersonalInfoFormScaffold(
        navLambda,
        currentName,
        currentNameTextFieldVisibility,
        onTextChange,
        onNameTextFieldVisibilityChange,
        currentDate,
        currentDateTextFieldVisibility,
        onDateTextChange,
        onDateTextFieldVisibilityChange,
        currentWeight,
        currentWeightTextFieldVisibility,
        onWeightTextChange,
        onWeightTextFieldVisibilityChange,
        currentHeight,
        currentHeightTextFieldVisibility,
        onHeightTextChange,
        onHeightTextFieldVisibilityChange,
        storePersonalData
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoFormScaffold(
    navLambda: () -> Unit,
    currentName: () -> String,
    currentNameTextFieldVisibility: () -> Boolean,
    onTextChange: (String) -> Unit,
    onNameTextFieldVisibilityChange: (Boolean) -> Unit,
    currentDate: () -> String,
    currentDateTextFieldVisibility: () -> Boolean,
    onDateTextChange: (String) -> Unit,
    onDateTextFieldVisibilityChange: (Boolean) -> Unit,
    currentWeight: () -> String,
    currentWeightTextFieldVisibility: () -> Boolean,
    onWeightTextChange: (String) -> Unit,
    onWeightTextFieldVisibilityChange: (Boolean) -> Unit,
    currentHeight: () -> String,
    currentHeightTextFieldVisibility: () -> Boolean,
    onHeightTextChange: (String) -> Unit,
    onHeightTextFieldVisibilityChange: (Boolean) -> Unit,
    storePersonalData:()->Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = "Personal information",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navLambda()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xff0d1721),
                ),
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior = scrollBehavior,
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff1d2a35)),
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
                        resourceIcon1 = R.drawable.baseline_point_of_sale_24,
                        validator = weightValidator
                    )

                    NumericUnitTextFieldComposable(
                        currentNumericUnit = currentHeight,
                        currentNumericUnitTextFieldVisibility = currentHeightTextFieldVisibility,
                        onNumericUnitTextChange = onHeightTextChange,
                        onNumericUnitTextFieldVisibilityChange = onHeightTextFieldVisibilityChange,
                        unit = "m",
                        contentDescription = "height",
                        resourceIcon1 = R.drawable.baseline_boy_24,
                        validator = heightValidator
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(0.8f)
                            .padding(top=50.dp, end = 20.dp),
                        onClick = {
                        storePersonalData()
                    }) {
                        Text("Save data")
                    }

                }
            }

        },
    )
}


val weightValidator = { number: String ->
    val numberD: Double
    var numberS: String = number
    try {
        numberD = number.toDouble()
        if (numberD < 0) {
            numberS = ""
        }
    } catch (e: NumberFormatException) {
        numberS = ""
    }
    numberS
}

val heightValidator = { number: String ->
    val numberD: Double
    var numberS: String = number
    try {
        numberD = number.toDouble()
        if (numberD < 0) {
            numberS = ""
        }
    } catch (e: NumberFormatException) {
        numberS = ""
    }
    numberS
}


@Preview(showBackground = true)
@Composable
fun PersonalDataFormPreview2() {

    //PersonalDataForm()
}