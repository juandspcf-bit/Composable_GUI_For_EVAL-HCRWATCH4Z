package com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.data.MockData
import com.icxcu.adsmartbandapp.screens.plotsFields.DatePickerDialogSample
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhysicalActivityLayoutScaffold(
    stepsList: () -> List<Int>,
    distanceList: () -> List<Double>,
    caloriesList: () -> List<Double>,
    getSelectedDay: () -> String,
    stateShowDialogDatePickerSetter: (Boolean) -> Unit,
    stateShowDialogDatePickerValue: () -> Boolean,
    stateMiliSecondsDateDialogDatePickerSetterS: (Long) -> Unit,
    navLambda: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Physical activity for\n${getSelectedDay()}",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
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
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {


                PhysicalActivityContent(
                    stepsList,
                    distanceList,
                    caloriesList
                )
            }

            if (stateShowDialogDatePickerValue()) {
                DatePickerDialogSample(
                    stateShowDialogDatePickerSetter,
                    stateMiliSecondsDateDialogDatePickerSetterS
                )
            }

        },

        )
}


@Composable
fun PhysicalActivityContent(
    stepsList: () -> List<Int>,
    distanceList: () -> List<Double>,
    caloriesList: () -> List<Double>,
) {
    Column(
        modifier = Modifier
            .background(Color(0xff1d2a35))
            .fillMaxSize()
    ) {

        Log.d("PhysicalActivityContent", "PhysicalActivityContent: ")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        ) {

            val stepsEntries by remember {
                derivedStateOf {
                    stepsList().mapIndexed { index, y ->
                        val entry = EntryHour(
                            Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                            index.toFloat(), y.toFloat()
                        )
                        entry
                    }
                }
            }


            val chartEntryModel = ChartEntryModelProducer(stepsEntries)

            MyComposeBarChart(
                chartEntryModel = chartEntryModel,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(start= 10.dp, end = 10.dp, bottom = 15.dp),
                legend = rememberLegendPhysicalActivity()
            )

        }

        Divider(modifier = Modifier.height(2.dp))

        PhysicalActivityLazyListSelector(
            stepsList,
            distanceList,
            caloriesList
        )
    }
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
    val stateShowDialogDatePickerSetter: (Boolean) -> Unit = { value ->
        stateShowDialogDatePicker = value
    }

    val stateMiliSecondsDateDialogDatePickerSetter: (Long) -> Unit = { value ->
        stateMiliSecondsDateDialogDatePicker = value

        val date = Date(value)
        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateData = formattedDate.format(date)
    }

    val getSelectedDay = {
        "24/10/1981"
    }

    val date = Date()
    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateData = formattedDate.format(date)
    PhysicalActivityLayoutScaffold(
        stepsList,
        distanceList,
        caloriesList,
        getSelectedDay,
        stateShowDialogDatePickerSetter,
        stateShowDialogDatePickerValue,
        stateMiliSecondsDateDialogDatePickerSetter
    ) {}
}

