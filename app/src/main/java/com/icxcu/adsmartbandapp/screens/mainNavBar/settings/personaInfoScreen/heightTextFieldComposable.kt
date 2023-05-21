package com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.input.pointer.pointerInput
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
fun NumericHeightTextFieldComposable(
    getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    onNumericUnitTextChange: (String) -> Unit,
    onNumericUnitTextFieldVisibilityChange: (Boolean) -> Unit,
    unit:String,
    contentDescription:String = "",
    resourceIcon1:Int = R.drawable.ic_launcher_foreground,
    validator: (String) -> String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { onNumericUnitTextFieldVisibilityChange(!getPersonalInfoDataStateState().heightTextFieldVisibility) },
                        onDoubleTap = { /* Double Tap Detected */ },
                        onLongPress = { /* Long Press Detected */ },
                        onTap = {  }
                    )
                }
                .fillMaxWidth(0.85f)
                //.padding(start = 20.dp, end = 20.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .background(color = Color(0xFFE91E63))
        ) {

            val numberValidated = validator(getPersonalInfoDataStateState().height)

            val displayNumericUnit = if (numberValidated == "") {
                "Your $contentDescription"
            } else {
                "$numberValidated $unit"
            }

            Text(
                displayNumericUnit,
                textAlign = TextAlign.Start,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
            )
        }

        Icon(
            painter = painterResource(resourceIcon1),
            contentDescription = contentDescription,
            tint = Color(0xFFFFC107),
            modifier = Modifier.fillMaxWidth(1f)
                .size(50.dp)
                .clickable {
                    onNumericUnitTextFieldVisibilityChange(!getPersonalInfoDataStateState().heightTextFieldVisibility)
                }
        )
    }

    AnimatedVisibility(
        visible = getPersonalInfoDataStateState().heightTextFieldVisibility,
        enter = expandVertically(animationSpec = tween(durationMillis = 1000)),
        exit = slideOutVertically()
    ) {
        NumericHeightTexField(
            getPersonalInfoDataStateState,
            onNumericUnitTextChange = onNumericUnitTextChange,
            onNumericUnitTextFieldVisibilityChange,
            contentDescription = contentDescription,
            resourceIcon1 = resourceIcon1
        )
    }


}


@Composable
fun NumericHeightTexField(
    getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    onNumericUnitTextChange: (String) -> Unit,
    onNumericUnitTextFieldVisibilityChange: (Boolean) -> Unit,
    contentDescription:String = "",
    resourceIcon1:Int = R.drawable.ic_launcher_foreground,

    ) {

    OutlinedTextField(
        value = getPersonalInfoDataStateState().height,
        onValueChange = onNumericUnitTextChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        label = { Text("Your $contentDescription") },
        modifier = Modifier.padding(10.dp)
            .background(Color(0xff1d2a35)),
        textStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(resourceIcon1),
                contentDescription = contentDescription,
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onNumericUnitTextFieldVisibilityChange(!getPersonalInfoDataStateState().heightTextFieldVisibility)
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
