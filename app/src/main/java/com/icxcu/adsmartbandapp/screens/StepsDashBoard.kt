package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R

@Composable
fun StepsDashBoard(){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .background(Color.DarkGray)
        .height(512.dp)) {
        val (iconSteps, valueSteps, kmSteps) = createRefs()
        val guide50 = createGuidelineFromBottom(fraction = 0.3f)
        val guide75 = createGuidelineFromBottom(fraction = 0.15f)

        Icon(
            painter = painterResource(R.drawable.baseline_directions_walk_24),
            contentDescription = "frost",
            modifier = Modifier
                .constrainAs(iconSteps) {
                    top.linkTo(parent.top, margin = 50.dp)
                    bottom.linkTo(guide50, margin = 50.dp)
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                        startMargin = 50.dp,
                        endMargin = 50.dp
                    )
                    height = Dimension.fillToConstraints
                }
                .fillMaxSize(),
            tint = Color.White
        )

        Text(text = "1000 steps", modifier = Modifier.constrainAs(valueSteps){
            top.linkTo(guide50)
            bottom.linkTo(guide75)
            linkTo(
                start = parent.start,
                end = parent.end,

            )
            height = Dimension.wrapContent
            width = Dimension.matchParent
        }.background(Color.Green),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h4)

        Text(text = "1000 km", modifier = Modifier.constrainAs(kmSteps){
            top.linkTo(guide75)
            bottom.linkTo(parent.bottom)
            linkTo(
                start = parent.start,
                end = parent.end,

                )
            height = Dimension.wrapContent
            width = Dimension.matchParent
        }.background(Color.Green),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4)


    }
}

@Preview(showBackground = true)
@Composable
fun StepsDashBoardPreview() {
    StepsDashBoard()
}
