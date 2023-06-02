package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.icxcu.adsmartbandapp.screens.plotsFields.PlotsConstants
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.EntryHour
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getHours
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.legendItemIconPaddingValue
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.legendItemIconSize
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.legendItemLabelTextSize
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.legendItemSpacing
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.legendPadding
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.legend.verticalLegendItem
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodPressureLayoutScaffold(
    systolicList: () -> List<Double>,
    diastolicList: () -> List<Double>,
    getSelectedDay: () -> String,
    stateShowDialogDatePickerSetter: (Boolean) -> Unit,
    stateShowDialogDatePickerValue: () -> Boolean,
    stateMiliSecondsDateDialogDatePickerSetter: (Long) -> Unit,
    navLambda: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()


    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = "Blood Pressure for ${getSelectedDay()}",
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


                BloodPressureInfoContent(
                    systolicList,
                    diastolicList,
                )

                if (stateShowDialogDatePickerValue()) {
                    DatePickerDialogSample(
                        stateShowDialogDatePickerSetter,
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

    Column(
        modifier = Modifier
            .background(Color(0xff1d2a35))
            .fillMaxSize()
    ) {

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
            .fillMaxWidth()
            .fillMaxHeight(0.4f)) {

            val chartEntryModel = ChartEntryModelProducer(mapSystolic, mapDiastolic)
            MyComposePlotChart(
                chartEntryModel,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(bottom = 15.dp),
                rememberLegendBloodPressure()
            )

        }

        Divider(
            modifier = Modifier
                .height(2.dp)
        )

        StatisticsBloodPressure(
            systolicListContent = systolicListContent,
            modifier = Modifier
                .background(Color(0x7FFF5722))
                .fillMaxWidth()
                .width(50.dp)
        )

        Divider(
            modifier = Modifier
                .height(2.dp)
        )

        BloodPressureSList(
            systolicListContent,
            diastolicListContent,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
        )

    }
}


@Composable
fun StatisticsBloodPressure(
    systolicListContent: () -> List<Double>,
    modifier: Modifier = Modifier
) {
    val filteredSystolicListContent = systolicListContent().filter { it > 0.0 }
    if (filteredSystolicListContent.isEmpty()) return

    val maxValueSystolic = filteredSystolicListContent.max()
    val maxValueSystolicValue = String.format("%.1f mmHg", maxValueSystolic)
    val hourMax = findIndex(maxValueSystolic, systolicListContent())


    val minValueSystolic = filteredSystolicListContent.min()
    val minValueSystolicValue = String.format("%.1f mmHg", minValueSystolic)
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

        item {
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

        item {
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

        item {
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

fun findIndex(value: Double, data: List<Double>): String {
    var hour = " "
    if (value > 0) {
        val filterIndexed = data.mapIndexed { index, d ->
            if (d == value) {
                index
            } else {
                -1
            }
        }.filter {
            it > -1
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
        getHours()
        BloodPressureList(
            systolicList,
            diastolicList,
            modifier = Modifier
                .fillMaxSize()
        )

    }
}


@Composable
fun BloodPressureList(
    systolicListContent: () -> List<Double>,
    diastolicListContent: () -> List<Double>,
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
        itemsIndexed(
            bloodPressureFullList,
            key = { index, _ ->
                PlotsConstants.HOUR_INTERVALS[index]
            }
        ) { index, pair ->
            val systolicValue = pair.first
            val diastolicValue = pair.second

            val stringSystolicValue = String.format("%.1f", systolicValue)
            val stringDiastolicValue = String.format("%.1f", diastolicValue)

            val getCategory = getBloodPressureCategory(systolicValue, diastolicValue)
            val category = mapCategories[getCategory]
            val readableCategory = mapToReadableCategories[getCategory]


            RowBloodPressure(
                valueBloodPressure = "$stringSystolicValue/$stringDiastolicValue mmHg",
                resource = category ?: R.drawable.blood_pressure_gauge,
                readableCategory = readableCategory ?: "No Category",
                hourTime = PlotsConstants.HOUR_INTERVALS[index]
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
                    .size(30.dp)
                    .fillMaxWidth()
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


@Composable
fun rememberLegendBloodPressure() = verticalLegend(
    items = chartColorsPLot.mapIndexed { index, chartColor ->
        verticalLegendItem(
            icon = shapeComponent(Shapes.pillShape, chartColor),
            label = textComponent(
                color = Color.White,
                textSize = legendItemLabelTextSize,
                typeface = Typeface.MONOSPACE,
            ),
            labelText = if (index == 0) {
                "Systolic"
            } else {
                "Diastolic"
            },
        )
    },
    iconSize = legendItemIconSize,
    iconPadding = legendItemIconPaddingValue,
    spacing = legendItemSpacing,
    padding = legendPadding,
)

@Preview(showBackground = true)
@Composable
fun BloodPressureLayoutScaffoldPreview() {
    BloodPressureInfoContent(
        { MockData.valuesToday.systolicList },
        { MockData.valuesToday.diastolicList },
    )


}