package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.data.entities.PersonalInfo

@Composable
fun PersonalInfoContent(
    getPersonalInfoDataStateState: () -> PersonalInfoDataState,
    getPersonalInfoListReadFromDB: () -> List<PersonalInfo>,
    validatePersonalInfo: () -> List<String> = { listOf() },
    getInvalidAlertDialogState: () -> InvalidAlertDialogState,
    getUpdateAlertDialogState: () -> UpdateAlertDialogState,
    updatePersonalData: (PersonalInfo) -> Unit = {},
    insertPersonalData: (PersonalInfo) -> Unit = {},
) {

    val scrollState = rememberScrollState()

    var selectedUri by rememberSaveable { mutableStateOf<Uri?>(null) }
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
                    }
                    ,
            ) {

                AsyncImage(
                    modifier = Modifier.fillMaxSize()
                    ,
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(context)
                        .data(if(selectedUri !=null){selectedUri}else {
                            R.drawable.user
                        })
                        .build(),
                    contentDescription = null
                )
            }

            NameTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataStateState,
                onTextChange = getPersonalInfoDataStateState().onTextChange,
                onNameTextFieldVisibilityChange = getPersonalInfoDataStateState().onNameTextFieldVisibilityChange
            )

            DateTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataStateState,
                onDateTextChange = getPersonalInfoDataStateState().onDateTextChange,
                onDateTextFieldVisibilityChange = getPersonalInfoDataStateState().onDateTextFieldVisibilityChange
            )

            NumericWeightTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataStateState,
                onNumericUnitTextChange = getPersonalInfoDataStateState().onWeightTextChange,
                onNumericUnitTextFieldVisibilityChange = getPersonalInfoDataStateState().onWeightTextFieldVisibilityChange,
                unit = "Kg",
                contentDescription = "weight",
                resourceIcon1 = R.drawable.baseline_point_of_sale_24,
                validator = ValidatorsPersonalField.weightValidator
            )

            NumericHeightTextFieldComposable(
                getPersonalInfoDataStateState = getPersonalInfoDataStateState,
                onNumericUnitTextChange = getPersonalInfoDataStateState().onHeightTextChange,
                onNumericUnitTextFieldVisibilityChange = getPersonalInfoDataStateState().onHeightTextFieldVisibilityChange,
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
                    if (personalInfoListReadFromDB.isNotEmpty() && personalInfoListReadFromDB[0].id != -1) {
                        personalInfoListReadFromDB[0].name = getPersonalInfoDataStateState().name
                        personalInfoListReadFromDB[0].birthdate =
                            getPersonalInfoDataStateState().date
                        personalInfoListReadFromDB[0].weight =
                            getPersonalInfoDataStateState().weight.toDouble()
                        personalInfoListReadFromDB[0].height =
                            getPersonalInfoDataStateState().height.toDouble()
                        Log.d("Flow", "PersonalInfoFormScaffold: updating")
                        updatePersonalData(personalInfoListReadFromDB[0])
                    } else {
                        val personalInfo = PersonalInfo()
                        personalInfo.name = getPersonalInfoDataStateState().name
                        personalInfo.birthdate = getPersonalInfoDataStateState().date
                        personalInfo.weight = getPersonalInfoDataStateState().weight.toDouble()
                        personalInfo.height = getPersonalInfoDataStateState().height.toDouble()
                        Log.d("Flow", "PersonalInfoFormScaffold: inserting")
                        insertPersonalData(personalInfo)
                    }


                }
            ) {
                Text(text = "Save info", color = Color.White, textAlign = TextAlign.Center)

            }

            if (getInvalidAlertDialogState().alertDialogPersonalFieldVisibility) {
                ValidationAlertDialog(
                    getInvalidAlertDialogState,
                )
            }

            if (getUpdateAlertDialogState().alertDialogUPersonalFieldVisibility) {
                UpdateAlertDialog(getUpdateAlertDialogState().setVisibilityAlertDialogStatusPersonalInfoU)
            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun PersonalInfoContentPreview() {
    PersonalInfoContent(
        getPersonalInfoDataStateState = { PersonalInfoDataState() },
        getPersonalInfoListReadFromDB = { listOf(PersonalInfo()) },
        validatePersonalInfo = { listOf() },
        getInvalidAlertDialogState = { InvalidAlertDialogState() },
        getUpdateAlertDialogState = { UpdateAlertDialogState() },
        updatePersonalData = {},
        insertPersonalData = {},
    )
}