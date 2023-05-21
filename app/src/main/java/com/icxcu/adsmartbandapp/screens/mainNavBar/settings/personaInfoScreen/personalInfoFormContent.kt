package com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.data.TypesTable
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo

@Composable
fun PersonalInfoContent(
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
    getPersonalInfoListReadFromDB: () -> List<PersonalInfo>,
    validatePersonalInfo: () -> List<String> = { listOf() },
    visibilityAlertDialogStatusPersonalInfo: () -> Boolean,
    setVisibilityAlertDialogStatusPersonalInfo: (Boolean) -> Unit,
    getInvalidFields: () -> List<String>,
    setInvalidFields: (List<String>) -> Unit,
    visibilityAlertDialogStatusPersonalInfoU: () -> Boolean,
    setVisibilityAlertDialogStatusPersonalInfoU: (Boolean) -> Unit,
    updatePersonalData: (PersonalInfo) -> Unit = {},
    insertPersonalData: (PersonalInfo) -> Unit = {},
) {

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff1d2a35)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.verticalScroll(scrollState).padding(start = 10.dp, bottom = 10.dp),
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
                validator = ValidatorsPersonalField.weightValidator
            )

            NumericUnitTextFieldComposable(
                currentNumericUnit = currentHeight,
                currentNumericUnitTextFieldVisibility = currentHeightTextFieldVisibility,
                onNumericUnitTextChange = onHeightTextChange,
                onNumericUnitTextFieldVisibilityChange = onHeightTextFieldVisibilityChange,
                unit = "m",
                contentDescription = "height",
                resourceIcon1 = R.drawable.baseline_boy_24,
                validator = ValidatorsPersonalField.heightValidator
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 50.dp, end = 20.dp),
                onClick = {

                    if (validatePersonalInfo().isNotEmpty()) {
                        setVisibilityAlertDialogStatusPersonalInfo(true)
                        setInvalidFields(validatePersonalInfo())
                        return@Button
                    }

                    val personalInfoListReadFromDB = getPersonalInfoListReadFromDB()
                    if (personalInfoListReadFromDB.isNotEmpty() && personalInfoListReadFromDB[0].id != -1) {
                        personalInfoListReadFromDB[0].name = currentName()
                        personalInfoListReadFromDB[0].birthdate = currentDate()
                        personalInfoListReadFromDB[0].weight = currentWeight().toDouble()
                        personalInfoListReadFromDB[0].height = currentHeight().toDouble()
                        Log.d("Flow", "PersonalInfoFormScaffold: updating")
                        updatePersonalData(personalInfoListReadFromDB[0])
                    } else {
                        val personalInfo = PersonalInfo()
                        personalInfo.name = currentName()
                        personalInfo.birthdate = currentDate()
                        personalInfo.weight = currentWeight().toDouble()
                        personalInfo.height = currentHeight().toDouble()
                        Log.d("Flow", "PersonalInfoFormScaffold: inserting")
                        insertPersonalData(personalInfo)
                    }


                }
            ) {
                Text(text = "Save info", color = Color.White, textAlign = TextAlign.Center)

            }

            if (visibilityAlertDialogStatusPersonalInfo()) {
                ValidationAlertDialog(
                    setVisibilityAlertDialogStatusPersonalInfo,
                    getInvalidFields,
                    setInvalidFields
                )
            }

            if (visibilityAlertDialogStatusPersonalInfoU()) {
                UpdateAlertDialog(setVisibilityAlertDialogStatusPersonalInfoU)
            }

        }
    }

}