package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.data.MockData
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Gauge(modifier: Modifier= Modifier) {

    Canvas(modifier = modifier.then(Modifier
        .size(150.dp, 75.dp)
        .offset(y = (0).dp)
        .padding(top = 20.dp , bottom = 20.dp , start = 40.dp, end=40.dp)
    )) {
        val height = size.height
        val width = size.width

        val cut = 00f

        val strokeWidth = 70f

        drawArc(
            Color(0xffba3a02),
            startAngle = 144f+180+cut,
            sweepAngle = 36f,
            useCenter = false,
            size = Size(width, height*2),
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Butt
            )
        )


        drawArc(
            Color(0xff990711),
            startAngle = 108f+180+cut,
            sweepAngle = 36f,
            useCenter = false,
            size = Size(width, height*2),
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Butt
            )
        )



        drawArc(
                Color(0xffa6ce39),
        startAngle = 0f+180+cut,
        sweepAngle = 36f,
        useCenter = false,
        size = Size(width, height*2),
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Butt
        )
        )

        drawArc(
            Color(0xffffec00),
            startAngle = 36f+180+cut,
            sweepAngle = 36f,
            useCenter = false,
            size = Size(width, height*2),
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Butt
            )
        )

        drawArc(
            Color(0xffffb600),
            startAngle = 72f+180+cut,
            sweepAngle = 36f,
            useCenter = false,
            size = Size(width, height*2),
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Butt
            )
        )



        val angleOffset = 90
        val angle = 18*9

        val x = -cos((angle)* PI/180)*width/2+width/2f
        val y = -sin((angle)* PI/180)*height+height

        Log.d("Angles", "Gauge: $x, $y")

        drawLine(
            start = Offset(x = width/2f, y = height/1f),
            end = Offset(x = x.toFloat(), y = y.toFloat()),
            color = Color(0xFFFF5722),
            strokeWidth = 30.0f,
            cap = StrokeCap.Round

        )
        drawCircle(
            color = Color(0xFFE6B113),
            center =  Offset(x = width/2f, y = height/1f),
            radius = (0.1f*width/2f)
        )

        drawCircle(
            color = Color(0xFF49412A),
            center =  Offset(x = width/2f, y = height/1f),
            radius = (0.05f*width/2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GaugePreview() {

    Gauge()


}