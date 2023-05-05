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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
    currentName: () -> String,
    currentNameTextFieldVisibility: () -> Boolean,
    onTextChange: (String) -> Unit,
    onNameTextFieldVisibilityChange: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(end = 20.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .background(color = Color(0xFFE91E63))
        ) {

            val displayName = if (currentName() == "") {
                "Your name"
            } else {
                currentName()
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
            painter = painterResource(R.drawable.baseline_person_24),
            contentDescription = "Date Range",
            tint = Color(0xFFC73030),
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onNameTextFieldVisibilityChange(!currentNameTextFieldVisibility())
                }
        )
    }

    AnimatedVisibility(
        visible = currentNameTextFieldVisibility(),
        enter = expandVertically(animationSpec = tween(durationMillis = 1000)),
        exit = slideOutVertically()
    ) {
        NameTexField(
            value = currentName,
            onTextChange = onTextChange,
            currentNameTextFieldVisibility,
            onNameTextFieldVisibilityChange,
        )
    }


}


@Composable
fun NameTexField(
    value: () -> String,
    onTextChange: (String) -> Unit,
    currentNameTextFieldVisibility: () -> Boolean,
    onNameTextFieldVisibilityChange: (Boolean) -> Unit,
) {

    OutlinedTextField(
        value = value(),
        onValueChange = onTextChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        label = { Text("Your Name") },
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person Icon"
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onNameTextFieldVisibilityChange(!currentNameTextFieldVisibility())
            }
        )

    )
}