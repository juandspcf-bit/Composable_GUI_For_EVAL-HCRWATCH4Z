package com.icxcu.adsmartbandapp.screens.plotsFields.heartRate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure.findFirstsIndexWithValueInDataList

@Composable
fun StatisticsHeartRate(heartRateListContent: () -> List<Double>, modifier: Modifier) {
    val filteredValueSystolic = heartRateListContent().filter { it > 0.0 }
    if (filteredValueSystolic.isEmpty()) return

    val maxValueSystolic = filteredValueSystolic.max()
    val maxValueSystolicValue = String.format("%.1f bpm", maxValueSystolic)
    val hourMax = findFirstsIndexWithValueInDataList(maxValueSystolic, heartRateListContent())


    val minValueSystolic = filteredValueSystolic.min()
    val minValueSystolicValue = String.format("%.1f bpm", minValueSystolic)
    val hourMin = findFirstsIndexWithValueInDataList(minValueSystolic, heartRateListContent())

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