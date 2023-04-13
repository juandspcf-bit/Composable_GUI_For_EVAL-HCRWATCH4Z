package com.icxcu.adsmartbandapp.screens.plotsFields

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextUtils
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.legend.verticalLegendItem
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.Animation
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

@Composable
internal fun ComposeBartCharts(
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
            modifier = modifier.fillMaxSize().background(Color.Black),

            startAxis = startAxis(
                label = axisLabelComponent(
                    color= Color.White,

                    ),
                maxLabelCount = 5,
                //axis = LineComponent(thicknessDp = 90f, color = 0x0000ff),
                valueFormatter = DecimalFormatAxisValueFormatter("#0", RoundingMode.FLOOR),
                tickLength = 2.dp
            ),

            bottomAxis = bottomAxis(
                sizeConstraint = Axis.SizeConstraint.TextWidth("sssss"),
                valueFormatter = axisValueFormatter,
                label = axisLabelComponent(
                    color= Color.White,
                    ellipsize = TextUtils.TruncateAt.START,
                    textSize = 15.sp,
                    lineCount = 3,
                    typeface = Typeface.SERIF,
                    textAlign = Paint.Align.CENTER
                ),
                labelRotationDegrees = -10f
            ),

            marker = rememberMarker(),
            legend = rememberLegend(),
            diffAnimationSpec = tween(
                durationMillis = 0,
            )


            )
    }
}

private val legendItemLabelTextSize = 12.sp
private val legendItemIconSize = 8.dp
private val legendItemIconPaddingValue = 10.dp
private val legendItemSpacing = 4.dp
private val legendTopPaddingValue = 8.dp
private val legendPadding = dimensionsOf(top = legendTopPaddingValue)
@Composable
private fun rememberLegend() = verticalLegend(
    items = chartColors.mapIndexed { index, chartColor ->
        verticalLegendItem(
            icon = shapeComponent(Shapes.pillShape, chartColor),
            label = textComponent(
                color = Color.White,
                textSize = legendItemLabelTextSize,
                typeface = Typeface.MONOSPACE,
            ),
            labelText = "Steps",
        )
    },
    iconSize = legendItemIconSize,
    iconPadding = legendItemIconPaddingValue,
    spacing = legendItemSpacing,
    padding = legendPadding,
)

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
                val hour = "${Duration.ofHours(0).plusMinutes(30L * toLong()).toHours()}"
                val time = hour + if ((toInt() + 1) % 2 == 0) {
                    ":30"
                } else {
                    ":00"
                }
                time
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

