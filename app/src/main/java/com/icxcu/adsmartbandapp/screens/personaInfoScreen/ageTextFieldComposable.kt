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
fun WeightTextFieldComposable(
    currentWeight: () -> String,
    currentWeightTextFieldVisibility: () -> Boolean,
    onWeightTextChange: (String) -> Unit,
    onWeightTextFieldVisibilityChange: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(end = 20.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .background(color = Color(0xFFE91E63))
        ) {

            val displayWeight = if (currentWeight() == "") {
                "Your age"
            } else {
                currentWeight()
            }

            Text(
                displayWeight,
                textAlign = TextAlign.Start,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
            )
        }

        Icon(
            painter = painterResource(R.drawable.baseline_point_of_sale_24),
            contentDescription = "Age",
            tint = Color(0xFFFFC107),
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onWeightTextFieldVisibilityChange(!currentWeightTextFieldVisibility())
                }
        )
    }

    AnimatedVisibility(
        visible = currentWeightTextFieldVisibility(),
        enter = expandVertically(animationSpec = tween(durationMillis = 1000)),
        exit = slideOutVertically()
    ) {
        WeightTexField(
            value = currentWeight,
            onWeightTextChange = onWeightTextChange,
            currentWeightTextFieldVisibility,
            onWeightTextFieldVisibilityChange,
        )
    }


}


@Composable
fun WeightTexField(
    value: () -> String,
    onWeightTextChange: (String) -> Unit,
    currentWeightTextFieldVisibility: () -> Boolean,
    onWeightTextFieldVisibilityChange: (Boolean) -> Unit,
) {

    OutlinedTextField(
        value = value(),
        onValueChange = onWeightTextChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        label = { Text("Your weight") },
        modifier = Modifier.padding(10.dp)
            .background(Color(0xff1d2a35)),
        textStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.baseline_point_of_sale_24),
                contentDescription = "weight",
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onWeightTextFieldVisibilityChange(!currentWeightTextFieldVisibility())
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
