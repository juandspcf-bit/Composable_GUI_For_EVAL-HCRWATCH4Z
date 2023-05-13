package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UpdateAlertDialog(
    setVisibilityAlertDialogStatusPersonalInfo: (Boolean) -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back button.
            // If you want to disable that functionality, simply leave this block empty.
            setVisibilityAlertDialogStatusPersonalInfo(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // perform the confirm action and
                    // close the dialog
                    setVisibilityAlertDialogStatusPersonalInfo(false)
                }
            ) {
                Text(
                    text = "Confirm",
                    color = Color(0xFFDCE775),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    // close the dialog
                    setVisibilityAlertDialogStatusPersonalInfo(false)
                }
            ) {
                Text(
                    text = "Dismiss",
                    color = Color(0xFFDCE775),
                )
            }
        },
        title = {
            Text(text = "My Data")
        },
        text = {
            Text(text = "Your data was added successfully")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .background(Color.Transparent)
            .border(2.dp, Color.Red, RoundedCornerShape(20.dp)),
        containerColor = Color(0xFF000000),
        textContentColor = Color.White,
        titleContentColor = Color.White,
        shape = RoundedCornerShape(20.dp)


    )
}