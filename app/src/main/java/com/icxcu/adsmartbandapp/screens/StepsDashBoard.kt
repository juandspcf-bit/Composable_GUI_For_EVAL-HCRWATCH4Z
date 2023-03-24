package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R

@Composable
fun StepsDashBoard() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .height(400.dp)
    ) {
        val (stepsCard, distanceCaloriesValues) = createRefs()
        val guideV5 = createGuidelineFromEnd(fraction = .5f)


        GenericCard(
            modifier = Modifier
                .constrainAs(stepsCard) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    linkTo(
                        start = parent.start,
                        end = guideV5,
                    )
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
                .padding(5.dp),
            title = "Steps",
            text = "10000",
            fieldPlural = "Steps",
            resource = R.drawable.baseline_directions_walk_24,
            iconPadding = 20.dp
        )

        ConstraintLayout(
            modifier = Modifier
                .constrainAs(distanceCaloriesValues) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    linkTo(
                        start = guideV5,
                        end = parent.end,
                    )
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .background(Color.DarkGray)
        ) {
            val (distanceCard, caloriesCard) = createRefs()
            val guideDistanceCaloriesH50 = createGuidelineFromBottom(fraction = 0.5f)

            GenericCard(
                modifier = Modifier
                    .constrainAs(distanceCard) {
                        top.linkTo(parent.top)
                        bottom.linkTo(guideDistanceCaloriesH50)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                        )
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    }
                    .padding(5.dp),
                title = "Distance",
                text = "10000",
                fieldPlural = "Km",
                resource = R.drawable.baseline_add_road_24,
                iconPadding = 20.dp
            )


            GenericCard(
                modifier = Modifier
                    .constrainAs(caloriesCard) {
                        top.linkTo(guideDistanceCaloriesH50)
                        bottom.linkTo(parent.bottom)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                        )
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    }
                    .padding(5.dp),
                title = "Calories",
                text = "10000",
                fieldPlural = "KCal",
                resource = R.drawable.baseline_local_fire_department_24,
                iconPadding = 20.dp
            )


        }


    }
}

@Composable
fun GenericCard(
    modifier: Modifier = Modifier,
    title: String = "Field",
    text: String = "value",
    fieldPlural: String = "Field",
    resource: Int = R.drawable.ic_launcher_foreground,
    iconTint: Color = Color.Green,
    iconPadding: Dp = 0.dp,
    backgroundCard: Color = Color.DarkGray,
    isWithIconTitle:Boolean=false,
    resourceIconTitle: Int = R.drawable.ic_launcher_foreground,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(size = 26.dp),
        border = BorderStroke(width = 1.dp, color = Color.Green),
        elevation = 4.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundCard)
        ) {
            val (iconValuesSteps, valuesTitle) = createRefs()
            val guideH50 = createGuidelineFromBottom(fraction = 0.5f)

            ConstraintLayout(modifier = Modifier
                .constrainAs(valuesTitle) {
                    top.linkTo(parent.top)
                    bottom.linkTo(guideH50)
                    linkTo(
                        start = parent.start,
                        end = parent.end,

                        )
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                }
                .padding(10.dp)) {
                val (titleSub, unit) = createRefs()
                createVerticalChain(titleSub, unit, chainStyle = ChainStyle.SpreadInside)

/**/            Text(
                text = title, modifier = Modifier
                    .constrainAs(titleSub) {centerHorizontallyTo(parent)},
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5
            )
            }

            ConstraintLayout(
                modifier = Modifier
                    .constrainAs(iconValuesSteps) {
                        top.linkTo(guideH50)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .background(backgroundCard)
            ) {
                val iconValueStepsV50 = createGuidelineFromEnd(fraction = 0.5f)
                val (iconSteps, valuesSteps) = createRefs()
                Icon(
                    painter = painterResource(resource),
                    contentDescription = "frost",
                    modifier = Modifier
                        .constrainAs(iconSteps) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(iconValueStepsV50)

                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        }
                        .fillMaxSize()
                        .padding(iconPadding),
                    tint = iconTint
                )

                ConstraintLayout(modifier = Modifier
                    .constrainAs(valuesSteps) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        linkTo(
                            start = iconValueStepsV50,
                            end = parent.end,

                            )
                        height = Dimension.wrapContent
                        width = Dimension.wrapContent
                    }
                    .padding(10.dp)) {
                    val (value, unit) = createRefs()
                    createVerticalChain(value, unit, chainStyle = ChainStyle.SpreadInside)

                    Text(
                        modifier = Modifier
                            .constrainAs(value) { centerHorizontallyTo(parent) },
                        text = text,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5
                    )

                    Text(
                        modifier = Modifier
                            .constrainAs(unit) { centerHorizontallyTo(parent) },
                        text = fieldPlural,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5
                    )


                }

            }


        }
    }

}

@Preview(showBackground = true)
@Composable
fun StepsDashBoardPreview() {
    StepsDashBoard()
}
