package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.MacAddress
import android.net.Uri
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



@Composable
fun PersonalInfoContent(
    macAddressDeviceBluetooth: String,
    getPersonalInfoDataState: () -> PersonalInfoDataState,
    getPersonalInfoListReadFromDB: () -> List<PersonalInfo>,
    validatePersonalInfo: () -> List<String> = { listOf() },
    getInvalidAlertDialogState: () -> InvalidAlertDialogState,
    updatePersonalData: (PersonalInfo) -> Unit = {},
    insertPersonalData: (PersonalInfo) -> Unit = {},
) {

    var selectedUri by rememberSaveable { mutableStateOf<Uri?>( null) }
    var initialBitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            selectedUri = uri
        }


    LaunchedEffect(key1 = true,){
        Log.d("AVATAR", "Launch Effect ")
        launch(Dispatchers.IO){
            try {
                val fin: FileInputStream = context.openFileInput("${macAddressDeviceBluetooth}.jpg")
                val decodedBitmap = BitmapFactory.decodeStream(fin)
                withContext(Dispatchers.Main){
                    initialBitmap = decodedBitmap
                }
                fin.close()
            }catch (e: FileNotFoundException){
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
                    modifier = Modifier.fillMaxSize()
                    ,
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(context)
                        .data(
                            if(selectedUri!=null){
                                selectedUri
                            }else if(initialBitmap!=null){
                                initialBitmap
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
                onNameTextFieldVisibilityChange = getPersonalInfoDataState().onNameTextFieldVisibilityChange,
                resourceIcon1 = R.drawable.person_48px
            )

            DateTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataState,
                onDateTextChange = getPersonalInfoDataState().onDateTextChange,
                onDateTextFieldVisibilityChange = getPersonalInfoDataState().onDateTextFieldVisibilityChange,
                resourceIcon1 = R.drawable.calendar_month_48px,
            )

            NumericWeightTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataState,
                onNumericUnitTextChange = getPersonalInfoDataState().onWeightTextChange,
                onNumericUnitTextFieldVisibilityChange = getPersonalInfoDataState().onWeightTextFieldVisibilityChange,
                unit = "Kg",
                contentDescription = "weight",
                resourceIcon1 = R.drawable.monitor_weight_48px,
                validator = ValidatorsPersonalField.weightValidator
            )

            NumericHeightTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataState,
                onNumericUnitTextChange = getPersonalInfoDataState().onHeightTextChange,
                onNumericUnitTextFieldVisibilityChange = getPersonalInfoDataState().onHeightTextFieldVisibilityChange,
                unit = "m",
                contentDescription = "height",
                resourceIcon1 = R.drawable.boy_48px,
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
                    if (personalInfoListReadFromDB[0].id != -1) {

                        scope.launch(Dispatchers.IO) {
                            selectedUri?.let { uri ->
                                storeImageProfile(context, uri, macAddressDeviceBluetooth)
                            }

                            val personalInfo = PersonalInfo().apply {
                                id = getPersonalInfoDataState().id
                                macAddress = getPersonalInfoDataState().macAddress
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
                                storeImageProfile(context, uri, macAddressDeviceBluetooth)
                            }

                            val personalInfo = PersonalInfo().apply{
                                macAddress = personalInfoListReadFromDB[0].macAddress
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
                Text(text = "Save info", color = Color.White, textAlign = TextAlign.Center)

            }







        }
    }

}

private fun storeImageProfile(
    context: Context,
    uri: Uri,
    macAddress: String
) {
    var bitmap: Bitmap?

    try {
        val resolver = context.contentResolver

        val inputStream = resolver.openInputStream(uri)
        inputStream.use {
            bitmap = BitmapFactory.decodeStream(it)
        }

        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 256,256)

        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        val nameFile = "${macAddress}.jpg"
        Log.d("STORE_IMAGE", "storeImageProfile: $nameFile")
        context.openFileOutput("${macAddress}.jpg", Context.MODE_PRIVATE).use {
            it.write(byteArray)
        }

/*        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "myImage.jpg")
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
        //contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.getDataDirectory().absolutePath)
        imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        fos = imageUri?.let {
            resolver.openOutputStream(it,"wt")
        }


        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        Objects.requireNonNull(fos)

        Log.d("AVATAR", "PersonalInfoContent: $imageUri")*/

/*        */


    } catch (e: IOException) {
        Log.d("AVATAR", "PersonalInfoContent: failed  $e")
    }
}


@Preview(showBackground = true)
@Composable
fun PersonalInfoContentPreview() {
    PersonalInfoContent(
        macAddressDeviceBluetooth = "",
        getPersonalInfoDataState = { PersonalInfoDataState() },
        getPersonalInfoListReadFromDB = { listOf(PersonalInfo()) },
        validatePersonalInfo = { listOf() },
        getInvalidAlertDialogState = { InvalidAlertDialogState() },
        updatePersonalData = {},
        insertPersonalData = {},
    )
}