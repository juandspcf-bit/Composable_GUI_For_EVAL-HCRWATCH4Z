package com.icxcu.adsmartbandapp.screens.mainNavBar.dashBoard

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
import com.icxcu.adsmartbandapp.screens.BloodPressureNestedRoute
import com.icxcu.adsmartbandapp.screens.HeartRateNestedRoute
import com.icxcu.adsmartbandapp.screens.PhysicalActivityNestedRoute
import com.icxcu.adsmartbandapp.screens.additionalWidgets.ArcCompose

@Composable
fun DashBoardScreen(
    dayValues: () -> Values,
    navMainController: NavHostController
) {

    val listOf = mutableListOf<DashBoardCardData>()
    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Steps",
            text = dayValues().stepList.sum().toString(),
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
            callBack = { navMainController.navigate(PhysicalActivityNestedRoute.PhysicalActivityMainRoute().route) },
            guidelineFromBottomFraction = 0.35f
        )
    )

    val sumDistanceList = dayValues().distanceList.sum()
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

    val sumCaloriesList = dayValues().caloriesList.sum()
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

    val avgHeartRate = dayValues().heartRateList.average()
    val stringAvgHeartRate = String.format("%.1f", avgHeartRate)

    listOf.add(
        DashBoardCardData(
            modifier = Modifier
                .padding(5.dp),
            title = "Heart Rate",
            text = stringAvgHeartRate,
            fieldPlural = "bpm",
            resource = R.drawable.heart,
            iconPadding = 15.dp,
            heightCard = 143.dp,
            widthCard = 250.dp,
            callBack = { navMainController.navigate(HeartRateNestedRoute.HeartRateMainRoute().route) },
        )
    )


    val bPHighMaxValue = dayValues().systolicList.max()
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
            callBack = { navMainController.navigate(BloodPressureNestedRoute.BloodPressureMainRoute().route) },
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
                verticalChainData = it.verticalChainData,
                isWithIconTitle = it.isWithIconTitle,
                resourceIconTitle = it.resourceIconTitle,
                heightCard = it.heightCard,
                widthCard = it.widthCard
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListDashBoardCardFieldsPreview() {
    DashBoardScreen(
        dayValues = {
            MockData.valuesToday
        },
        navMainController = rememberNavController()
    )
}