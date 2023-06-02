package com.icxcu.adsmartbandapp.screens.plotsFields.heartRate

import android.graphics.Typeface
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.EntryHour
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

                HeartRateInfoContent(
                    heartRateList,
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
fun HeartRateInfoContent(
    heartRateList: () -> List<Double>,
    getAgeCalculated: () -> Int,
) {
    Column(
        modifier = Modifier
            .background(Color(0xff1d2a35))
            .fillMaxSize()
    ) {

        val mapHeartRate = heartRateList().mapIndexed { index, y ->
            val entry = EntryHour(
                Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                index.toFloat(), y.toFloat()
            )
            entry
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
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

        Divider(modifier = Modifier.height(2.dp))

        StatisticsHeartRate(
            heartRateListContent = heartRateList,
            modifier = Modifier
                .background(Color(0x7FFF5722))
                .fillMaxWidth()
                .width(50.dp)
        )

        Divider(modifier = Modifier.height(2.dp))

        HeartRateLazyList(
            heartRateList,
            getAgeCalculated,
        )

    }
}




fun getHeartRateZones(
    heartRateValue: Double,
    age: Int
): Int {
    val maxHeartRate = 220 - age
    val percentageOfMax = 100 * heartRateValue / maxHeartRate
    val resource: Int
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

        else -> {
            resource = R.drawable.heart_rate
        }
    }

    return resource
}

fun getReadableHeartRateZones(heartRateValue: Double, age: Int): String {
    val maxHeartRate = 220 - age
    val percentageOfMax = 100 * heartRateValue / maxHeartRate
    val resource: String
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

        else -> {
            resource = "No zone"
        }
    }

    return resource
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