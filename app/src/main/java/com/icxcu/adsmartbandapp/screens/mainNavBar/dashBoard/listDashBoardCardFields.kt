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
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.DashBoardCard
import com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard.DashBoardCardData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashBoardScreen(
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
            iconPadding = 0.dp,
            heightCard = 300.dp,
            widthCard = 500.dp,
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

    val sumDistanceList = dayDateValuesReadFromSW().distanceList.sum()
    val stringSumDistanceList = String.format("%.1f", sumDistanceList)

    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Distance",
            text = stringSumDistanceList,
            fieldPlural = "Km",
            resource = R.drawable.measure_distance,
            iconPadding = 15.dp,
            heightCard = 143.dp,
            widthCard = 250.dp

        )
    )

    val sumCaloriesList = dayDateValuesReadFromSW().caloriesList.sum()
    val stringSumCaloriesList = String.format("%.1f", sumCaloriesList)

    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Calories",
            text = stringSumCaloriesList,
            fieldPlural = "KCal",
            resource = R.drawable.calories,
            iconPadding = 15.dp,
            heightCard = 143.dp,
            widthCard = 250.dp
        )
    )

    val avgHeartRate = dayDateValuesReadFromSW().heartRateList.average()
    val stringAvgHeartRate = String.format("%.1f", avgHeartRate)

    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Heart Rate",
            text = stringAvgHeartRate,
            fieldPlural = "bpm",
            resource = R.drawable.heart_rate,
            iconPadding = 15.dp,
            heightCard = 143.dp,
            widthCard = 250.dp,
            callBack = { navMainController.navigate(Routes.HeartRatePlot.route) },
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
            iconPadding = 15.dp,
            heightCard = 143.dp,
            widthCard = 500.dp,
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
            iconPadding = 15.dp,
            heightCard = 143.dp,
            widthCard = 250.dp,
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
            iconPadding = 15.dp,
            heightCard = 143.dp,
            widthCard = 500.dp,
            verticalChainData = false
        )
    )

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff1d2a35)),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalItemSpacing = 0.dp
    ) {
        items(
            listOf,
            key = { it.title }
        ) {
            DashBoardCard(
                modifier = it.modifier.clickable {
                    it.callBack()
                },
                title = it.title,
                text = it.text,
                fieldPlural = it.fieldPlural,
                resource = it.resource,
                iconPadding = it.iconPadding,
                heightCard = it.heightCard,
                widthCard = it.widthCard,
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
    DashBoardScreen(
        dayDateValuesReadFromSW = {
            MockData.valuesToday
        },
        navMainController = rememberNavController()
    )
}