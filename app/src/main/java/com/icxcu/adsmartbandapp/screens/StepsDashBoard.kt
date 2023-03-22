package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.ui.theme.ADSmartBandAppTheme

@Composable
fun StepsDashBoard(){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .background(Color.DarkGray)
        .height(512.dp)) {
        val (iconSteps, valueSteps, kmSteps) = createRefs()
        val guide50 = createGuidelineFromBottom(fraction = 1f)
        val guide75 = createGuidelineFromBottom(fraction = 0.7f)

        Box(modifier = Modifier.constrainAs(iconSteps){
            top.linkTo(parent.top)
            bottom.linkTo(guide50)
            linkTo(start=parent.start, end = parent.end)
        }.fillMaxWidth()) {
            Icon(
                painter = painterResource(R.drawable.baseline_directions_walk_24),
                contentDescription = "frost",
                modifier = Modifier.align(Alignment.Center),
                tint = Color.White
            )
        }



    }
}

@Preview(showBackground = true)
@Composable
fun StepsDashBoardPreview() {
    StepsDashBoard()
}
