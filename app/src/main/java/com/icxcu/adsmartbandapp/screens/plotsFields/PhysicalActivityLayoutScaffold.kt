package com.icxcu.adsmartbandapp.screens.plotsFields

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.data.MockData
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhysicalActivityLayoutScaffold(
    stepsList: List<Int>,
    distanceList: List<Double>,
    caloriesList: List<Double>,
    navLambda: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var showDialog by remember {
        mutableStateOf(false)
    }
    val modifyShowDialog: (Boolean) -> Unit = { value ->
        showDialog = value
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
                        showDialog = !showDialog
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
                PhysicalActivityInfoContent(
                    stepsList,
                    distanceList,
                    caloriesList
                )
            }

            if (showDialog) {
                DatePickerDialogSample(modifyShowDialog)
            }

        },


        )
}

@Composable
fun PhysicalActivityInfoContent(
    stepsList: List<Int>,
    distanceList: List<Double>,
    caloriesList: List<Double>,) {
    ConstraintLayout(
        modifier = Modifier
            .background(Color(0xff1d2a35))
            .fillMaxSize()
    ) {
        val (plot, divider, tabRow, list) = createRefs()
        val guideH3 = createGuidelineFromTop(fraction = 0.4f)


        val stepsEntries = stepsList.mapIndexed { index, y ->
            val entry = EntryHour(
                Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                index.toFloat(), y.toFloat()
            )
            entry
        }

        distanceList.mapIndexed { index, y ->
            val entry = EntryHour(
                Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                index.toFloat(), y.toFloat()
            )
            entry
        }

        caloriesList.mapIndexed { index, y ->
            val entry = EntryHour(
                Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                index.toFloat(), y.toFloat()
            )
            entry
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
/*            when(state){
                0->ComposeChart2(chartEntryModelProducer = ChartEntryModelProducer(stepsEntries))
                1->ComposeChart2(chartEntryModelProducer = ChartEntryModelProducer(distanceEntries))
                2->ComposeChart2(chartEntryModelProducer = ChartEntryModelProducer(caloriesEntries))
            }*/
            ComposeBartCharts(chartEntryModelProducer = ChartEntryModelProducer(stepsEntries))
        }

        Divider(modifier = Modifier
            .constrainAs(divider) {
                top.linkTo(guideH3)
                //bottom.linkTo(list.top)
                linkTo(start = parent.start, end = parent.end)
            }
            .height(2.dp))

        ListSelector(
            listSteps = stepsList,
            distanceList = distanceList,
            caloriesList =caloriesList,
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

@Composable
fun MyTab(title: String, onClick: () -> Unit, selected: Boolean) {
    Tab(
        selected,
        onClick,
        selectedContentColor = Color.Red,
        unselectedContentColor = Color.Green,
        modifier = Modifier.fillMaxHeight()
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally),

            )
    }
}


@Composable
fun ListSelector(
    listSteps: List<Int>,
    distanceList: List<Double>,
    caloriesList: List<Double>,
    modifierTabs: Modifier,
    modifierList: Modifier
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Steps", "Distance", "Calories")

    TabRow(
        selectedTabIndex = state,
        modifier = modifierTabs.height(52.dp),
        containerColor = Color.DarkGray,
        divider = {
            Divider(modifier = Modifier.fillMaxWidth(), color = Color(0xFF855454))
        },
    ) {

        titles.forEachIndexed { index, title ->
            MyTab(title = title, onClick = { state = index }, selected = (index == state))
        }
    }

    Box(
        modifier = modifierList
    ) {
        val hoursList = getHours()
        when (state) {
            0 -> {
                StepsList(
                    dataList = listSteps,
                    hoursList,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            1 -> {
                DistanceList(
                    dataList = distanceList,
                    hoursList,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            2 -> {
                CaloriesList(
                    dataList = caloriesList,
                    hoursList,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

    }
}


@Composable
fun StepsList(dataList: List<Int>, hourList: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(dataList) { index, item ->
            RowSteps(
                valueSteps = "$item Steps",
                resource = R.drawable.runner_for_lazy_colum_list,
                hourTime = getIntervals(index, hourList)
            )
        }
    }
}

@Composable
fun DistanceList(dataList: List<Double>, hourList: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(dataList) { index, item ->
            RowSteps(
                valueSteps = "$item Kms",
                resource = R.drawable.distance_for_lazy_list,
                hourTime = getIntervals(index, hourList)
            )
        }
    }
}

@Composable
fun CaloriesList(dataList: List<Double>, hourList: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(dataList) { index, item ->
            RowSteps(
                valueSteps = "$item Kcal",
                resource = R.drawable.calories_for_lazy_list,
                hourTime = getIntervals(index, hourList)
            )
        }
    }
}

@Composable
fun RowSteps(
    valueSteps: String,
    resource: Int = R.drawable.ic_launcher_foreground,
    hourTime: String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxSize()

        ) {
            val (hour, icon, value) = createRefs()
            val guideHourIcon = createGuidelineFromStart(fraction = 0.25f)
            val gideIconValue = createGuidelineFromStart(fraction = 0.75f)

            Text(text = hourTime, color = Color.White, modifier = Modifier.constrainAs(hour) {
                linkTo(start = parent.start, end = guideHourIcon)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            })

            Image(
                painter = painterResource(resource),
                contentScale = ContentScale.FillHeight,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(guideHourIcon)
                        end.linkTo(gideIconValue)
                    }
                    .size(50.dp)

            )

            Text(text = valueSteps, color = Color.White, modifier = Modifier.constrainAs(value) {
                linkTo(start = gideIconValue, end = parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)

            })


        }

        Divider(modifier = Modifier.height(1.dp))


    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogSample(modifyShowDialog: (Boolean) -> Unit) {
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
                        Log.d(
                            "Dialog",
                            "DatePickerDialogSample: ${datePickerState.selectedDateMillis}"
                        )

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
    PhysicalActivityLayoutScaffold(
        MockData.values.stepList,
        MockData.values.distanceList,
        MockData.values.caloriesList
    ) {}
}


fun getHours() = MutableList(48) { 0 }.mapIndexed { index, value ->
    val hour = "${Duration.ofHours(0).plusMinutes(30L * index).toHours()}"
    val time = hour + if ((index + 1) % 2 == 0) {
        ":30"
    } else {
        ":00"
    }
    time
}

fun getIntervals(index: Int = 0, hoursList: List<String>) = when (index) {
    47 -> "${hoursList[46]} - 00:00"
    else -> "${hoursList[index]} - ${hoursList[index + 1]}"
}
