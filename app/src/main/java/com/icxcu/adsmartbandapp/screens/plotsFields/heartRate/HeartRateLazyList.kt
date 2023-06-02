package com.icxcu.adsmartbandapp.screens.plotsFields.heartRate

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.screens.plotsFields.PlotsConstants

@Composable
fun HeartRateLazyList(
    heartRateList: () -> List<Double>,
    getAgeCalculated: () -> Int,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(items = heartRateList(),
            key = { index, value ->
                PlotsConstants.HOUR_INTERVALS[index]
            }) { index, heartRateValue ->
            val stringHeartRateValue = String.format("%.1f", heartRateValue)

            Log.d("MyCalculatedAge", "HeartRateList: ${getAgeCalculated()}")
            val myAge = if (getAgeCalculated() > 0) (getAgeCalculated()) else {
                41
            }
            val zoneResource = getHeartRateZones(heartRateValue, myAge)
            val zoneReadable = getReadableHeartRateZones(heartRateValue, myAge)

            RowHeartRate(
                valueHeartRate = "$stringHeartRateValue bpm",
                resource = zoneResource,
                readableCategory = zoneReadable,
                hourTime = PlotsConstants.HOUR_INTERVALS[index]
            )
        }
    }
}

@Composable
fun RowHeartRate(
    valueHeartRate: String,
    resource: Int,
    readableCategory: String,
    hourTime: String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxSize()

        ) {
            val (hour, icon, value) = createRefs()
            val guideHourIcon = createGuidelineFromStart(fraction = 0.35f)
            val gideIconValue = createGuidelineFromStart(fraction = 0.65f)

            Text(text = hourTime, color = Color.White, modifier = Modifier.constrainAs(hour) {
                linkTo(start = parent.start, end = guideHourIcon)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                height = Dimension.wrapContent
            })



            Image(
                painter = painterResource(resource),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(guideHourIcon)
                        end.linkTo(gideIconValue)
                        width = Dimension.fillToConstraints
                    }
                    .size(50.dp)
                    .fillMaxWidth()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .constrainAs(value) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(gideIconValue)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }) {

                Text(text = valueHeartRate, color = Color.White)
                Text(readableCategory, color = Color(0x9fffffff), textAlign = TextAlign.Center)
            }

        }

        Divider(modifier = Modifier.height(1.dp))

    }
}