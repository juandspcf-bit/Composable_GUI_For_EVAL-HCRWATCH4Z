package com.icxcu.adsmartbandapp.screens.plotsFields

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.icxcu.adsmartbandapp.data.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhysicalActivityLayoutScaffold(
    stepsList: () -> List<Int>,
    distanceList: () -> List<Double>,
    caloriesList: () -> List<Double>,
    stateShowDialogDatePickerSetter: (Boolean) -> Unit,
    stateShowDialogDatePickerValue: () -> Boolean,
    stateMiliSecondsDateDialogDatePickerS: () -> Long,
    stateMiliSecondsDateDialogDatePickerSetterS: (Long) -> Unit,
    navLambda: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val stateMiliSecondsDateDialogDatePicker = {
        stateMiliSecondsDateDialogDatePickerS()
    }
    val stateMiliSecondsDateDialogDatePickerSetter:(Long) -> Unit = { value ->
        stateMiliSecondsDateDialogDatePickerSetterS(value)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Steps", maxLines = 1,
                        overflow = TextOverflow.Ellipsis, color = Color.White
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
                actions = {
                    IconButton(onClick = {
                        stateShowDialogDatePickerSetter(!stateShowDialogDatePickerValue())
                    }) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Date Range",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = { padding ->
            Box(
                Modifier
                    .padding(padding)
                    .fillMaxSize(), contentAlignment = Alignment.TopCenter
            ) {

                val stepsListScaffold = {
                    stepsList()
                }
                val distanceListScaffold = {
                    distanceList()
                }
                val caloriesListScaffold = {
                    caloriesList()
                }
                PhysicalActivityInfoContent(
                    stepsListScaffold,
                    distanceListScaffold,
                    caloriesListScaffold
                )
            }

            if (stateShowDialogDatePickerValue()) {
                DatePickerDialogSample(stateShowDialogDatePickerSetter,
                    stateMiliSecondsDateDialogDatePicker,
                    stateMiliSecondsDateDialogDatePickerSetter
                )
            }

        },

        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogSample(
    modifyShowDialog: (Boolean) -> Unit,
    stateMiliSecondsDateDialogDatePicker: () -> Long,
    stateMiliSecondsDateDialogDatePickerSetter: (Long) -> Unit,
) {
    // Decoupled snackbar host state from scaffold state for demo purposes.
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)
    val openDialog = remember { mutableStateOf(true) }
// TODO demo how to read the selected date from the state.
    if (openDialog.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled =
            remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false
                modifyShowDialog(false)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false

                        datePickerState.selectedDateMillis?.let {
                            stateMiliSecondsDateDialogDatePickerSetter(
                                it
                            )
                        }
                        modifyShowDialog(false)
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        modifyShowDialog(false)
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StepsPlotsPreview() {
    val stepsList = {
        MockData.values.stepList
    }
    val distanceList = {
        MockData.values.distanceList
    }
    val caloriesList = {
        MockData.values.caloriesList
    }

    var stateShowDialogDatePicker = {
        false
    }

    /*    PhysicalActivityLayoutScaffold(
            stepsList,
            distanceList,
            caloriesList,
            {},
            stateShowDialogDatePicker
        ) {}*/
}

