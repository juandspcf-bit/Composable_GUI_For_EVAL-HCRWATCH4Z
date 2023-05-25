package com.icxcu.adsmartbandapp.screens.loadingScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    data:String ="",
    navLambdaDataScreenDataPreferences: () -> Unit,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff0d1721)),
        contentAlignment = Alignment.Center
    ){

        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = Color(0xFFDCE775),
            backgroundColor = Color.Transparent,
            strokeWidth = 10.dp
        )

        Log.d("CircularProgress", "LoadingScreen: ")
        LaunchedEffect(key1 = data, ){
            delay(1000)
            navLambdaDataScreenDataPreferences()
        }


    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {

}