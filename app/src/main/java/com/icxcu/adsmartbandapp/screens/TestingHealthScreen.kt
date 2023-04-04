package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R

@Composable
fun TestingHealthScreen() {
    val fields = listOf(
        R.drawable.heart_rate,
        R.drawable.blood_pressure_gauge,
        R.drawable.oxygen_saturation,
        R.drawable.thermometer
    )




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0580B8)),
        verticalArrangement = Arrangement.Top
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0580B8))
                .padding(10.dp)
        ) {
            val (bloodP) = createRefs()
            val createGuidelineFromStart1 = createGuidelineFromStart(fraction = .25f)
            val createGuidelineFromStart2 = createGuidelineFromStart(fraction = .75f)
            LazyVerticalGrid(
                modifier = Modifier.constrainAs(bloodP){
                                                       linkTo(start=createGuidelineFromStart1,
                                                           end=createGuidelineFromStart2,
                                                       )
                    top.linkTo(parent.top)
                    width = Dimension.wrapContent
                },
                columns = GridCells.Adaptive(100.dp),
                state = rememberLazyGridState(),
                contentPadding = PaddingValues(10.dp)
            ) {

                items(fields) {
                    BoxImageCircle(
                        withImage = true,
                        imageResource = it
                    )

                }
            }
        }
        //BarIconsWithConstrainLayout()

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.test),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

    }


}




@Composable
fun BoxImageCircle(
    modifier: Modifier = Modifier,
    colorBacKGround: Color = Color(
        0xFFE7EDF1
    ),
    withImage: Boolean = false,
    imageResource: Int = R.drawable.ic_launcher_foreground
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .background(Color(0xFF0580B8)),
        contentAlignment = Alignment.Center
    ) {
        if (withImage) {

            Box(
                modifier = modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDDE7EC))
                    .border(
                        BorderStroke(
                            2.dp,
                            color = Color.DarkGray
                        ), CircleShape
                    ),
                contentAlignment = Alignment.Center
            ){
                Image(
                    modifier = modifier
                        .size(75.dp)
 ,

                    painter = painterResource(imageResource),
                    contentDescription = null,
                    contentScale = ContentScale.Inside
                )
            }


        }


    }
}

@Preview(showBackground = true)
@Composable
fun TestingHealthScreenPreview() {
    TestingHealthScreen()
}
