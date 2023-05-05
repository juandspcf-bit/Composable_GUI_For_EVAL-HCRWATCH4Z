package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun NumericUnitTextFieldComposable(
    currentNumericUnit: () -> String,
    currentNumericUnitTextFieldVisibility: () -> Boolean,
    onNumericUnitTextChange: (String) -> Unit,
    onNumericUnitTextFieldVisibilityChange: (Boolean) -> Unit,
    unit:String,
    contentDescription:String = "",
    resourceIcon1:Int = R.drawable.ic_launcher_foreground
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(end = 20.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .background(color = Color(0xFFE91E63))
        ) {

            val displayNumericUnit = if (currentNumericUnit() == "") {
                "Your weight"
            } else {
                currentNumericUnit() + " $unit"
            }

            Text(
                displayNumericUnit,
                textAlign = TextAlign.Start,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    start = 10.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
            )
        }

        Icon(
            painter = painterResource(resourceIcon1),
            contentDescription = contentDescription,
            tint = Color(0xFFFFC107),
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onNumericUnitTextFieldVisibilityChange(!currentNumericUnitTextFieldVisibility())
                }
        )
    }

    AnimatedVisibility(
        visible = currentNumericUnitTextFieldVisibility(),
        enter = expandVertically(animationSpec = tween(durationMillis = 1000)),
        exit = slideOutVertically()
    ) {
        NumericUnitTexField(
            value = currentNumericUnit,
            onNumericUnitTextChange = onNumericUnitTextChange,
            currentNumericUnitTextFieldVisibility,
            onNumericUnitTextFieldVisibilityChange,
            contentDescription = contentDescription,
            resourceIcon1 = resourceIcon1
        )
    }


}


@Composable
fun NumericUnitTexField(
    value: () -> String,
    onNumericUnitTextChange: (String) -> Unit,
    currentNumericUnitTextFieldVisibility: () -> Boolean,
    onNumericUnitTextFieldVisibilityChange: (Boolean) -> Unit,
    contentDescription:String = "",
    resourceIcon1:Int = R.drawable.ic_launcher_foreground
) {

    OutlinedTextField(
        value = value(),
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
                onNumericUnitTextFieldVisibilityChange(!currentNumericUnitTextFieldVisibility())
            }
        ),
        colors = TextFieldDefaults.colors(focusedTextColor = Color.White,
            focusedLabelColor = Color.White,
            focusedContainerColor = Color(0xff1d2a35),
            focusedIndicatorColor = Color(0xFFFFC107),
            focusedSupportingTextColor = Color.Red,
            focusedTrailingIconColor = Color(0xFFFFC107),
        )

    )
}
