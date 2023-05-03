package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.axisValueFormatter

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
import com.patrykandpatrick.vico.core.legend.VerticalLegend
import java.math.RoundingMode

@Composable
fun MyComposePlotChart(
    chartEntryModel: ChartEntryModelProducer,
    modifier: Modifier = Modifier,
    legend: VerticalLegend
) {

    ProvideChartStyle(rememberChartStyle(chartColorsPLot)) {
        val defaultLines = currentChartStyle.lineChart.lines
        Chart(
            modifier = modifier,
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
                    color = Color.White,

                    ),
                maxLabelCount = 2,
                valueFormatter = DecimalFormatAxisValueFormatter("#0", RoundingMode.FLOOR),
                tickLength = 3.dp
            ),

            bottomAxis = bottomAxis(
                tickPosition = HorizontalAxis.TickPosition.Center(1, 5),
                sizeConstraint = Axis.SizeConstraint.TextWidth("sssss"),
                valueFormatter = axisValueFormatter,
                label = axisLabelComponent(

                    color = Color.White,
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
            legend = legend,
        )
    }

}




private const val COLOR_1_CODE = 0xffff5500
private const val COLOR_2_CODE = 0xffd3d826
private val color1 = Color(COLOR_1_CODE)
private val color2 = Color(COLOR_2_CODE)
val chartColorsPLot = listOf(color1, color2)