package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import androidx.navigation.compose.rememberNavController
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.screens.additionalWidgets.ArcCompose

@Composable
fun DashBoardCard(
    modifier: Modifier = Modifier,
    title: String = "Field",
    text: String = "value",
    fieldPlural: String = "Field",
    resource: Int = R.drawable.ic_launcher_foreground,
    iconPadding: Dp = 0.dp,
    backgroundCard: Color = Color.DarkGray,
    verticalChainData: Boolean = true,
    isWithIconTitle: Boolean = false,
    resourceIconTitle: @Composable () -> Unit = { Spacer(modifier = Modifier.size(0.dp)) },
    heightCard: Dp = 200.dp,
    widthCard: Dp = 200.dp,
    guidelineFromBottomFraction: Float = 0.5f
) {
    Card(
        modifier = modifier
            .height(heightCard)
            .width(widthCard),
        shape = RoundedCornerShape(size = 26.dp),
        border = BorderStroke(width = 1.dp, color = Color.Green),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),

        ) {

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundCard)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
            ) {

                Text(
                    modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                    text = title,

                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )



                if (isWithIconTitle) {
                    Text(
                        modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                        text = title,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                    ) {
                        resourceIconTitle()
                    }
                }

            }


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(backgroundCard)
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(iconPadding)
                        .fillMaxWidth(0.3f)
                ) {
                    Image(
                        painter = painterResource(resource),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center

                    )
                }


                if(verticalChainData){
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {

                        Text(
                            modifier = Modifier,
                            text = text,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            modifier = Modifier,
                            text = fieldPlural,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )

                    }
                }else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                    ) {

                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = text,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = fieldPlural,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                }


            }


        }


    }

}


data class DashBoardCardData(
    var modifier: Modifier = Modifier,
    var title: String = "Field",
    var text: String = "value",
    var fieldPlural: String = "Field",
    var resource: Int = R.drawable.ic_launcher_foreground,
    var iconTint: Color = Color.Green,
    var iconPadding: Dp = 0.dp,
    var backgroundCard: Color = Color.DarkGray,
    var isWithIconTitle: Boolean = false,
    var resourceIconTitle: @Composable () -> Unit = { Spacer(modifier = Modifier.size(0.dp)) },
    var heightCard: Dp = 256.dp,
    var widthCard: Dp = 400.dp,
    var verticalChainData: Boolean = true,
    var callBack: () -> Unit = {},
    var guidelineFromBottomFraction: Float = 0.5f
)

@Preview(showBackground = true)
@Composable
fun DashBoardCardPreview() {
    val navController = rememberNavController()
    val it = DashBoardCardData(
        modifier = Modifier
            .padding(5.dp),
        title = "Steps",
        text = "20000",
        fieldPlural = "Steps",
        resource = R.drawable.walk,
        iconPadding = 0.dp,
        heightCard = 400.dp,
        widthCard = 500.dp,
        verticalChainData = false,
        isWithIconTitle = true,
        resourceIconTitle = {
            ArcCompose(
                modifier = Modifier.fillMaxWidth(),
                stepsMade = 7000,
                stepsGoal = 10000,
                radius = 70.dp
            )
        },
        callBack = { navController.popBackStack() }
    )

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
        resourceIconTitle = it.resourceIconTitle

    )

}
