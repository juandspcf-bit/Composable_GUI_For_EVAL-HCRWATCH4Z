package com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
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
            padding
            PersonalInfoContent(
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
                validatePersonalInfo,
                visibilityAlertDialogStatusPersonalInfo,
                setVisibilityAlertDialogStatusPersonalInfo,
                getInvalidFields,
                setInvalidFields,
                visibilityAlertDialogStatusPersonalInfoU,
                setVisibilityAlertDialogStatusPersonalInfoU,
                updatePersonalData,
                insertPersonalData,
            )

        },
    )
}

