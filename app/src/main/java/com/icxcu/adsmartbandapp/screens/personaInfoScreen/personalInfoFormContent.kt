package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun PersonalInfoContent(
    getLauncherActivity: () -> ActivityResultLauncher<Intent>?,
    getPersonalInfoDataState: () -> PersonalInfoDataState,
    validatePersonalInfo: () -> List<String> = { listOf() },
    getInvalidAlertDialogState: () -> InvalidAlertDialogState,
    updatePersonalData: (PersonalInfo) -> Unit = {},
    insertPersonalData: (PersonalInfo) -> Unit = {},
) {

    var selectedUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var initialBitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            selectedUri = uri
        }


    LaunchedEffect(key1 = true) {
        Log.d("AVATAR", "Launch Effect ")
        launch(Dispatchers.IO) {
            try {
                val fin: FileInputStream = context.openFileInput("myProfile.jpg")
                val decodedBitmap = BitmapFactory.decodeStream(fin)
                withContext(Dispatchers.Main) {
                    initialBitmap = decodedBitmap
                }
                fin.close()
            } catch (e: FileNotFoundException) {
                Log.d("AVATAR", "PersonalInfoContent: not profile image found")
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff1d2a35)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(start = 15.dp, end = 15.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .padding(30.dp)
                    .size(190.dp)
                    .clip(CircleShape)
                    .background(Color(0xff1d2a35))
                    .border(2.dp, Color.White, CircleShape)
                    .clickable {
                        launcher.launch(arrayOf("image/*"))
                    },
            ) {

                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(context)
                        .data(
                            if (selectedUri != null) {
                                selectedUri
                            } else if (initialBitmap != null) {
                                initialBitmap
                            } else {
                                R.drawable.user
                            }
                        )
                        .build(),
                    contentDescription = null
                )
            }

            RowOptionComposable(
                getFieldValue = { getPersonalInfoDataState().name },
                getVisibilityState = { getPersonalInfoDataState().nameTextFieldVisibility },
                placeHolder = "Your Name",
                onClick = getPersonalInfoDataState().onNameTextFieldVisibilityChange,
                resourceIcon1 = R.drawable.person_48px
            ) {
                NameTexField(
                    getPersonalInfoDataState,
                    onTextChange = getPersonalInfoDataState().onNameTextChange,
                    getPersonalInfoDataState().onNameTextFieldVisibilityChange,
                    R.drawable.person_48px
                )
            }

            RowOptionComposable(
                getFieldValue = { if (getPersonalInfoDataState().date == "") {
                    ""
                } else {
                    val trimmed =
                        if (getPersonalInfoDataState().date.length >= 8) getPersonalInfoDataState().date.substring(
                            0..7
                        ) else getPersonalInfoDataState().date
                    var out = ""
                    for (i in trimmed.indices) {
                        out += trimmed[i]
                        if (i % 2 == 1 && i < 4) out += "/"
                    }

                    try {
                        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        LocalDate.parse(out, formatter)
                    } catch (e: DateTimeParseException) {
                        out = ""
                    }
                    out
                } },
                getVisibilityState = { getPersonalInfoDataState().dateTextFieldVisibility },
                placeHolder = "Your birthdate",
                onClick = getPersonalInfoDataState().onDateTextFieldVisibilityChange,
                resourceIcon1 = R.drawable.calendar_month_48px
            ) {
                DateTexField(
                    getPersonalInfoDataState,
                    onDateTextChange = getPersonalInfoDataState().onDateTextChange,
                    getPersonalInfoDataState().onDateTextFieldVisibilityChange,
                    contentDescription = "Your birthdate",
                    R.drawable.calendar_month_48px,
                )
            }

            RowOptionComposable(
                getFieldValue = {
                    val numberValidated =
                        ValidatorsPersonalField.weightValidator(getPersonalInfoDataState().weight)
                    if(numberValidated!=""){
                        "$numberValidated Kg"
                    }else{
                        ""
                    }
                },
                getVisibilityState = { getPersonalInfoDataState().weightTextFieldVisibility },
                placeHolder = "Your weight",
                onClick = getPersonalInfoDataState().onWeightTextFieldVisibilityChange,
                resourceIcon1 = R.drawable.monitor_weight_48px
            ) {
                NumericUnitTexField(
                    getFieldValue = { getPersonalInfoDataState().weight },
                    onNumericUnitTextChange = getPersonalInfoDataState().onWeightTextChange,
                    onNumericUnitTextFieldVisibilityChange = getPersonalInfoDataState().onWeightTextFieldVisibilityChange,
                    contentDescription = "Your weight",
                    suffix = "Kg",
                    resourceIcon1 = R.drawable.monitor_weight_48px
                )
            }

            RowOptionComposable(
                getFieldValue = {
                    val numberValidated =
                        ValidatorsPersonalField.heightValidator(getPersonalInfoDataState().height)
                    if(numberValidated!=""){
                        "$numberValidated m"
                    }else{
                        ""
                    }
                },
                getVisibilityState = { getPersonalInfoDataState().heightTextFieldVisibility },
                placeHolder = "Your height",
                onClick = getPersonalInfoDataState().onHeightTextFieldVisibilityChange,
                resourceIcon1 = R.drawable.boy_48px
            ) {
                NumericUnitTexField(
                    getFieldValue = { getPersonalInfoDataState().height },
                    onNumericUnitTextChange = getPersonalInfoDataState().onHeightTextChange,
                    onNumericUnitTextFieldVisibilityChange = getPersonalInfoDataState().onHeightTextFieldVisibilityChange,
                    contentDescription = "Your height",
                    suffix = "m",
                    resourceIcon1 = R.drawable.boy_48px
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 50.dp, end = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFB74D),
                ),
                onClick = {

                    if (validatePersonalInfo().isNotEmpty()) {
                        getInvalidAlertDialogState().alertDialogPersonalFieldVisibility = true
                        getInvalidAlertDialogState().invalidFields = validatePersonalInfo()
                        return@Button
                    }

                    if (getPersonalInfoDataState().id != -1) {

                        scope.launch(Dispatchers.IO) {
                            selectedUri?.let { uri ->
                                storeImageProfile(context, uri)
                            }

                            val personalInfo = PersonalInfo().apply {
                                id = getPersonalInfoDataState().id
                                name = getPersonalInfoDataState().name
                                birthdate = getPersonalInfoDataState().date
                                weight = getPersonalInfoDataState().weight.toDouble()
                                height = getPersonalInfoDataState().height.toDouble()
                            }
                            updatePersonalData(personalInfo)


                        }

                    } else {

                        scope.launch(Dispatchers.IO) {
                            selectedUri?.let { uri ->
                                storeImageProfile(context, uri)
                            }

                            val personalInfo = PersonalInfo().apply {
                                name = getPersonalInfoDataState().name
                                birthdate = getPersonalInfoDataState().date
                                weight = getPersonalInfoDataState().weight.toDouble()
                                height = getPersonalInfoDataState().height.toDouble()
                            }
                            insertPersonalData(personalInfo)
                        }
                    }
                }
            ) {
                Text(text = "Save info", color = Color.Black, textAlign = TextAlign.Center)

            }

        }
    }

}

private fun storeImageProfile(
    context: Context,
    uri: Uri,
) {
    var bitmap: Bitmap?

    try {
        val resolver = context.contentResolver

        val inputStream = resolver.openInputStream(uri)
        inputStream.use {
            bitmap = BitmapFactory.decodeStream(it)
        }

        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 256, 256)

        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        context.openFileOutput("myProfile.jpg", Context.MODE_PRIVATE).use {
            it.write(byteArray)
        }

    } catch (e: IOException) {
        Log.d("AVATAR", "PersonalInfoContent: failed  $e")
    }
}


@Preview(showBackground = true)
@Composable
fun PersonalInfoContentPreview() {
    PersonalInfoContent(
        getLauncherActivity = { null },
        getPersonalInfoDataState = { PersonalInfoDataState() },
        validatePersonalInfo = { listOf() },
        getInvalidAlertDialogState = { InvalidAlertDialogState() },
        updatePersonalData = {},
    ) {}
}