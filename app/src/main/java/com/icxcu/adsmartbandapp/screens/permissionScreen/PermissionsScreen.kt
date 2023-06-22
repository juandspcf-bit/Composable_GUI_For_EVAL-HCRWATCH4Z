package com.icxcu.adsmartbandapp.screens.permissionScreen

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat


@Composable
fun PermissionsScreen(
    activity: Activity?,
    permissionDataTypeList: MutableList<PermissionDataType>,
    navLambda: () ->Unit,
    allPermissionsGranted: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff1d2a35)),
        verticalArrangement = Arrangement.Top
    ) {


        repeat(permissionDataTypeList.size){
            PermissionType(
                permissionDataTypeList[it].message,
                permissionDataTypeList[it].permissionType,
                permissionDataTypeList[it].permissionLauncher,
                permissionDataTypeList[it].permissionGranted,
            )
        }

        val annotatedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            ) {
                append("You can also enable permissions ")
            }

            pushStringAnnotation(
                tag = "settings",
                annotation = "settings"
            )
            withStyle(
                style = SpanStyle(
                    color = Color(0xffafaf00),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            ) {
                append("in the settings")
            }
            pop()
        }

        ClickableText(
            modifier = Modifier.padding(12.dp),
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "settings",// tag which you used in the buildAnnotatedString
                    start = offset,
                    end = offset
                )[0].let {
                    // onClick block
                    // open settings
                    val intentSettings =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intentSettings.data =
                        Uri.fromParts("package", "com.icxcu.adsmartbandapp", null)
                    activity?.startActivity(intentSettings)
                }
            }
        )

        Button(
            enabled = allPermissionsGranted,
            modifier = Modifier
                .padding(12.dp),
            shape = RoundedCornerShape(size = 6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff04aa6d),
                disabledContainerColor = Color(0xff027146)
            ),
            onClick = {
                navLambda()
            }
        ) {
            Text(text = "collect your data")
        }


    }


}


@Composable
fun PermissionType(
    message: String = "",
    permissionType: String,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    permissionStatusGrant:Boolean
) {


    Row(Modifier.padding(6.dp)) {
        Text(
            text = message,
            color=Color.White,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .weight(0.7f)
                .padding(7.dp)
        )

        Button(
            enabled = !permissionStatusGrant,
            modifier = Modifier
                .weight(0.3f)
                .padding(5.dp),
            shape = RoundedCornerShape(size = 6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffafaf00),
                disabledContainerColor = Color(0xff787800)
            ),
            onClick = {
                if (!permissionStatusGrant) {
                    permissionLauncher.launch(permissionType)
                }
            }
        ) {
            Text(text = "request", color = Color.Black)
        }
    }
}




fun isPermissionGranted(activity: Activity?, permissionType: String): Boolean {
    return activity?.let {
        ContextCompat.checkSelfPermission(
            it,
            permissionType
        )
    } == PackageManager.PERMISSION_GRANTED
}

@Preview(showBackground = true)
@Composable
fun PermissionsScreenPreview() {

/*    PermissionsScreen(
        LocalContext.current as Activity,
    viewModel: PermissionsViewModel,
    navLambda: () ->Unit
    )*/
}