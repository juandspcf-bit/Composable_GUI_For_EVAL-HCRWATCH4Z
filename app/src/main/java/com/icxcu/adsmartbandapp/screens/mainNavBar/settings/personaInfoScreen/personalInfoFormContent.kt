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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo

@Composable
fun PersonalInfoContent(
    getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    getPersonalInfoListReadFromDB: () -> List<PersonalInfo>,
    validatePersonalInfo: () -> List<String> = { listOf() },
    getInvalidAlertDialogState: () -> InvalidAlertDialogState,
    getUpdateAlertDialogState: () -> UpdateAlertDialogState,
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
            modifier = Modifier.verticalScroll(scrollState).padding(start = 15.dp, end = 15.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            NameTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataStateState,
                onTextChange = getPersonalInfoDataStateState().onTextChange,
                onNameTextFieldVisibilityChange = getPersonalInfoDataStateState().onNameTextFieldVisibilityChange
            )

            DateTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataStateState,
                onDateTextChange = getPersonalInfoDataStateState().onDateTextChange,
                onDateTextFieldVisibilityChange = getPersonalInfoDataStateState().onDateTextFieldVisibilityChange
            )

            NumericWeightTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataStateState,
                onNumericUnitTextChange = getPersonalInfoDataStateState().onWeightTextChange,
                onNumericUnitTextFieldVisibilityChange = getPersonalInfoDataStateState().onWeightTextFieldVisibilityChange,
                unit = "Kg",
                contentDescription = "weight",
                resourceIcon1 = R.drawable.baseline_point_of_sale_24,
                validator = ValidatorsPersonalField.weightValidator
            )

            NumericHeightTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataStateState,
                onNumericUnitTextChange = getPersonalInfoDataStateState().onHeightTextChange,
                onNumericUnitTextFieldVisibilityChange = getPersonalInfoDataStateState().onHeightTextFieldVisibilityChange,
                unit = "m",
                contentDescription = "height",
                resourceIcon1 = R.drawable.baseline_boy_24,
                validator = ValidatorsPersonalField.heightValidator
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 50.dp, end = 20.dp),
                onClick = {

                    if (validatePersonalInfo().isNotEmpty()) {
                        getInvalidAlertDialogState().alertDialogPersonalFieldVisibility = true
                        getInvalidAlertDialogState().invalidFields = validatePersonalInfo()
                        return@Button
                    }

                    val personalInfoListReadFromDB = getPersonalInfoListReadFromDB()
                    if (personalInfoListReadFromDB.isNotEmpty() && personalInfoListReadFromDB[0].id != -1) {
                        personalInfoListReadFromDB[0].name = getPersonalInfoDataStateState().name
                        personalInfoListReadFromDB[0].birthdate = getPersonalInfoDataStateState().date
                        personalInfoListReadFromDB[0].weight = getPersonalInfoDataStateState().weight.toDouble()
                        personalInfoListReadFromDB[0].height = getPersonalInfoDataStateState().height.toDouble()
                        Log.d("Flow", "PersonalInfoFormScaffold: updating")
                        updatePersonalData(personalInfoListReadFromDB[0])
                    } else {
                        val personalInfo = PersonalInfo()
                        personalInfo.name = getPersonalInfoDataStateState().name
                        personalInfo.birthdate = getPersonalInfoDataStateState().date
                        personalInfo.weight = getPersonalInfoDataStateState().weight.toDouble()
                        personalInfo.height = getPersonalInfoDataStateState().height.toDouble()
                        Log.d("Flow", "PersonalInfoFormScaffold: inserting")
                        insertPersonalData(personalInfo)
                    }


                }
            ) {
                Text(text = "Save info", color = Color.White, textAlign = TextAlign.Center)

            }

            if ( getInvalidAlertDialogState().alertDialogPersonalFieldVisibility) {
                ValidationAlertDialog(
                    getInvalidAlertDialogState,
                )
            }

            if (getUpdateAlertDialogState().alertDialogUPersonalFieldVisibility) {
                UpdateAlertDialog(getUpdateAlertDialogState().setVisibilityAlertDialogStatusPersonalInfoU)
            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun PersonalInfoContentPreview() {
    PersonalInfoContent(
        getPersonalInfoDataStateState = { PersonalInfoDataState() },
        getPersonalInfoListReadFromDB = { listOf(PersonalInfo()) },
        validatePersonalInfo = { listOf() },
        getInvalidAlertDialogState = { InvalidAlertDialogState() },
        getUpdateAlertDialogState = { UpdateAlertDialogState() },
        updatePersonalData = {},
        insertPersonalData = {},
    )
}