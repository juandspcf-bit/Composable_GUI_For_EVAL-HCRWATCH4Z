package com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.icxcu.adsmartbandapp.data.MockData
import com.icxcu.adsmartbandapp.screens.plotsFields.DatePickerDialogSample
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhysicalActivityLayoutScaffold(
    stepsList: () -> List<Int>,
    distanceList: () -> List<Double>,
    caloriesList: () -> List<Double>,
    selectedDay: String,
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
            MediumTopAppBar(
                title = {
                    Text(
                        text = "Physical activity for $selectedDay",
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
                PhysicalActivityContent(
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





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StepsPlotsPreview2() {
    val stepsList = {
        MockData.valuesToday.stepList
    }
    val distanceList = {
        MockData.valuesToday.distanceList
    }
    val caloriesList = {
        MockData.valuesToday.caloriesList
    }

    var stateShowDialogDatePicker by remember {
        mutableStateOf(false)
    }
    var stateMiliSecondsDateDialogDatePicker by remember {
        mutableStateOf(0L)
    }

    val stateShowDialogDatePickerValue = {
        stateShowDialogDatePicker
    }
    val stateShowDialogDatePickerSetter:(Boolean) -> Unit = { value ->
        stateShowDialogDatePicker = value
    }

    val stateMiliSecondsDateDialogDatePickerVal = {
        stateMiliSecondsDateDialogDatePicker
    }
    val stateMiliSecondsDateDialogDatePickerSetter:(Long) -> Unit = { value ->
        stateMiliSecondsDateDialogDatePicker = value

        val date = Date(value)
        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateData = formattedDate.format(date)


    }

    val date = Date()
    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateData = formattedDate.format(date)
    PhysicalActivityLayoutScaffold(
        stepsList,
        distanceList,
        caloriesList,
        selectedDay = dateData,
        stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePickerVal,
        stateMiliSecondsDateDialogDatePickerSetter
    ) {}
}

