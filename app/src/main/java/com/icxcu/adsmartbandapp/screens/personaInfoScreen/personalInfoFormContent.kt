package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import java.io.IOException
import java.io.OutputStream
import java.util.Objects


@Composable
fun PersonalInfoContent(
    getPersonalInfoDataState: () -> PersonalInfoDataState,
    getPersonalInfoListReadFromDB: () -> List<PersonalInfo>,
    validatePersonalInfo: () -> List<String> = { listOf() },
    getInvalidAlertDialogState: () -> InvalidAlertDialogState,
    updatePersonalData: (PersonalInfo) -> Unit = {},
    insertPersonalData: (PersonalInfo) -> Unit = {},
) {

    var selectedUri by rememberSaveable { mutableStateOf<Uri?>( null) }
    Log.d("AVATAR", "Initial values: ${getPersonalInfoDataState().uri}, ${getPersonalInfoDataState().name}")


    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            selectedUri = uri
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
                    modifier = Modifier.fillMaxSize()
                    ,
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(context)
                        .data(
                            if(selectedUri!=null){
                                selectedUri
                            }else if(getPersonalInfoDataState().uri!=""){
                                Uri.parse(getPersonalInfoDataState().uri)
                            }else{
                                R.drawable.user
                            }
                        )
                        .build(),
                    contentDescription = null
                )
            }

            NameTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataState,
                onTextChange = getPersonalInfoDataState().onTextChange,
                onNameTextFieldVisibilityChange = getPersonalInfoDataState().onNameTextFieldVisibilityChange
            )

            DateTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataState,
                onDateTextChange = getPersonalInfoDataState().onDateTextChange,
                onDateTextFieldVisibilityChange = getPersonalInfoDataState().onDateTextFieldVisibilityChange
            )

            NumericWeightTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataState,
                onNumericUnitTextChange = getPersonalInfoDataState().onWeightTextChange,
                onNumericUnitTextFieldVisibilityChange = getPersonalInfoDataState().onWeightTextFieldVisibilityChange,
                unit = "Kg",
                contentDescription = "weight",
                resourceIcon1 = R.drawable.baseline_point_of_sale_24,
                validator = ValidatorsPersonalField.weightValidator
            )

            NumericHeightTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataState,
                onNumericUnitTextChange = getPersonalInfoDataState().onHeightTextChange,
                onNumericUnitTextFieldVisibilityChange = getPersonalInfoDataState().onHeightTextFieldVisibilityChange,
                unit = "m",
                contentDescription = "height",
                resourceIcon1 = R.drawable.baseline_boy_24,
                validator = ValidatorsPersonalField.heightValidator
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 50.dp, end = 20.dp),
                onClick = {

                    if (validatePersonalInfo().isNotEmpty()) {
                        getInvalidAlertDialogState().alertDialogPersonalFieldVisibility = true
                        getInvalidAlertDialogState().invalidFields = validatePersonalInfo()
                        return@Button
                    }

                    val personalInfoListReadFromDB = getPersonalInfoListReadFromDB()
                    Log.d("AVATAR", "Update: ${getPersonalInfoDataState().uri}")
                    if (personalInfoListReadFromDB[0].id != -1) {



                        scope.launch(Dispatchers.IO) {
                            selectedUri?.let { uri ->

                                val imageUri = uri(context, uri)

                                val personalInfo = PersonalInfo().apply {
                                    id = getPersonalInfoDataState().id
                                    this.uri = imageUri?.toString() ?: ""
                                    macAddress = getPersonalInfoDataState().macAddress
                                    name = getPersonalInfoDataState().name
                                    birthdate = getPersonalInfoDataState().date
                                    weight = getPersonalInfoDataState().weight.toDouble()
                                    height = getPersonalInfoDataState().height.toDouble()
                                }
                                updatePersonalData(personalInfo)

                            }
                        }

                    } else {

                        scope.launch(Dispatchers.IO) {
                            selectedUri?.let { uri ->

                                val imageUri = uri(context, uri)

                                val personalInfo = PersonalInfo().apply{
                                    macAddress = personalInfoListReadFromDB[0].macAddress
                                    this.uri = imageUri?.toString() ?: ""
                                    name = getPersonalInfoDataState().name
                                    birthdate = getPersonalInfoDataState().date
                                    weight = getPersonalInfoDataState().weight.toDouble()
                                    height = getPersonalInfoDataState().height.toDouble()
                                }
                                insertPersonalData(personalInfo)

                            }
                        }
                    }
                }
            ) {
                Text(text = "Save info", color = Color.White, textAlign = TextAlign.Center)

            }







        }
    }

}

private fun uri(
    context: Context,
    uri: Uri,
): Uri? {
    var bitmap: Bitmap? = null
    var imageUri:Uri? = null
    val fos: OutputStream?

    try {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream.use {
            bitmap = BitmapFactory.decodeStream(it)
        }

        val resolver = context.contentResolver
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "myImage.jpg")
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")

        imageUri =
            resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        fos = imageUri?.let {
            resolver.openOutputStream(it)
        }

        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        Objects.requireNonNull(fos)

        Log.d("AVATAR", "PersonalInfoContent: $imageUri")
    } catch (e: IOException) {
        Log.d("AVATAR", "PersonalInfoContent: failed  $e")
    }
    return imageUri
}


@Preview(showBackground = true)
@Composable
fun PersonalInfoContentPreview() {
    PersonalInfoContent(
        getPersonalInfoDataState = { PersonalInfoDataState() },
        getPersonalInfoListReadFromDB = { listOf(PersonalInfo()) },
        validatePersonalInfo = { listOf() },
        getInvalidAlertDialogState = { InvalidAlertDialogState() },
        updatePersonalData = {},
        insertPersonalData = {},
    )
}