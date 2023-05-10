package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo

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
    getPersonalInfoListReadFromDB: () -> List<PersonalInfo>,
    validatePersonalInfo: () -> Boolean = { false },
    visibilityAlertDialogStatusPersonalInfo: () -> Boolean,
    setVisibilityAlertDialogStatusPersonalInfo: (Boolean) -> Unit,
    updatePersonalData: (PersonalInfo) -> Unit = {},
    insertPersonalData: (PersonalInfo) -> Unit = {},
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
        content = {
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

                            if (validatePersonalInfo().not()) {
                                Log.d("Flow", "PersonalInfoFormScaffold: no valid")
                                setVisibilityAlertDialogStatusPersonalInfo(true)
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

                    if(visibilityAlertDialogStatusPersonalInfo()){
                        ValidationAlertDialog(setVisibilityAlertDialogStatusPersonalInfo)
                    }


                }
            }

        },
    )
}


@Composable
fun ValidationAlertDialog(
    setVisibilityAlertDialogStatusPersonalInfo: (Boolean) -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back button.
            // If you want to disable that functionality, simply leave this block empty.
            setVisibilityAlertDialogStatusPersonalInfo(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // perform the confirm action and
                    // close the dialog
                    setVisibilityAlertDialogStatusPersonalInfo(false)
                }
            ) {
                Text(
                    text = "Confirm",
                    color = Color(0xFFDCE775),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    // close the dialog
                    setVisibilityAlertDialogStatusPersonalInfo(false)
                }
            ) {
                Text(
                    text = "Dismiss",
                    color = Color(0xFFDCE775),
                )
            }
        },
        title = {
            Text(text = "Invalid data in the form")
        },
        text = {
            Text(text = "The following fields are invalid")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .background(Color.Transparent),
        shape = RoundedCornerShape(20.dp),
        containerColor = Color(0xFF2196F3),
        textContentColor = Color.White,
        titleContentColor = Color.White

        )
}