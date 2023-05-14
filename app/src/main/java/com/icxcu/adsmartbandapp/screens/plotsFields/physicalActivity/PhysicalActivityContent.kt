package com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity

import android.graphics.Typeface
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
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.legend.verticalLegendItem
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.time.Duration

@Composable
fun PhysicalActivityContent(
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

            val stepsEntries = stepsListContent().mapIndexed { index, y ->
                val entry = EntryHour(
                    Duration.ofHours(24).minusMinutes(30L * index.toLong()),
                    index.toFloat(), y.toFloat()
                )
                entry
            }
            val chartEntryModel = ChartEntryModelProducer(stepsEntries)

            MyComposeBarChart(
                chartEntryModel = chartEntryModel,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(bottom = 15.dp),
                legend = rememberLegendPhysicalActivity()
            )

        }

        Divider(modifier = Modifier
            .constrainAs(divider) {
                top.linkTo(guideH3)
                //bottom.linkTo(list.top)
                linkTo(start = parent.start, end = parent.end)
                height = Dimension.wrapContent
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
fun MyPhysicalActivityTab(title: String, onClick: () -> Unit, selected: Boolean) {
    Tab(
        selected,
        onClick,
        selectedContentColor = Color(0xFFFFF176),
        modifier = Modifier
            .fillMaxHeight()
            .height(52.dp)
    ) {
        Text(
            text = title,
            color = if(selected){Color(0xFFFFF176)
            }else{Color(0xFF7986CB)
            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally),

            )

    }
}

@Composable
fun MyPhysicalActivityIndicator(color: Color, modifier: Modifier){
    Box(
        modifier
            //.padding(5.dp)
            .fillMaxSize()
            //.border(BorderStroke(2.dp, color), RoundedCornerShape(5.dp))
    ){
        Divider(thickness = 2.dp,
            color = Color.White,
        modifier = Modifier.align(Alignment.BottomCenter))
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


    val stepList = {
        stepsListContent()
    }

    val distanceList = {
        distanceListContent()
    }

    val caloriesList = {
        caloriesListContent()
    }

    var state by remember { mutableStateOf(0) }
    val titles = listOf("Steps", "Distance", "Calories")

// Reuse the default offset animation modifier, but use our own indicator
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        MyPhysicalActivityIndicator(
            MaterialTheme.colorScheme.primary,
            Modifier.tabIndicatorOffset(tabPositions[state])
        )
    }

    TabRow(
        selectedTabIndex = state,
        modifier = modifierTabs,//.height(52.dp),
        containerColor = Color.DarkGray,
        indicator = indicator,
        divider = {
            Divider(modifier = Modifier.fillMaxWidth(), color = Color(0xFF7986CB))
        },
    ) {
        titles.forEachIndexed { index, title ->
            MyPhysicalActivityTab(title = title, onClick = { state = index }, selected = (index == state))
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
        itemsIndexed(
            stepList(),
            key = { index, value ->
                getIntervals(index, hourList)
            }
        ) { index, item ->
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
        itemsIndexed(
            distanceList(),
            key = { index, value ->
                getIntervals(index, hourList)
            }
        ) { index, item ->
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
        itemsIndexed(
            caloriesList(),
            key = { index, value ->
                getIntervals(index, hourList)
            }
        ) { index, item ->
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


val legendItemLabelTextSize = 12.sp
val legendItemIconSize = 8.dp
val legendItemIconPaddingValue = 10.dp
val legendItemSpacing = 4.dp
private val legendTopPaddingValue = 8.dp
val legendPadding = dimensionsOf(top = legendTopPaddingValue)
@Composable
fun rememberLegendPhysicalActivity() = verticalLegend(
    items = chartColorsPhysicalActivity.mapIndexed { _, chartColor ->
        verticalLegendItem(
            icon = shapeComponent(Shapes.pillShape, chartColor),
            label = textComponent(
                color = Color.White,
                textSize = legendItemLabelTextSize,
                typeface = Typeface.MONOSPACE,
            ),
            labelText = "Steps",
        )
    },
    iconSize = legendItemIconSize,
    iconPadding = legendItemIconPaddingValue,
    spacing = legendItemSpacing,
    padding = legendPadding,
)