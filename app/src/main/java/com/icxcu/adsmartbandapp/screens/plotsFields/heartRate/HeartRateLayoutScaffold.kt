package com.icxcu.adsmartbandapp.screens.plotsFields.heartRate

import android.graphics.Typeface
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.screens.plotsFields.DatePickerDialogSample
import com.icxcu.adsmartbandapp.screens.plotsFields.PlotsConstants
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.MyComposePlotChart
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.findIndex
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
fun HeartRateLayoutScaffold(
    heartRateList: () -> List<Double>,
    getSelectedDay: () -> String,
    stateShowDialogDatePickerSetter: (Boolean) -> Unit,
    stateShowDialogDatePickerValue: () -> Boolean,
    stateMiliSecondsDateDialogDatePickerSetter: (Long) -> Unit,
    getAgeCalculated: () -> Int,
    navLambda: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = "Heart Rate for ${getSelectedDay()}",
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
                val heartRateListScaffold = {
                    heartRateList()
                }


                HeartRateInfoContent(
                    heartRateListScaffold,
                    getAgeCalculated
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
fun HeartRateInfoContent(heartRateListContent: () -> List<Double>,
                         getAgeCalculated:()->Int,
) {
    ConstraintLayout(
        modifier = Modifier
            .background(Color(0xff1d2a35))
            .fillMaxSize()
    ) {
        val (plot, divider, statistics, list) = createRefs()
        val guide4f = createGuidelineFromTop(fraction = 0.4f)
        createGuidelineFromTop(fraction = 0.6f)


        val mapHeartRate = heartRateListContent().mapIndexed { index, y ->
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

            val chartEntryModel = ChartEntryModelProducer(mapHeartRate)
            MyComposePlotChart(
                chartEntryModel, modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(bottom = 15.dp),
                rememberLegendHeartRate()
            )

        }

        Divider(modifier = Modifier
            .constrainAs(divider) {
                top.linkTo(guide4f)

                linkTo(start = parent.start, end = parent.end)
                height = Dimension.wrapContent
            }
            .height(2.dp))

        StatisticsHeartRate(heartRateListContent = heartRateListContent,
            modifier = Modifier
                .constrainAs(statistics) {
                    top.linkTo(divider.bottom)
                    linkTo(start = parent.start, end = parent.end)
                    height = Dimension.fillToConstraints
                }
                .background(Color(0x7FFF5722))
                .fillMaxWidth())


        HeartRateListS(
            heartRateListContent,
            getAgeCalculated,
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
fun StatisticsHeartRate(heartRateListContent: () -> List<Double>, modifier: Modifier) {
    val filteredValueSystolic = heartRateListContent().filter { it >0.0 }
    if(filteredValueSystolic.isEmpty()) return

    val maxValueSystolic = filteredValueSystolic.max()
    val maxValueSystolicValue = String.format("%.1f bpm", maxValueSystolic)
    val hourMax = findIndex(maxValueSystolic, heartRateListContent())


    val minValueSystolic = filteredValueSystolic.min()
    val minValueSystolicValue = String.format("%.1f bpm", minValueSystolic)
    val hourMin = findIndex(minValueSystolic, heartRateListContent())

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


@Composable
fun HeartRateListS(heartRateListContent: () -> List<Double>,
                   getAgeCalculated:()->Int,
                   modifier: Modifier) {


    val heartRateList = {
        heartRateListContent()
    }


    Box(
        modifier = modifier
    ) {
        getHours()
        HeartRateList(
            heartRateList,
            getAgeCalculated,
            modifier = Modifier
                .fillMaxSize()
        )

    }

}

@Composable
fun HeartRateList(
    heartRateListContent: () -> List<Double>,
    getAgeCalculated: () -> Int,
    modifier: Modifier
) {


    LazyColumn(modifier = modifier) {
        itemsIndexed(items = heartRateListContent(),
            key = { index, value ->
                PlotsConstants.HOUR_INTERVALS[index]
            }) { index, heartRateValue ->
            val stringHeartRateValue = String.format("%.1f", heartRateValue)

            Log.d("MyCalculatedAge", "HeartRateList: ${getAgeCalculated()}")
            val myAge = if(getAgeCalculated()>0)(getAgeCalculated())else{41}
            val zoneResource = getHeartRateZones(heartRateValue, myAge)
            val zoneReadable = getReadableHeartRateZones(heartRateValue, myAge)

            RowHeartRate(
                valueHeartRate = "$stringHeartRateValue bpm",
                resource = zoneResource,
                readableCategory = zoneReadable,
                hourTime =  PlotsConstants.HOUR_INTERVALS[index]
            )
        }
    }

}

fun getHeartRateZones(heartRateValue: Double,
                      age: Int
): Int {
    val maxHeartRate = 220 - age
    val percentageOfMax = 100 * heartRateValue / maxHeartRate
    val resource:Int
    when {
        (percentageOfMax < 60.0 && 50.0 >= percentageOfMax) -> {
            resource = R.drawable.heart_rate_zone_1
        }

        (percentageOfMax < 70.0 && 60.0 >= percentageOfMax) -> {
            resource = R.drawable.heart_rate_zone_2
        }

        (percentageOfMax < 80.0 && 70.0 >= percentageOfMax) -> {
            resource = R.drawable.heart_rate_zone_3
        }

        (percentageOfMax < 90.0 && 80.0 >= percentageOfMax) -> {
            resource = R.drawable.heart_rate_zone_4
        }

        (percentageOfMax < 100.0 && 90.0 >= percentageOfMax) -> {
            resource = R.drawable.heart_rate_zone_5
        }

        else->{
            resource = R.drawable.heart_rate
        }
    }

    return resource
}

fun getReadableHeartRateZones(heartRateValue: Double, age: Int): String {
    val maxHeartRate = 220 - age
    val percentageOfMax = 100 * heartRateValue / maxHeartRate
    val resource:String
    when {
        (percentageOfMax < 60.0 && 50.0 >= percentageOfMax) -> {
            resource = "Very light"
        }

        (percentageOfMax < 70.0 && 60.0 >= percentageOfMax) -> {
            resource = "Light"
        }

        (percentageOfMax < 80.0 && 70.0 >= percentageOfMax) -> {
            resource = "Moderate"
        }

        (percentageOfMax < 90.0 && 80.0 >= percentageOfMax) -> {
            resource = "Hard"
        }

        (percentageOfMax < 100.0 && 90.0 >= percentageOfMax) -> {
            resource = "Maximum"
        }

        else->{
            resource = "No zone"
        }
    }

    return resource
}

@Composable
fun RowHeartRate(
    valueHeartRate: String,
    resource: Int,
    readableCategory: String,
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
                    .size(50.dp)
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

                Text(text = valueHeartRate, color = Color.White)
                Text(readableCategory, color = Color(0x9fffffff), textAlign = TextAlign.Center)
            }

        }

        Divider(modifier = Modifier.height(1.dp))


    }
}


@Composable
fun rememberLegendHeartRate() = verticalLegend(
    items = listOf(
        verticalLegendItem(
            icon = shapeComponent(Shapes.pillShape, chartHeartRateColorsPLot),
            label = textComponent(
                color = Color.White,
                textSize = legendItemLabelTextSize,
                typeface = Typeface.MONOSPACE,
            ),
            labelText = "Heart Rate bpm"
        )
    ),
    iconSize = legendItemIconSize,
    iconPadding = legendItemIconPaddingValue,
    spacing = legendItemSpacing,
    padding = legendPadding,
)

private const val COLOR_1_CODE = 0xffff5500
private val color1 = Color(COLOR_1_CODE)
val chartHeartRateColorsPLot = color1