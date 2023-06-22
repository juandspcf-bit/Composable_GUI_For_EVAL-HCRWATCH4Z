package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoFormScaffold(
    navLambda: () -> Unit,
    getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    validatePersonalInfo: () -> List<String> = { listOf() },
    getInvalidAlertDialogState: () -> InvalidAlertDialogState,
    getUpdateAlertDialogVisibilityState: () -> UpdateAlertDialogPersonalFieldVisibilityState,
    getInsertAlertDialogVisibilityState: () -> InsertAlertDialogPersonalFieldVisibilityState,
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff1d2a35)),
                contentAlignment = Alignment.Center
            ){
                PersonalInfoContent(
                    getPersonalInfoDataStateState,
                    validatePersonalInfo,
                    getInvalidAlertDialogState,
                    updatePersonalData,
                    insertPersonalData,
                )

                if (getInvalidAlertDialogState().alertDialogPersonalFieldVisibility) {
                    ValidationAlertDialog(
                        getInvalidAlertDialogState,
                    )
                }

                if (getUpdateAlertDialogVisibilityState().updateAlertDialogPersonalFieldVisibility) {
                    UpdateAlertDialog(getUpdateAlertDialogVisibilityState().setUpdateAlertDialogPersonalFieldVisibility)
                }

                if (getInsertAlertDialogVisibilityState().insertAlertDialogPersonalFieldVisibility) {
                    InsertAlertDialog(getInsertAlertDialogVisibilityState().setInsertAlertDialogPersonalFieldVisibility)
                }
            }


        },
    )
}


@Preview(showBackground = true)
@Composable
fun PersonalInfoFormScaffoldPreview() {
    PersonalInfoFormScaffold(
        navLambda = {},
        getPersonalInfoDataStateState = { PersonalInfoDataState() },
        validatePersonalInfo = { listOf() },
        getInvalidAlertDialogState = { InvalidAlertDialogState() },
        getUpdateAlertDialogVisibilityState = { UpdateAlertDialogPersonalFieldVisibilityState() },
        getInsertAlertDialogVisibilityState = { InsertAlertDialogPersonalFieldVisibilityState() },
        updatePersonalData = {},
    ) {}


}