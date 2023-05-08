package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import com.icxcu.adsmartbandapp.viewModels.DataViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun PersonalDataForm(
    dataViewModel: DataViewModel,
    navLambda: () -> Unit
) {

    PersonalInfoDBHandler(
        dataViewModel
    )

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

    val getPersonalInfoListReadFromDB = {
        dataViewModel.personalInfoListReadFromDB
    }

    val isValidPersonalInfo={
        dataViewModel.isValidPersonalInfo( currentName,currentDate, currentWeight, currentHeight)
    }

    val insertPersonalData= { personalInfo:PersonalInfo->
        personalInfo.macAddress = dataViewModel.macAddressDeviceBluetooth
        dataViewModel.insertPersonalData(personalInfo)
    }

    val updatePersonalData= { personalInfo:PersonalInfo->
        dataViewModel.updatePersonalData(personalInfo)
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
        getPersonalInfoListReadFromDB,
        isValidPersonalInfo,
        updatePersonalData,
        insertPersonalData
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
    getPersonalInfoListReadFromDB:()->List<PersonalInfo>,
    isValidPersonalInfo:()->Boolean={false},
    updatePersonalData:(PersonalInfo)->Unit ={},
    insertPersonalData:(PersonalInfo)->Unit ={},
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
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(top = 50.dp, end = 20.dp),
                        onClick = {
                            if(isValidPersonalInfo().not()){
                                Log.d("Flow", "PersonalInfoFormScaffold: no valid")
                                return@Button
                            }

                            val personalInfoListReadFromDB = getPersonalInfoListReadFromDB()
                            if (personalInfoListReadFromDB.isNotEmpty() && personalInfoListReadFromDB[0].id!=-1){
                                personalInfoListReadFromDB[0].name = currentName()
                                personalInfoListReadFromDB[0].birthdate = dateValidator(currentDate())
                                personalInfoListReadFromDB[0].weight = currentWeight().toDouble()
                                personalInfoListReadFromDB[0].height = currentHeight().toDouble()
                                Log.d("Flow", "PersonalInfoFormScaffold: updating")
                                updatePersonalData(personalInfoListReadFromDB[0])
                            }else {
                                val personalInfo = PersonalInfo()
                                personalInfo.name = currentName()
                                personalInfo.birthdate = currentDate()
                                personalInfo.weight = currentWeight().toDouble()
                                personalInfo.height = currentHeight().toDouble()
                                Log.d("Flow", "PersonalInfoFormScaffold: inserting")
                                insertPersonalData(personalInfo)
                            }
                    }) {

                        Text(text = "Save info", color = Color.White, textAlign = TextAlign.Center, )
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

val dateValidator = { currentDate: String ->
    val displayDate = if (currentDate == "") {
        ""
    } else {
        val trimmed = if (currentDate.length >= 8) currentDate.substring(0..7) else currentDate
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i % 2 == 1 && i < 4) out += "/"
        }

        try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            LocalDate.parse(out, formatter)
        }catch (e: DateTimeParseException){
            out = "Enter a valid date"
        }
        out
    }
    displayDate

}


@Preview(showBackground = true)
@Composable
fun PersonalDataFormPreview2() {

    //PersonalDataForm()
}