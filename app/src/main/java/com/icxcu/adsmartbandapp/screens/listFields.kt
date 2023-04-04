package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.icxcu.adsmartbandapp.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListFields(
    dataSteps: List<Int>,
    navMainController: NavHostController
) {

    val listOf = mutableListOf<GenericCardData>()
    listOf.add(
        GenericCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Steps",
            text = dataSteps.sum().toString(),
            fieldPlural = "Steps",
            resource = R.drawable.walk,
            iconPadding = 20.dp,
            size = 400.dp,
            callBack = { navMainController.navigate(Routes.StepsPlots.route) }
        )
    )
    listOf.add(
        GenericCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Distance",
            text = "10000",
            fieldPlural = "Km",
            resource = R.drawable.measure_distance,
            iconPadding = 20.dp,
            size = 195.dp
        )
    )

    listOf.add(
        GenericCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Calories",
            text = "10000",
            fieldPlural = "KCal",
            resource = R.drawable.calories,
            iconPadding = 20.dp,
            size = 195.dp
        )
    )

    listOf.add(
        GenericCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Blood Pressure",
            text = "130",
            fieldPlural = "mmHg",
            resource = R.drawable.blood_pressure_gauge,
            iconPadding = 20.dp,
            size = 200.dp
        )
    )

    listOf.add(
        GenericCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "SpO2",
            text = "95",
            fieldPlural = "%",
            resource = R.drawable.oxygen_saturation,
            iconPadding = 20.dp,
            size = 200.dp,
            verticalChainData = false
        )
    )

    listOf.add(
        GenericCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Temperature",
            text = "30",
            fieldPlural = "°C",
            resource = R.drawable.thermometer,
            iconPadding = 20.dp,
            size = 200.dp,
            verticalChainData = false
        )
    )

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff1d2a35)),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalItemSpacing = 0.dp
    ) {
        items(listOf) {
            GenericCard(
                modifier = it.modifier.clickable {
                    it.callBack()
                },
                title = it.title,
                text = it.text,
                fieldPlural = it.fieldPlural,
                resource = it.resource,
                iconPadding = it.iconPadding,
                size = it.size,
                verticalChainData = it.verticalChainData,

                )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ListFieldsPreview() {

}