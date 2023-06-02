package com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
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
            MediumTopAppBar(
                title = {
                    Text(
                        text = "Physical activity for ${getSelectedDay()}",
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
    stepsListContent: () -> List<Int>,
    distanceListContent: () -> List<Double>,
    caloriesListContent: () -> List<Double>,
) {
    ConstraintLayout(
        modifier = Modifier
            .background(Color(0xff1d2a35))
            .fillMaxSize()
    ) {
        val (plot, divider, tabRow, list) = createRefs()
        val guideH3 = createGuidelineFromTop(fraction = 0.4f)


        val stepList = {
            stepsListContent()
        }

        val distanceList = {
            distanceListContent()
        }

        val caloriesList = {
            caloriesListContent()
        }

        Box(modifier = Modifier
            .background(
                Color(0xfff5f5f7)
            )
            .constrainAs(plot) {
                top.linkTo(parent.top)
                bottom.linkTo(guideH3)
                linkTo(start = parent.start, end = parent.end)
                height = Dimension.fillToConstraints
            }
        ) {

            val stepsEntries = stepsListContent().mapIndexed { index, y ->
                val entry = EntryHour(
                    Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                    index.toFloat(), y.toFloat()
                )
                entry
            }
            val chartEntryModel = ChartEntryModelProducer(stepsEntries)

            MyComposeBarChart(
                chartEntryModel = chartEntryModel,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(bottom = 15.dp),
                legend = rememberLegendPhysicalActivity()
            )

        }

        Divider(modifier = Modifier
            .constrainAs(divider) {
                top.linkTo(guideH3)
                //bottom.linkTo(list.top)
                linkTo(start = parent.start, end = parent.end)
                height = Dimension.wrapContent
            }
            .height(2.dp))

        ListSelector(
            stepList,
            distanceList,
            caloriesList,
            modifierTabs = Modifier
                .constrainAs(tabRow) {
                    top.linkTo(divider.bottom)
                    linkTo(start = parent.start, end = parent.end)
                    height = Dimension.fillToConstraints
                }//.padding(top = 20.dp, bottom = 20.dp, start = 50.dp, end = 50.dp),
            , modifierList = Modifier
                .constrainAs(list) {
                    top.linkTo(tabRow.bottom)
                    bottom.linkTo(parent.bottom)
                    linkTo(start = parent.start, end = parent.end)
                    height = Dimension.fillToConstraints
                }
                .fillMaxSize())
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

    val stateMiliSecondsDateDialogDatePickerVal = {
        stateMiliSecondsDateDialogDatePicker
    }
    val stateMiliSecondsDateDialogDatePickerSetter: (Long) -> Unit = { value ->
        stateMiliSecondsDateDialogDatePicker = value

        val date = Date(value)
        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateData = formattedDate.format(date)
    }

    val getSelectedDay = {
        "///"
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

