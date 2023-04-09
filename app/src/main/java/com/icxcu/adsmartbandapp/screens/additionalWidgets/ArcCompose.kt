package com.icxcu.adsmartbandapp.screens.additionalWidgets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.magnifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ArcCompose(
    modifier: Modifier = Modifier,
    stepsMade: Int = 0,
    stepsGoal: Int = 10000,
    sizeContainer: Dp = 150.dp,
    radius: Dp = 50.dp
) {

    Box(
        modifier = modifier.background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        DrawArc(
            stepsMade = stepsMade,
            stepsGoal = stepsGoal,
            sizeContainer = sizeContainer,
            radius = radius
        )
    }

}

@Composable
fun DrawArc(
    stepsMade: Int = 0,
    stepsGoal: Int = 10000,
    sizeContainer: Dp = 150.dp,
    radius: Dp = 50.dp
) {
    var angle = 360 * stepsMade.toFloat() / stepsGoal.toFloat()
    Canvas(modifier = Modifier
        .size(sizeContainer)
        .background(Color.Transparent)) {
        drawArc(
            Color.Yellow,
            startAngle = -180f,
            sweepAngle = angle,
            topLeft = Offset(
                sizeContainer.toPx() / 2 - radius.toPx() / 2,
                sizeContainer.toPx() / 2 - radius.toPx() / 2
            ),
            useCenter = false,
            size = Size(radius.toPx(),radius.toPx()),
            style = Stroke(
                width = 30f,
                cap = StrokeCap.Round
            ),

            )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArcComposePreview() {
    ArcCompose(stepsMade = 6000, stepsGoal = 10000)
}