package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.data.MockData
import com.icxcu.adsmartbandapp.screens.plotsFields.DatePickerDialogSample
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
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Blood Pressure for\n${getSelectedDay()}",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
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


                BloodPressureContent(
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
fun BloodPressureContent(
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        ) {

            val chartEntryModel = ChartEntryModelProducer(mapSystolic, mapDiastolic)
            MyComposePlotChart(
                chartEntryModel,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(start= 10.dp, end = 10.dp, bottom = 15.dp),
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

        BloodPressureLazyList(
            systolicListContent,
            diastolicListContent,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
        )

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
    BloodPressureLayoutScaffold(
        { MockData.valuesToday.systolicList },
        { MockData.valuesToday.diastolicList },
        getSelectedDay = {"24/10/1981"},
    stateShowDialogDatePickerSetter={},
    stateShowDialogDatePickerValue = { false },
    stateMiliSecondsDateDialogDatePickerSetter= {},
    navLambda ={},
    )


}