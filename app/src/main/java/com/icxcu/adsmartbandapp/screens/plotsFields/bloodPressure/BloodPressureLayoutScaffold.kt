package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.screens.plotsFields.DatePickerDialogSample
import com.icxcu.adsmartbandapp.screens.plotsFields.getHours
import com.icxcu.adsmartbandapp.screens.plotsFields.getIntervals
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.EntryHour
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.axisValueFormatter
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.chartColors
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.legendItemIconPaddingValue
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.legendItemIconSize
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.legendItemLabelTextSize
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.legendItemSpacing
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.legendPadding
import com.icxcu.adsmartbandapp.ui.theme.rememberChartStyle
import com.icxcu.adsmartbandapp.ui.theme.rememberMarker
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.legend.verticalLegendItem
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.Axis
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.math.RoundingMode
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
    val stateMiliSecondsDateDialogDatePickerSetter:(Long) -> Unit = { value ->
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
                    DatePickerDialogSample(stateShowDialogDatePickerSetter,
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
        val (plot, divider, list) = createRefs()
        val guideH3 = createGuidelineFromTop(fraction = 0.4f)


        val mapSystolic = systolicListContent().mapIndexed { index, y ->
            val entry =EntryHour(Duration.ofHours(24).minusMinutes(30L*index.toLong()),
                index.toFloat(), y.toFloat())
            entry
        }
        val mapDiastolic = diastolicListContent().mapIndexed { index, y ->
            val entry =EntryHour(Duration.ofHours(24).minusMinutes(30L*index.toLong()),
                index.toFloat(), y.toFloat())
            entry
        }



        Box(modifier = Modifier
            /*            .padding(start = 10.dp, end = 10.dp)
                        .clip(shape = RoundedCornerShape(size = 25.dp))*/
            .background(
                Color(0xFFE57373)
            )
            .constrainAs(plot) {
                top.linkTo(parent.top)
                bottom.linkTo(guideH3)
                linkTo(start = parent.start, end = parent.end)
                height = Dimension.fillToConstraints
            }
        ) {

            val chartEntryModel = ChartEntryModelProducer(mapSystolic, mapDiastolic)


            ProvideChartStyle(rememberChartStyle(chartColors)) {
                val defaultLines = currentChartStyle.lineChart.lines
                Chart(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(0.dp),
                    marker = rememberMarker(),
                    chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false),
                    chart = lineChart(
                        remember(defaultLines) {
                            defaultLines.map { defaultLine -> defaultLine.copy(lineBackgroundShader = null) }
                        },

                        ),
                    model = chartEntryModel.getModel(),
                    startAxis = startAxis(
                        label = axisLabelComponent(
                            horizontalMargin = 0.dp,
                            horizontalPadding = 0.dp,
                            textSize = 15.sp,
                            color= Color.White,

                            ),
                        maxLabelCount = 2,
                        valueFormatter = DecimalFormatAxisValueFormatter("#0", RoundingMode.FLOOR),
                        tickLength = 3.dp
                    ),

                    bottomAxis = bottomAxis(
                        tickPosition= HorizontalAxis.TickPosition.Center(1,5),
                        sizeConstraint = Axis.SizeConstraint.TextWidth("sssss"),
                        valueFormatter = axisValueFormatter,
                        label = axisLabelComponent(

                            color= Color.White,
                            ellipsize = TextUtils.TruncateAt.MARQUEE,
                            textSize = 15.sp,
                            lineCount = 3,
                            typeface = Typeface.SERIF,
                            textAlign = Paint.Align.CENTER,
                            horizontalPadding = 0.dp,
                            horizontalMargin = 100.dp,
                            verticalMargin = 10.dp,
                        ),
                        labelRotationDegrees = 90f
                    ),
                    legend = rememberLegendBloodPressure(),
                )
            }
        }

        Divider(modifier = Modifier
            .constrainAs(divider) {
                top.linkTo(guideH3)
                bottom.linkTo(list.top)
                linkTo(start = parent.start, end = parent.end)
                height = Dimension.wrapContent
            }
            .height(2.dp))

        BloodPressureSList(
            systolicListContent,
            diastolicListContent,
            //.padding(top = 20.dp, bottom = 20.dp, start = 50.dp, end = 50.dp),
            modifierList = Modifier
                .constrainAs(list) {
                    top.linkTo(divider.bottom)
                    bottom.linkTo(parent.bottom)
                    linkTo(start = parent.start, end = parent.end)
                    height = Dimension.fillToConstraints
                }
                .fillMaxSize())

    }
}

@Composable
fun BloodPressureSList(
    systolicListContent: () -> List<Double>,
    diastolicListContent: () -> List<Double>,
    modifierList: Modifier
) {

    val systolicList = {
        systolicListContent()
    }

    val diastolicList = {
        diastolicListContent()
    }


    Box(
        modifier = modifierList
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

            val stringSystolicValue= String.format("%.1f", systolicValue)
            val stringDiastolicValue= String.format("%.1f", diastolicValue)
            RowBloodPressure(
                valueSteps = "$stringSystolicValue, $stringDiastolicValue",
                hourTime = getIntervals(index, hourList)
            )

        }
    }
}

@Composable
fun RowBloodPressure(
    valueSteps: String,
    resource: Int = R.drawable.blood_pressure_gauge,
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


@Composable
fun rememberLegendBloodPressure() = verticalLegend(
    items = chartColors.mapIndexed { index, chartColor ->
        verticalLegendItem(
            icon = shapeComponent(Shapes.pillShape, chartColor),
            label = textComponent(
                color = Color.White,
                textSize = legendItemLabelTextSize,
                typeface = Typeface.MONOSPACE,
            ),
            labelText = if(index==0){"Systolic"}else{"Diastolic"},
        )
    },
    iconSize = legendItemIconSize,
    iconPadding = legendItemIconPaddingValue,
    spacing = legendItemSpacing,
    padding = legendPadding,
)