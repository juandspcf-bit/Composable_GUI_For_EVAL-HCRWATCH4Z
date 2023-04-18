package com.icxcu.adsmartbandapp.screens.plotsFields

import androidx.compose.runtime.Composable
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