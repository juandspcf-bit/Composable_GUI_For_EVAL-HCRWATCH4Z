package com.icxcu.adsmartbandapp.screens.plotsFields

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.icxcu.adsmartbandapp.repositories.Values
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.time.Duration


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhysicalActivityInfo(
    values: Values,
    navLambda: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
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
                PhysicalActivityInfoContent(values)
            }

        },


        )
}


@Composable
fun PhysicalActivityInfoContent(values: Values) {
    ConstraintLayout(
        modifier = Modifier
            .background(Color(0xff1d2a35))
            .fillMaxSize()
    ) {
        val (plot, divider, tabRow, list) = createRefs()
        val guideH3 = createGuidelineFromTop(fraction = 0.4f)


        val stepsEntries = values.stepList.mapIndexed { index, y ->
            val entry = EntryHour(
                Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                index.toFloat(), y.toFloat()
            )
            entry
        }

        val distanceEntries = values.distanceList.mapIndexed { index, y ->
            val entry = EntryHour(
                Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                index.toFloat(), y.toFloat()
            )
            entry
        }

        val caloriesEntries = values.caloriesList.mapIndexed { index, y ->
            var entry = EntryHour(
                Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                index.toFloat(), y.toFloat()
            )
            entry
        }

        Box(modifier = Modifier
/*            .padding(start = 10.dp, end = 10.dp)
            .clip(shape = RoundedCornerShape(size = 25.dp))*/
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

        ListSelector(values, modifierTabs = Modifier
            .constrainAs(tabRow) {
                top.linkTo(divider.bottom)
                linkTo(start = parent.start, end = parent.end)
                height = Dimension.fillToConstraints
            }//.padding(top = 20.dp, bottom = 20.dp, start = 50.dp, end = 50.dp),
            ,modifierList = Modifier
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
            color= Color.White,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally),

        )
    }
}


@Composable
fun ListSelector(values: Values, modifierTabs: Modifier, modifierList: Modifier) {
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
                    dataList = values.stepList,
                    hoursList,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            1 -> {
                DistanceList(
                    dataList = values.distanceList,
                    hoursList,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            2 -> {
                CaloriesList(
                    dataList = values.caloriesList,
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
            RowSteps(valueSteps = "$item Steps",
                resource = R.drawable.runner_for_lazy_colum_list,
                hourTime = getIntervals(index, hourList))
        }
    }
}

@Composable
fun DistanceList(dataList: List<Double>, hourList: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(dataList) { index, item ->
            RowSteps(valueSteps = "$item Kms",
                resource = R.drawable.distance_for_lazy_list,
                hourTime = getIntervals(index, hourList))
        }
    }
}

@Composable
fun CaloriesList(dataList: List<Double>, hourList: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(dataList) { index, item ->
            RowSteps(valueSteps = "$item Kcal",
                resource = R.drawable.calories_for_lazy_list,
                hourTime = getIntervals(index, hourList))
        }
    }
}

@Composable
fun RowSteps(
    valueSteps: String,
    resource: Int = R.drawable.ic_launcher_foreground,
    hourTime:String
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

            Text(text = hourTime, color= Color.White, modifier = Modifier.constrainAs(hour) {
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

            Text(text = valueSteps, color= Color.White, modifier = Modifier.constrainAs(value) {
                linkTo(start = gideIconValue, end = parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)

            })


        }

        Divider(modifier = Modifier.height(1.dp))


    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StepsPlotsPreview() {

    val stepValue = listOf(
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        141,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        239,
        110,
        1455,
        3177,
        2404,
        246,
        315,
        65,
        25,
        74,
        0,
        0,
        0,
        47,
        77,
        1025,
        1600,
        164,
        252,
        37,
        51,
        79,
        0,
        11,
        0,
        17,
        43,
        311,
        0
    )

    var disValue = listOf(
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.109,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.185,
        0.077,
        1.127,
        2.512,
        1.849,
        0.185,
        0.249,
        0.053,
        0.02,
        0.058,
        0.0,
        0.0,
        0.0,
        0.039,
        0.061,
        0.788,
        1.201,
        0.131,
        0.193,
        0.03,
        0.04,
        0.062,
        0.0,
        0.009,
        0.0,
        0.014,
        0.034,
        0.204,
        0.0
    )

    var caloriesValues = listOf(
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        7.1,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        12.1,
        5.1,
        73.7,
        164.2,
        121.0,
        12.0,
        16.4,
        3.4,
        1.3,
        3.8,
        0.0,
        0.0,
        0.0,
        2.6,
        3.9,
        51.6,
        78.6,
        8.5,
        12.7,
        1.9,
        2.6,
        4.1,
        0.0,
        0.6,
        0.0,
        0.8,
        2.3,
        13.3,
        0.0
    )

    val values = Values(stepValue, disValue, caloriesValues)

    PhysicalActivityInfo(values = values) {}
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

fun getIntervals(index:Int=0, hoursList:List<String>) = when(index){
    47->"${hoursList[46]} - 00:00"
    else -> "${hoursList[index]} - ${hoursList[index+1]}"
}


