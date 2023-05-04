package com.icxcu.adsmartbandapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icxcu.adsmartbandapp.R

@Composable
fun PersonalDataForm(){
    var name by remember {
        mutableStateOf("")
    }
    val currentName = {
        name
    }

    val onTextChange = { valueFromTextField:String ->
        name = valueFromTextField
    }

    var nameTextFieldVisibility by remember {
        mutableStateOf(false)
    }

    val currentNameTextFieldVisibility = {
        nameTextFieldVisibility
    }

    val onNameTextFieldVisibilityChange = { visibility:Boolean ->
        nameTextFieldVisibility = visibility
    }


    var date by remember {
        mutableStateOf("")
    }
    val currentDate = {
        date
    }

    val onDateTextChange = { valueFromDateTextField:String ->
        date = valueFromDateTextField
    }

    var dateTextFieldVisibility by remember {
        mutableStateOf(false)
    }

    val currentDateTextFieldVisibility = {
        dateTextFieldVisibility
    }

    val onDateTextFieldVisibilityChange = { visibility:Boolean ->
        dateTextFieldVisibility = visibility
    }


    Box(contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly) {

            NameTextFieldComposable(
                currentName = currentName,
                currentNameTextFieldVisibility = currentNameTextFieldVisibility,
                onTextChange = onTextChange,
                onNameTextFieldVisibilityChange = onNameTextFieldVisibilityChange
            )

            DateTextFieldComposable(
                currentDate = currentDate,
                currentDateTextFieldVisibility = currentDateTextFieldVisibility,
                onDateTextChange = onDateTextChange,
                onDateTextFieldVisibilityChange = onDateTextFieldVisibilityChange
            )


        }
    }



}

@Composable
fun NameTextFieldComposable(
    currentName:()->String,
    currentNameTextFieldVisibility: ()-> Boolean,
    onTextChange:(String)->Unit,
    onNameTextFieldVisibilityChange: (Boolean)->Unit,
){
    Row (verticalAlignment = Alignment.CenterVertically){

        Box(modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(end = 20.dp,)
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = Color(0xFFE91E63))
        ) {

            var displayName = if(currentName()==""){
                "Your name"
            }else{
                currentName()
            }

            Text(displayName,
                textAlign = TextAlign.Start,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp, top=10.dp, bottom = 10.dp)
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
    value:()->String,
    onTextChange:(String)->Unit,
    currentNameTextFieldVisibility: ()-> Boolean,
    onNameTextFieldVisibilityChange: (Boolean)->Unit,
){

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
        textStyle = TextStyle(fontWeight = FontWeight.Bold,
            fontSize = 30.sp),
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

@Composable
fun DateTextFieldComposable(
    currentDate:()->String,
    currentDateTextFieldVisibility: ()-> Boolean,
    onDateTextChange:(String)->Unit,
    onDateTextFieldVisibilityChange: (Boolean)->Unit,
){
    Row (verticalAlignment = Alignment.CenterVertically){

        Box(modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(end = 20.dp,)
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = Color(0xFFE91E63))
        ) {

            var displayDate = if(currentDate()==""){
                "Your birthdate"
            }else{
                currentDate()
            }

            Text(displayDate,
                textAlign = TextAlign.Start,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp, top=10.dp, bottom = 10.dp)
            )
        }

        Icon(
            painter = painterResource(R.drawable.baseline_date_range_24),
            contentDescription = "Date Range",
            tint = Color(0xFFC73030),
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onDateTextFieldVisibilityChange(!currentDateTextFieldVisibility())
                }

        )
    }

    AnimatedVisibility(
        visible = currentDateTextFieldVisibility(),
        enter = expandVertically(animationSpec = tween(durationMillis = 1000)),
        exit = slideOutVertically()
    ) {
        DateTexField(
            value = currentDate,
            onDateTextChange = onDateTextChange,
            currentDateTextFieldVisibility = currentDateTextFieldVisibility,
            onDateTextFieldVisibilityChange = onDateTextFieldVisibilityChange,
        )
    }
}

@Composable
fun DateTexField(
    value:()->String,
    onDateTextChange:(String)->Unit,
    currentDateTextFieldVisibility: ()-> Boolean,
    onDateTextFieldVisibilityChange: (Boolean)->Unit,
){

    OutlinedTextField(
        value = value(),
        onValueChange = onDateTextChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        label = { Text("Your birthdate") },
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(fontWeight = FontWeight.Bold,
            fontSize = 30.sp),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Birthdate"
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onDateTextFieldVisibilityChange(!currentDateTextFieldVisibility())
            }
        ),
        visualTransformation = DateTransformation()

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

@Preview(showBackground = true)
@Composable
fun PersonalDataFormPreview() {
    PersonalDataForm()
}