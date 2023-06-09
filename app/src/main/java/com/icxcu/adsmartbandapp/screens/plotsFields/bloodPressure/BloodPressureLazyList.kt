package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.screens.plotsFields.PlotsConstants

@Composable
fun BloodPressureLazyList(
    systolicList: () -> List<Double>,
    diastolicList: () -> List<Double>,
    modifier: Modifier = Modifier
) {
    val bloodPressureFullList = systolicList().zip(diastolicList()).toList()

    Box(
        modifier = modifier
    ) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            itemsIndexed(
                bloodPressureFullList,
                key = { index, _ ->
                    PlotsConstants.HOUR_INTERVALS[index]
                }
            ) { index, pair ->
                val systolicValue = pair.first
                val diastolicValue = pair.second

                val stringSystolicValue = String.format("%.1f", systolicValue)
                val stringDiastolicValue = String.format("%.1f", diastolicValue)

                val getCategory = getBloodPressureCategory(systolicValue, diastolicValue)
                val category = mapCategories[getCategory]
                val readableCategory = mapToReadableCategories[getCategory]

                Log.d("CATEGORY", "BloodPressureLazyList: $getCategory")

                RowBloodPressure(
                    valueBloodPressure = "$stringSystolicValue/$stringDiastolicValue mmHg",
                    resource = category ?: R.drawable.blood_pressure_gauge,
                    readableCategory = readableCategory ?: "No Category",
                    hourTime = PlotsConstants.HOUR_INTERVALS[index]
                )

            }
        }
    }
}

@Composable
fun RowBloodPressure(
    valueBloodPressure: String,
    resource: Int = R.drawable.blood_pressure_gauge,
    readableCategory: String = "No category",
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
                    .size(30.dp)
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

                Text(text = valueBloodPressure, color = Color.White)
                Text(readableCategory, color = Color(0x9fffffff), textAlign = TextAlign.Center)
            }

        }

        Divider(modifier = Modifier.height(1.dp))

    }
}
