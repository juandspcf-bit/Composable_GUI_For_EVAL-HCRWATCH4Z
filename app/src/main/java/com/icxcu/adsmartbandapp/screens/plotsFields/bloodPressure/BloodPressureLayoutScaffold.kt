package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.data.MockData
import com.icxcu.adsmartbandapp.screens.plotsFields.DatePickerDialogSample
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.EntryHour
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getHours
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getIntervals
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodPressureLayoutScaffold(
    systolicList: () -> List<Double>,
    diastolicList: () -> List<Double>,
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
    val stateMiliSecondsDateDialogDatePickerSetter: (Long) -> Unit = { value ->
        stateMiliSecondsDateDialogDatePickerSetterS(value)
    }
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = "Blood Pressure for $selectedDay",
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
                val systolicListScaffold = {
                    systolicList()
                }
                val diastolicListScaffold = {
                    diastolicList()
                }

                BloodPressureInfoContent(
                    systolicListScaffold,
                    diastolicListScaffold,
                )

                if (stateShowDialogDatePickerValue()) {
                    DatePickerDialogSample(
                        stateShowDialogDatePickerSetter,
                        stateMiliSecondsDateDialogDatePicker,
                        stateMiliSecondsDateDialogDatePickerSetter
                    )
                }
            }

        },
    )
}

@Composable
fun BloodPressureInfoContent(
    systolicListContent: () -> List<Double>,
    diastolicListContent: () -> List<Double>,
) {

    ConstraintLayout(
        modifier = Modifier
            .background(Color(0xff1d2a35))
            .fillMaxSize()
    ) {
        val (plot, divider, statistics, list) = createRefs()
        val guide4f = createGuidelineFromTop(fraction = 0.4f)
        createGuidelineFromTop(fraction = 0.6f)


        val mapSystolic = systolicListContent().mapIndexed { index, y ->
            val entry = EntryHour(
                Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                index.toFloat(), y.toFloat()
            )
            entry
        }
        val mapDiastolic = diastolicListContent().mapIndexed { index, y ->
            val entry = EntryHour(
                Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                index.toFloat(), y.toFloat()
            )
            entry
        }

        Box(modifier = Modifier
            .constrainAs(plot) {
                top.linkTo(parent.top)
                bottom.linkTo(guide4f)
                linkTo(start = parent.start, end = parent.end)
                height = Dimension.fillToConstraints
            }
        ) {

            val chartEntryModel = ChartEntryModelProducer(mapSystolic, mapDiastolic)
            MyComposePlotChart(chartEntryModel, modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(bottom = 15.dp),)

        }

        Divider(modifier = Modifier
            .constrainAs(divider) {
                top.linkTo(guide4f)

                linkTo(start = parent.start, end = parent.end)
                height = Dimension.wrapContent
            }
            .height(2.dp))

        StatisticsBloodPressure(systolicListContent = systolicListContent,
            modifier = Modifier
                .constrainAs(statistics) {
                    top.linkTo(divider.bottom)
                    //bottom.linkTo(guide6f)
                    linkTo(start = parent.start, end = parent.end)
                    height = Dimension.fillToConstraints
                }
                .background(Color(0xFF6B1A79))
                .fillMaxWidth())


        BloodPressureSList(
            systolicListContent,
            diastolicListContent,
            modifier = Modifier
                .constrainAs(list) {
                    top.linkTo(statistics.bottom)
                    bottom.linkTo(parent.bottom)
                    linkTo(start = parent.start, end = parent.end)
                    height = Dimension.fillToConstraints
                }
                .fillMaxSize())

    }
}


@Composable
fun StatisticsBloodPressure(
    systolicListContent: () -> List<Double>,
    modifier: Modifier = Modifier
) {
    val maxValueSystolic = systolicListContent().max()
    val maxValueSystolicValue= String.format("%.1f mmHg", maxValueSystolic)
    val hourMax = findIndex(maxValueSystolic, systolicListContent())


    val minValueSystolic = systolicListContent().min()
    val minValueSystolicValue= String.format("%.1f mmHg", minValueSystolic)
    val hourMin = findIndex(minValueSystolic, systolicListContent())

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    text = "Max Value",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = maxValueSystolicValue,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }

        item{
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    text = "Min Value",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = minValueSystolicValue,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }

        item{
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 10.dp)
            ) {

                Text(
                    text = "Time",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    hourMax,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }

        item{
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = "Time",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    hourMin,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }

        }



    }




}

fun findIndex(value:Double, data:List<Double>):String{
    var hour = " "
    if (value > 0) {
        val filterIndexed = data.mapIndexed { index, d ->
            if (d == value) {
                index
            } else {
                -1
            }
        }.filter {
            it>-1
        }
        if (filterIndexed.isNotEmpty()) {
            hour = getHours()[filterIndexed[0]]
        }
    }

    return hour
}


@Composable
fun BloodPressureSList(
    systolicListContent: () -> List<Double>,
    diastolicListContent: () -> List<Double>,
    modifier: Modifier
) {

    val systolicList = {
        systolicListContent()
    }

    val diastolicList = {
        diastolicListContent()
    }


    Box(
        modifier = modifier
    ) {
        val hoursList = getHours()
        BloodPressureList(
            systolicList,
            diastolicList,
            hoursList,
            modifier = Modifier
                .fillMaxSize()
        )

    }
}


@Composable
fun BloodPressureList(
    systolicListContent: () -> List<Double>,
    diastolicListContent: () -> List<Double>,
    hourList: List<String>,
    modifier: Modifier = Modifier
) {
    val systolicList = {
        systolicListContent()
    }

    val diastolicList = {
        diastolicListContent()
    }

    val bloodPressureFullList = systolicList().zip(diastolicList()).toList()

    LazyColumn(modifier = modifier) {
        itemsIndexed(bloodPressureFullList) { index, pair ->
            val systolicValue = pair.first
            val diastolicValue = pair.second

            val stringSystolicValue = String.format("%.1f", systolicValue)
            val stringDiastolicValue = String.format("%.1f", diastolicValue)

            val getCategory =getBloodPressureCategory(systolicValue, diastolicValue)
            val category = mapCategories[getCategory]
            val readableCategory = mapToReadableCategories[getCategory]


            RowBloodPressure(
                valueBloodPressure = "$stringSystolicValue/$stringDiastolicValue mmHg",
                resource = category?:R.drawable.blood_pressure_gauge,
                readableCategory= readableCategory?:"No Category",
                hourTime = getIntervals(index, hourList)
            )

        }
    }
}

@Composable
fun RowBloodPressure(
    valueBloodPressure: String,
    resource: Int = R.drawable.blood_pressure_gauge,
    readableCategory: String = "No category",
    hourTime: String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxSize()

        ) {
            val (hour, icon, value) = createRefs()
            val guideHourIcon = createGuidelineFromStart(fraction = 0.35f)
            val gideIconValue = createGuidelineFromStart(fraction = 0.65f)

            Text(text = hourTime, color = Color.White, modifier = Modifier.constrainAs(hour) {
                linkTo(start = parent.start, end = guideHourIcon)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                height = Dimension.wrapContent
            })

            Image(
                painter = painterResource(resource),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(guideHourIcon)
                        end.linkTo(gideIconValue)
                        width = Dimension.fillToConstraints
                    }
                    .size(30.dp).fillMaxWidth()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                .constrainAs(value) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(gideIconValue)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }) {

                Text(text = valueBloodPressure, color = Color.White)
                Text(readableCategory, color = Color(0x9fffffff), textAlign = TextAlign.Center)
            }





        }

        Divider(modifier = Modifier.height(1.dp))


    }

}


@Preview(showBackground = true)
@Composable
fun BloodPressureLayoutScaffoldPreview() {
    BloodPressureInfoContent(
        { MockData.valuesToday.systolic },
        { MockData.valuesToday.diastolic },
    )


}