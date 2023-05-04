package com.icxcu.adsmartbandapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.R

@Composable
fun Settings() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff1d2a35)),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Row (verticalAlignment = Alignment.CenterVertically){

                Box(modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(end = 20.dp,)
                    .clip(shape= RoundedCornerShape(size = 12.dp))
                    .background(color = Color(0xFFE91E63))
                   ) {

                    Text("Personal Information",
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 10.dp, top=10.dp, bottom = 10.dp)
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.baseline_person_24),
                    contentDescription = "Date Range",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    Settings()
}