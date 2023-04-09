package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.compose.rememberNavController
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.screens.additionalWidgets.ArcCompose

@Composable
fun GenericCard(
    modifier: Modifier = Modifier,
    title: String = "Field",
    text: String = "value",
    fieldPlural: String = "Field",
    resource: Int = R.drawable.ic_launcher_foreground,
    iconPadding: Dp = 0.dp,
    backgroundCard: Color = Color.DarkGray,
    verticalChainData: Boolean = true,
    isWithIconTitle: Boolean = false,
    resourceIconTitle: @Composable () -> Unit ={Spacer(modifier = Modifier.size(0.dp))},
    size: Dp = 200.dp
) {
    Card(
        modifier = modifier.height(size),
        shape = RoundedCornerShape(size = 26.dp),
        border = BorderStroke(width = 1.dp, color = Color.Green),
        elevation = 4.dp,

        ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundCard)
        ) {
            val (iconValuesSteps, valuesTitle) = createRefs()
            val guideH50 = createGuidelineFromBottom(fraction = 0.5f)

            /*ConstraintLayout(modifier = Modifier
                .constrainAs(valuesTitle) {
                    top.linkTo(parent.top)
                    bottom.linkTo(guideH50)
                    linkTo(
                        start = parent.start,
                        end = parent.end,

                        )
                    height = Dimension.matchParent
                    width = Dimension.matchParent
                }
                .fillMaxSize().padding(10.dp)) {

                val (titleSub, pointer) = createRefs()
                createVerticalChain(titleSub, pointer, chainStyle = ChainStyle.Spread)






            }*/
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                .constrainAs(valuesTitle) {
                    top.linkTo(parent.top)
                    bottom.linkTo(guideH50)
                    linkTo(
                        start = parent.start,
                        end = parent.end,

                        )
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }) {

                Text(
                    modifier=Modifier.padding(top =0.dp, bottom = 0.dp),
                text = title,
                   // modifier = Modifier
                    //.constrainAs(titleSub) { centerHorizontallyTo(parent) }
                    //.padding(bottom = 0.dp),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5)



                if (isWithIconTitle){
                    Text(
                        modifier=Modifier.padding(top =0.dp, bottom = 0.dp),
                        text = title,
                        // modifier = Modifier
                        //.constrainAs(titleSub) { centerHorizontallyTo(parent) }
                        //.padding(bottom = 0.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5)

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                        //.constrainAs(pointer) { centerHorizontallyTo(parent) },
                    ){
                        resourceIconTitle()
                    }
                }

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


                Image(
                    painter = painterResource(resource),
                    contentDescription = null,
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
                    if (verticalChainData) {
                        createVerticalChain(value, unit, chainStyle = ChainStyle.SpreadInside)
                    } else {
                        createHorizontalChain(value, unit, chainStyle = ChainStyle.SpreadInside)
                    }



                    Text(
                        modifier = Modifier
                            .constrainAs(value) {
                                if (verticalChainData) {
                                    centerHorizontallyTo(parent)
                                } else {
                                    centerVerticallyTo(parent)
                                }
                            }
                            .padding(
                                if (verticalChainData) {
                                    0.dp
                                } else {
                                    2.dp
                                }
                            ),
                        text = text,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5
                    )

                    Text(
                        modifier = Modifier
                            .constrainAs(unit) {
                                if (verticalChainData) {
                                    centerHorizontallyTo(parent)
                                } else {
                                    centerVerticallyTo(parent)
                                }
                            }
                            .padding(
                                if (verticalChainData) {
                                    0.dp
                                } else {
                                    2.dp
                                }
                            ),
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



data class GenericCardData(
    var modifier: Modifier = Modifier,
    var title: String = "Field",
    var text: String = "value",
    var fieldPlural: String = "Field",
    var resource: Int = R.drawable.ic_launcher_foreground,
    var iconTint: Color = Color.Green,
    var iconPadding: Dp = 0.dp,
    var backgroundCard: Color = Color.DarkGray,
    var isWithIconTitle: Boolean = false,
    var resourceIconTitle:  @Composable () -> Unit ={Spacer(modifier = Modifier.size(0.dp))},
    var size: Dp = 256.dp,
    var verticalChainData: Boolean = true,
    var callBack: () -> Unit = {}
)

@Preview(showBackground = true)
@Composable
fun StepsDashBoardPreview() {
    var navController=rememberNavController()
    var it = GenericCardData(
        modifier = Modifier
            .padding(5.dp),
        title = "Steps",
        text = "20000",
        fieldPlural = "Steps",
        resource = R.drawable.walk,
        iconPadding = 20.dp,
        size = 400.dp,
        isWithIconTitle = true,
        resourceIconTitle = { ArcCompose(
            modifier = Modifier.fillMaxWidth(),
            stepsMade = 7000,
            stepsGoal = 10000,
            radius = 70.dp
        ) },
        callBack = { navController.popBackStack() }
    )

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
        isWithIconTitle = it.isWithIconTitle,
        resourceIconTitle = it.resourceIconTitle

        )

}
