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
            .background(Color(0xff1d2a35)),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            state = rememberLazyGridState(),
            contentPadding = PaddingValues(2.dp),
            verticalArrangement=Arrangement.SpaceEvenly
        ) {

            items(fields) {
                BoxImageCircle(
                    withImage = true,
                    imageResource = it
                )

            }
        }


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
    withImage: Boolean = false,
    imageResource: Int = R.drawable.ic_launcher_foreground
) {
    Box(
        modifier = modifier
            .size(110.dp)
            .background(Color(0xff1d2a35))
            .padding(start = 10.dp, end = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        if (withImage) {

            Box(
                modifier = modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
                    .border(
                        BorderStroke(
                            2.dp,
                            color = Color.Green
                        ), CircleShape
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = modifier
                        .size(75.dp),
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
