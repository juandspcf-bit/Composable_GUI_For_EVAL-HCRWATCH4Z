package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.R

@Composable
fun TestingHealthScreen() {
    val fields = listOf(
        R.drawable.blood_pressure_gauge,
        R.drawable.oxygen_saturation,
        R.drawable.thermometer
    )




    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        LazyVerticalGrid(modifier = Modifier.background(Color(0xFF03A9F4)).fillMaxWidth(),horizontalArrangement = Arrangement.SpaceEvenly,
            columns = GridCells.Fixed(3),
            state = rememberLazyGridState(),
            contentPadding = PaddingValues(30.dp)
        ){

            items(fields){
                ButtonCircle(it)
            }

        }

        Image(modifier=Modifier.background(Color(0xff1d2a35)).fillMaxSize(),
            painter = painterResource(R.drawable.test),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    }




}


@Composable
fun ButtonCircle(resource:Int=R.drawable.blood_pressure_gauge){
    Image(modifier = Modifier.padding(10.dp).clip(CircleShape).border(BorderStroke(2.dp, Color.DarkGray), CircleShape).background(Color(
        0xFFE7EDF1
    )
    ),
        painter = painterResource(resource),
        contentDescription = null,
        contentScale = ContentScale.FillWidth)
}


@Preview(showBackground = true)
@Composable
fun TestingHealthScreenPreview() {
    TestingHealthScreen()
}
