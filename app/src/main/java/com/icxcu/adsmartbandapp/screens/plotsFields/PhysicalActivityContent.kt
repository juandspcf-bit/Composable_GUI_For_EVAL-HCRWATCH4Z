package com.icxcu.adsmartbandapp.screens.plotsFields

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R

@Composable
fun PhysicalActivityInfoContent(
    stepsListContent: () -> List<Int>,
    distanceListContent: () -> List<Double>,
    caloriesListContent: () -> List<Double>,
) {
    ConstraintLayout(
        modifier = Modifier
            .background(Color(0xff1d2a35))
            .fillMaxSize()
    ) {
        val (plot, divider, tabRow, list) = createRefs()
        val guideH3 = createGuidelineFromTop(fraction = 0.4f)


        val stepList = {
            stepsListContent()
        }

        val distanceList = {
            distanceListContent()
        }

        val caloriesList = {
            caloriesListContent()
        }

        Box(modifier = Modifier
            .background(
                Color(0xfff5f5f7)
            )
            .constrainAs(plot) {
                top.linkTo(parent.top)
                bottom.linkTo(guideH3)
                linkTo(start = parent.start, end = parent.end)
                height = Dimension.fillToConstraints
            }
        ) {

            MyComposeBarChart(stepList)

        }

        Divider(modifier = Modifier
            .constrainAs(divider) {
                top.linkTo(guideH3)
                //bottom.linkTo(list.top)
                linkTo(start = parent.start, end = parent.end)
            }
            .height(2.dp))

        ListSelector(
            stepList,
            distanceList,
            caloriesList,
            modifierTabs = Modifier
                .constrainAs(tabRow) {
                    top.linkTo(divider.bottom)
                    linkTo(start = parent.start, end = parent.end)
                    height = Dimension.fillToConstraints
                }//.padding(top = 20.dp, bottom = 20.dp, start = 50.dp, end = 50.dp),
            , modifierList = Modifier
                .constrainAs(list) {
                    top.linkTo(tabRow.bottom)
                    bottom.linkTo(parent.bottom)
                    linkTo(start = parent.start, end = parent.end)
                    height = Dimension.fillToConstraints
                }
                .fillMaxSize())
    }
}

@Composable
fun MyTab(title: String, onClick: () -> Unit, selected: Boolean) {
    Tab(
        selected,
        onClick,
        selectedContentColor = Color.Red,
        unselectedContentColor = Color.Green,
        modifier = Modifier.fillMaxHeight()
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally),

            )
    }
}


@Composable
fun ListSelector(
    stepsListContent: () -> List<Int>,
    distanceListContent: () -> List<Double>,
    caloriesListContent: () -> List<Double>,
    modifierTabs: Modifier,
    modifierList: Modifier
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Steps", "Distance", "Calories")

    val stepList = {
        stepsListContent()
    }

    val distanceList = {
        distanceListContent()
    }

    val caloriesList = {
        caloriesListContent()
    }

    TabRow(
        selectedTabIndex = state,
        modifier = modifierTabs.height(52.dp),
        containerColor = Color.DarkGray,
        divider = {
            Divider(modifier = Modifier.fillMaxWidth(), color = Color(0xFF855454))
        },
    ) {

        titles.forEachIndexed { index, title ->
            MyTab(title = title, onClick = { state = index }, selected = (index == state))
        }
    }

    Box(
        modifier = modifierList
    ) {
        val hoursList = getHours()
        when (state) {
            0 -> {
                StepsList(
                    stepsListContent = stepList,
                    hoursList,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            1 -> {
                DistanceList(
                    distanceListContent = distanceList,
                    hoursList,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            2 -> {
                CaloriesList(
                    caloriesListContent = caloriesList,
                    hoursList,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

    }
}


@Composable
fun StepsList(
    stepsListContent: () -> List<Int>,
    hourList: List<String>,
    modifier: Modifier = Modifier
) {
    val stepList = {
        stepsListContent()
    }
    LazyColumn(modifier = modifier) {
        itemsIndexed(stepList()) { index, item ->
            RowSteps(
                valueSteps = "$item Steps",
                resource = R.drawable.runner_for_lazy_colum_list,
                hourTime = getIntervals(index, hourList)
            )
        }
    }
}

@Composable
fun DistanceList(
    distanceListContent:()-> List<Double>,
    hourList: List<String>,
    modifier: Modifier = Modifier
) {
    val distanceList={
        distanceListContent()
    }
    LazyColumn(modifier = modifier) {
        itemsIndexed(distanceList()) { index, item ->
            RowSteps(
                valueSteps = "$item Kms",
                resource = R.drawable.distance_for_lazy_list,
                hourTime = getIntervals(index, hourList)
            )
        }
    }
}

@Composable
fun CaloriesList(
    caloriesListContent: () -> List<Double>,
    hourList: List<String>,
    modifier: Modifier = Modifier
) {
    val caloriesList={
        caloriesListContent()
    }

    LazyColumn(modifier = modifier) {
        itemsIndexed(caloriesList()) { index, item ->
            RowSteps(
                valueSteps = "$item Kcal",
                resource = R.drawable.calories_for_lazy_list,
                hourTime = getIntervals(index, hourList)
            )
        }
    }
}

@Composable
fun RowSteps(
    valueSteps: String,
    resource: Int = R.drawable.ic_launcher_foreground,
    hourTime: String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxSize()

        ) {
            val (hour, icon, value) = createRefs()
            val guideHourIcon = createGuidelineFromStart(fraction = 0.25f)
            val gideIconValue = createGuidelineFromStart(fraction = 0.75f)

            Text(text = hourTime, color = Color.White, modifier = Modifier.constrainAs(hour) {
                linkTo(start = parent.start, end = guideHourIcon)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            })

            Image(
                painter = painterResource(resource),
                contentScale = ContentScale.FillHeight,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(guideHourIcon)
                        end.linkTo(gideIconValue)
                    }
                    .size(50.dp)

            )

            Text(text = valueSteps, color = Color.White, modifier = Modifier.constrainAs(value) {
                linkTo(start = gideIconValue, end = parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)

            })


        }

        Divider(modifier = Modifier.height(1.dp))


    }

}
