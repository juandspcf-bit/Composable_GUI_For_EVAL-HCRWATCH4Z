package com.icxcu.adsmartbandapp.screens.plotsFields

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.icxcu.adsmartbandapp.data.MockData
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.time.Duration

@Composable
fun MyComposeBarChart(stepsList:()-> List<Int>){
    val stepsEntries = stepsList().mapIndexed { index, y ->
        val entry = EntryHour(
            Duration.ofHours(24).minusMinutes(30L * index.toLong()),
            index.toFloat(), y.toFloat()
        )
        entry
    }
    ComposeBartCharts(chartEntryModelProducer = ChartEntryModelProducer(stepsEntries))

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyComposeBarChartPreview() {
    val stepsList = {
        MockData.values.stepList
    }

    MyComposeBarChart(stepsList)
}