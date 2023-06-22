package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icxcu.adsmartbandapp.R

@Composable
fun NameTexField(
    getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    onTextChange: (String) -> Unit,
    onNameTextFieldVisibilityChange: () -> Unit,
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
                painter = painterResource(id = resourceIcon1),
                contentDescription = "Person Icon"
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onNameTextFieldVisibilityChange()
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

@Composable
fun NumericUnitTexField(
    getFieldValue: () -> String,
    onNumericUnitTextChange: (String) -> Unit,
    onNumericUnitTextFieldVisibilityChange: () -> Unit,
    contentDescription: String = "",
    suffix: String,
    resourceIcon1: Int = R.drawable.ic_launcher_foreground,

    ) {
    val numberValidated =
        ValidatorsPersonalField.weightValidator(getFieldValue())
    OutlinedTextField(
        value = numberValidated,
        onValueChange = onNumericUnitTextChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        label = { Text(contentDescription) },
        modifier = Modifier
            .padding(10.dp)
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
        suffix = { Text(text = suffix) },
        keyboardActions = KeyboardActions(
            onDone = {
                onNumericUnitTextFieldVisibilityChange()
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


@Composable
fun DateTexField(
    getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    onDateTextChange: (String) -> Unit,
    onDateTextFieldVisibilityChange: () -> Unit,
    contentDescription: String = "",
    resourceIcon1:Int = R.drawable.ic_launcher_foreground,
) {

    OutlinedTextField(
        value = getPersonalInfoDataStateState().date,
        onValueChange = onDateTextChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        label = { Text(contentDescription) },
        modifier = Modifier
            .padding(10.dp)
            .background(Color(0xff1d2a35)),
        textStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(resourceIcon1),
                contentDescription = "Birthday Icon"
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onDateTextFieldVisibilityChange()
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


class DateTransformation : VisualTransformation {
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
            if (offset <= 3) return offset + 1
            if (offset <= 8) return offset + 2
            return 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset - 1
            if (offset <= 10) return offset - 2
            return 8
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}