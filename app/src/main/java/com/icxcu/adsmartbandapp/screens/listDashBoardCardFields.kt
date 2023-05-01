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
import androidx.navigation.compose.rememberNavController
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.data.MockData
import com.icxcu.adsmartbandapp.repositories.Values
import com.icxcu.adsmartbandapp.screens.additionalWidgets.ArcCompose

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListDashBoardCardFields(
    dayDateValuesReadFromSW: () -> Values,
    navMainController: NavHostController
) {

    val listOf = mutableListOf<DashBoardCardData>()
    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Steps",
            text = dayDateValuesReadFromSW().stepList.sum().toString(),
            fieldPlural = "Steps",
            resource = R.drawable.walk,
            iconPadding = 5.dp,
            size = 280.dp,
            isWithIconTitle = true,
            resourceIconTitle = {
                ArcCompose(
                    stepsMade = 9000,
                    stepsGoal = 10000,
                    sizeContainer = 110.dp,
                    radius = 60.dp
                )
            },
            callBack = { navMainController.navigate(Routes.StepsPlots.route) },
            guidelineFromBottomFraction = 0.35f
        )
    )
    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Distance",
            text = "10000",
            fieldPlural = "Km",
            resource = R.drawable.measure_distance,
            iconPadding = 5.dp,
            size = 140.dp
        )
    )

    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Calories",
            text = "10000",
            fieldPlural = "KCal",
            resource = R.drawable.calories,
            iconPadding = 5.dp,
            size = 140.dp
        )
    )


    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Heart Rate",
            text = "96",
            fieldPlural = "bpm",
            resource = R.drawable.heart_rate,
            iconPadding = 5.dp,
            size = 140.dp,
            callBack = {  },
        )
    )


    val bPHighMaxValue = dayDateValuesReadFromSW().systolicList.max()
    val stringValueBP = String.format("%.1f", bPHighMaxValue)

    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Blood Pressure",
            text = stringValueBP,
            fieldPlural = "mmHg",
            resource = R.drawable.blood_pressure_gauge,
            iconPadding = 5.dp,
            size = 140.dp,
            callBack = { navMainController.navigate(Routes.BloodPressurePlots.route) },
        )
    )

    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "SpO2",
            text = "95",
            fieldPlural = "%",
            resource = R.drawable.oxygen_saturation,
            iconPadding = 5.dp,
            size = 140.dp,
            verticalChainData = false
        )
    )

    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Temperature",
            text = "30",
            fieldPlural = "°C",
            resource = R.drawable.thermometer,
            iconPadding = 5.dp,
            size = 140.dp,
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
            DashBoardCard(
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
                isWithIconTitle = it.isWithIconTitle,
                resourceIconTitle = it.resourceIconTitle,
                guidelineFromBottomFraction = it.guidelineFromBottomFraction
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListDashBoardCardFieldsPreview() {
    ListDashBoardCardFields(
        dayDateValuesReadFromSW = {
            MockData.valuesToday
        },
        navMainController = rememberNavController()
    )
}