package com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icxcu.adsmartbandapp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun DateTextFieldComposable(
    getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    onDateTextChange: (String) -> Unit,
    onDateTextFieldVisibilityChange: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {  },
                        onDoubleTap = { /* Double Tap Detected */ },
                        onLongPress = { /* Long Press Detected */ },
                        onTap = { onDateTextFieldVisibilityChange(!getPersonalInfoDataStateState().dateTextFieldVisibility) }
                    )
                }
                .fillMaxWidth(0.8f)
                .padding(end = 20.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .background(color = Color(0xFFE91E63))
        ) {

            val displayDate = if (getPersonalInfoDataStateState().date == "") {
                "Your birthdate"
            } else {
                val trimmed = if (getPersonalInfoDataStateState().date.length >= 8) getPersonalInfoDataStateState().date.substring(0..7) else getPersonalInfoDataStateState().date
                var out = ""
                for (i in trimmed.indices) {
                    out += trimmed[i]
                    if (i % 2 == 1 && i < 4) out += "/"
                }

                Log.d("FilteringForm", "PersonalInfoDBHandler: $out")

                try {
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    LocalDate.parse(out, formatter)
                }catch (e: DateTimeParseException){
                    out = "Enter a valid date"
                }
                out
            }

            Text(
                displayDate,
                textAlign = TextAlign.Start,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
            )
        }

        Icon(
            painter = painterResource(R.drawable.baseline_date_range_24),
            contentDescription = "Date Range",
            tint = Color(0xFFFFC107),
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onDateTextFieldVisibilityChange(!getPersonalInfoDataStateState().dateTextFieldVisibility)
                }
        )
    }

    AnimatedVisibility(
        visible = getPersonalInfoDataStateState().dateTextFieldVisibility,
        enter = expandVertically(animationSpec = tween(durationMillis = 1000)),
        exit = slideOutVertically()
    ) {
        DateTexField(
            getPersonalInfoDataStateState,
            onDateTextChange = onDateTextChange,
            onDateTextFieldVisibilityChange,
        )
    }


}


@Composable
fun DateTexField(
    getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    onDateTextChange: (String) -> Unit,
    onDateTextFieldVisibilityChange: (Boolean) -> Unit,
) {

    OutlinedTextField(
        value = getPersonalInfoDataStateState().date,
        onValueChange = onDateTextChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        label = { Text("Your Birthday") },
        modifier = Modifier.padding(10.dp)
            .background(Color(0xff1d2a35)),
        textStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Birthday Icon"
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onDateTextFieldVisibilityChange(!getPersonalInfoDataStateState().dateTextFieldVisibility)
            }
        ),
        visualTransformation = DateTransformation(),
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


class DateTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text)
    }
}

fun dateFilter(text: AnnotatedString): TransformedText {

    val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 2 == 1 && i < 4) out += "/"
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 3) return offset +1
            if (offset <= 8) return offset +2
            return 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <=2) return offset
            if (offset <=5) return offset -1
            if (offset <=10) return offset -2
            return 8
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}