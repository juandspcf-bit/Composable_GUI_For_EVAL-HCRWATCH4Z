package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import com.icxcu.adsmartbandapp.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListFields(){

    val listOf = mutableListOf<GenericCardData>()
    listOf.add(GenericCardData(modifier = Modifier
        .padding(5.dp),
        title = "Steps",
        text = "10000",
        fieldPlural = "Steps",
        resource = R.drawable.walk,
        iconPadding = 20.dp,
    size = 400.dp))
    listOf.add(GenericCardData(modifier = Modifier
        .padding(5.dp),
        title = "Distance",
        text = "10000",
        fieldPlural = "Km",
        resource = R.drawable.measure_distance,
        iconPadding = 20.dp,
        size = 200.dp))

    listOf.add(GenericCardData(
        modifier = Modifier
            .padding(5.dp),
        title = "Calories",
        text = "10000",
        fieldPlural = "KCal",
        resource = R.drawable.calories,
        iconPadding = 20.dp,
        size = 200.dp))

    listOf.add(GenericCardData(
        modifier = Modifier
            .padding(5.dp),
        title = "Blood Pressure",
        text = "130",
        fieldPlural = "mmHg",
        resource = R.drawable.blood_pressure_gauge,
        iconPadding = 20.dp,
        size = 200.dp))


    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().background(Color(0xff1d2a35)),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalItemSpacing = 0.dp
    ){
        items(listOf){
            GenericCard(
                modifier = it.modifier,
                title = it.title,
                text = it.text,
                fieldPlural = it.fieldPlural,
                resource = it.resource,
                iconPadding = it.iconPadding,
                size = it.size
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ListFieldsPreview() {
    ListFields()
}