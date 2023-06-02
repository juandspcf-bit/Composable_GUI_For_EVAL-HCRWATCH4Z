package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

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

@Composable
fun StatisticsBloodPressure(
    systolicListContent: () -> List<Double>,
    modifier: Modifier = Modifier
) {
    val filteredSystolicListContent = systolicListContent().filter { it > 0.0 }
    if (filteredSystolicListContent.isEmpty()) return

    val maxValueSystolic = filteredSystolicListContent.max()
    val maxValueSystolicValue = String.format("%.1f mmHg", maxValueSystolic)
    val hourMax = findIndex(maxValueSystolic, systolicListContent())


    val minValueSystolic = filteredSystolicListContent.min()
    val minValueSystolicValue = String.format("%.1f mmHg", minValueSystolic)
    val hourMin = findIndex(minValueSystolic, systolicListContent())

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