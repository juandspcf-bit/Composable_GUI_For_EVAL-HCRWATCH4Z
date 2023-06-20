package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icxcu.adsmartbandapp.R

@Composable
fun NameTextFieldComposable(
    getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    onTextChange: (String) -> Unit,
    onNameTextFieldVisibilityChange: (Boolean) -> Unit,
    resourceIcon1:Int = R.drawable.ic_launcher_foreground,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .clickable {
                    onNameTextFieldVisibilityChange(!getPersonalInfoDataStateState().nameTextFieldVisibility)
                }
                .fillMaxWidth(0.85f)
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .background(color = Color(0xFFE91E63))
        ) {

            val displayName = if (getPersonalInfoDataStateState().name == "") {
                "Your name"
            } else {
                getPersonalInfoDataStateState().name
            }

            Text(
                displayName,
                textAlign = TextAlign.Start,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
            )
        }

        Icon(
            painter = painterResource(resourceIcon1),
            contentDescription = "Date Range",
            tint = Color.White,
            modifier = Modifier.fillMaxWidth(1f)
                .size(50.dp)
                .clickable {
                    onNameTextFieldVisibilityChange(!getPersonalInfoDataStateState().nameTextFieldVisibility)
                }
        )
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ){
        AnimatedVisibility(
            visible = getPersonalInfoDataStateState().nameTextFieldVisibility,
            enter = expandVertically(animationSpec = tween(durationMillis = 1000)),
            exit = slideOutVertically()
        ) {
            NameTexField(
                getPersonalInfoDataStateState,
                onTextChange = onTextChange,
                onNameTextFieldVisibilityChange,
                resourceIcon1
            )
        }
    }




}

@Composable
fun NameTexField(
    getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    onTextChange: (String) -> Unit,
    onNameTextFieldVisibilityChange: (Boolean) -> Unit,
    resourceIcon1:Int = R.drawable.ic_launcher_foreground,
) {

    OutlinedTextField(

        value = getPersonalInfoDataStateState().name,
        onValueChange = onTextChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        label = { Text("Your Name") },
        modifier = Modifier
            .padding(10.dp)
            .background(Color(0xff1d2a35))
            ,
        textStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person Icon"
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onNameTextFieldVisibilityChange(!getPersonalInfoDataStateState().nameTextFieldVisibility)
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            focusedContainerColor = Color(0xff1d2a35),
            unfocusedContainerColor = Color(0xff1d2a35),
            focusedIndicatorColor = Color(0xFFFFC107),
            unfocusedIndicatorColor = Color(0xFFFFC107),
            focusedTrailingIconColor = Color(0xFFFFC107),
            unfocusedTrailingIconColor = Color(0xFFFFC107),
        )



    )
}