package com.icxcu.adsmartbandapp.screens.plotsFields

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.ui.theme.rememberChartStyle
import com.icxcu.adsmartbandapp.ui.theme.rememberMarker
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.Axis
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.axis.formatter.PercentageFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.math.RoundingMode
import java.time.Duration
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsPlots(
    dataSteps: List<Int>,
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
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            Box(
                Modifier
                    .padding(padding)
                    .fillMaxSize(), contentAlignment = Alignment.TopCenter
            ) {
                ContentPlots(dataSteps)
            }

        },


        )
}


@Composable
fun ContentPlots(dataSteps: List<Int>) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (plot, divider,tabRow,list) = createRefs()
        val guideH3 = createGuidelineFromTop(fraction = 0.4f)


        val mapIndexed2 = dataSteps.mapIndexed { index, y ->
            var entry = EntryHour(
                Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                index.toFloat(), y.toFloat()
            )
            Log.d("Go", "MainScreen: ${entry.duration.toHours()}")
            entry
        }
        ComposeChart2(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(shape = RoundedCornerShape(size = 25.dp))
                .background(
                    Color(0xFFF6F4F8)
                )
                .constrainAs(plot) {
                    top.linkTo(parent.top)
                    bottom.linkTo(guideH3)
                    linkTo(start = parent.start, end = parent.end)
                    height = Dimension.fillToConstraints
                }, chartEntryModelProducer = ChartEntryModelProducer(mapIndexed2)
        )

        Divider(modifier = Modifier
            .constrainAs(divider) {
                top.linkTo(guideH3)
                //bottom.linkTo(list.top)
                linkTo(start = parent.start, end = parent.end)
            }
            .height(2.dp))


        var state by remember { mutableStateOf(0) }
        val titles = listOf("Steps", "Distance", "Calories")

        TabRow(selectedTabIndex = state, modifier = Modifier
            .padding(top = 5.dp, bottom = 5.dp, start = 50.dp, end = 50.dp)
            .constrainAs(tabRow) {
                top.linkTo(divider.bottom)
                linkTo(start = parent.start, end = parent.end)
                height = Dimension.fillToConstraints

            }
            .fillMaxWidth()) {

            titles.forEachIndexed { index, title ->
                MyTab(title = title, onClick = { state = index }, selected = (index == state))
            }
        }

        if(state==0){
            StepsList(dataSteps = dataSteps, modifier = Modifier.constrainAs(list) {
                top.linkTo(tabRow.bottom)
                bottom.linkTo(parent.bottom)
                linkTo(start = parent.start, end = parent.end)
                height = Dimension.fillToConstraints

            }.fillMaxSize())
        }



    }
}

@Composable
fun MyTab(title: String, onClick: () -> Unit, selected: Boolean) {
    Tab(selected, onClick) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

    }
}


@Composable
internal fun ComposeChart2(
    chartEntryModelProducer: ChartEntryModelProducer,
    modifier: Modifier = Modifier
) {
    val thresholdLine = rememberThresholdLine()
    ProvideChartStyle(rememberChartStyle(chartColors)) {
        val defaultColumns = currentChartStyle.columnChart.columns
        Chart(
            chart = columnChart(
                spacing = 1.dp,
                columns = remember(defaultColumns) {
                    defaultColumns.map { defaultColumn ->
                        LineComponent(defaultColumn.color, COLUMN_WIDTH_DP, defaultColumn.shape)
                    }
                },
                decorations = remember(thresholdLine) { listOf(thresholdLine) },
            ),
            chartModelProducer = chartEntryModelProducer,
            modifier = modifier,
            //.height(512.dp)
            //.padding(start = 10.dp, end = 10.dp),
            startAxis = startAxis(
                maxLabelCount = 5,
                //axis = LineComponent(thicknessDp = 90f, color = 0x0000ff),
                valueFormatter = DecimalFormatAxisValueFormatter("#0", RoundingMode.FLOOR),
                tickLength = 2.dp
            ),

            bottomAxis = bottomAxis(
                sizeConstraint = Axis.SizeConstraint.TextWidth("sssss"),
                valueFormatter = axisValueFormatter,
                label = axisLabelComponent(
                    ellipsize = TextUtils.TruncateAt.START,
                    textSize = 15.sp,
                    lineCount = 3,
                    typeface = Typeface.SERIF,
                    textAlign = Paint.Align.CENTER
                ),
                labelRotationDegrees = -10f
            ),

            marker = rememberMarker(),


            )
    }
}

@Composable
private fun rememberThresholdLine(): ThresholdLine {
    val line = shapeComponent(strokeWidth = thresholdLineThickness, strokeColor = color2)
    val label = textComponent(
        color = Color.Black,
        background = shapeComponent(Shapes.pillShape, color2),
        padding = thresholdLineLabelPadding,
        margins = thresholdLineLabelMargins,
        typeface = Typeface.MONOSPACE,
    )
    return remember(line, label) {
        ThresholdLine(
            thresholdValue = THRESHOLD_LINE_VALUE,
            lineComponent = line,
            labelComponent = label
        )
    }
}


class EntryHour(
    val duration: Duration,
    override val x: Float,
    override val y: Float,
) : ChartEntry {
    override fun withY(y: Float) = EntryHour(duration, x, y)
}


val axisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, chartValues ->
    (chartValues.chartEntryModel.entries.first().getOrNull(value.toInt()) as? EntryHour)
        ?.x
        ?.run {
            if (toInt() % 6L == 0L) {
                var hour = "${Duration.ofHours(0).plusMinutes(30L * toLong()).toHours()}"
                val time = hour + if ((toInt() + 1) % 2 == 0) {
                    ":30"
                } else {
                    ":00"
                }
                time
                //"${Duration.ofHours(0).plusMinutes(30L*toLong()).toHours()}"
            } else {
                " "
            }
        }
        .orEmpty()
}


private const val COLOR_1_CODE = 0xffff5500
private const val COLOR_2_CODE = 0xffd3d826
private const val PERSISTENT_MARKER_X = 1000f

private const val COLUMN_WIDTH_DP = 5f
private const val THRESHOLD_LINE_VALUE = 13f
private const val START_AXIS_LABEL_COUNT = 5
private const val BOTTOM_AXIS_TICK_OFFSET = 0
private const val BOTTOM_AXIS_TICK_SPACING = 1

private val color1 = Color(COLOR_1_CODE)
private val color2 = Color(COLOR_2_CODE)
private val chartColors = listOf(color1)
private val thresholdLineLabelMarginValue = 4.dp
private val thresholdLineLabelHorizontalPaddingValue = 8.dp
private val thresholdLineLabelVerticalPaddingValue = 2.dp
private val thresholdLineThickness = 2.dp
private val thresholdLineLabelPadding =
    dimensionsOf(thresholdLineLabelHorizontalPaddingValue, thresholdLineLabelVerticalPaddingValue)
private val thresholdLineLabelMargins = dimensionsOf(thresholdLineLabelMarginValue)
private val startAxisValueFormatter =
    PercentageFormatAxisValueFormatter<AxisPosition.Vertical.Start>()
private val bottomAxisTickPosition =
    HorizontalAxis.TickPosition.Center(BOTTOM_AXIS_TICK_OFFSET, BOTTOM_AXIS_TICK_SPACING)


@Composable
fun StepsList(dataSteps: List<Int>, modifier: Modifier = Modifier) {

    LazyColumn(modifier = modifier) {
        items(dataSteps) {
            RowSteps(valueSteps = it.toString())
        }
    }

}

@Composable
fun RowSteps(valueSteps:String){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 2.dp, bottom = 2.dp)) {
        val (hour, icon, value) = createRefs()
        val guideHourIcon = createGuidelineFromStart(fraction = 0.25f)
        val gideIconValue = createGuidelineFromStart(fraction = 0.75f)

        Text(text = "Hour", modifier = Modifier.constrainAs(hour){
            linkTo(start=parent.start, end=guideHourIcon)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        })

        Text(text = valueSteps, modifier = Modifier.constrainAs(value){
            linkTo(start=gideIconValue, end=parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        })



    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StepsPlotsPreview() {

    var stepValue = listOf(
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
    StepsPlots(dataSteps = stepValue) {}
}