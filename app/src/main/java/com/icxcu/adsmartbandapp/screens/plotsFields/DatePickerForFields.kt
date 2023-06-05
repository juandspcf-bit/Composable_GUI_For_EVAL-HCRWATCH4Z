package com.icxcu.adsmartbandapp.screens.plotsFields

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.ui.theme.DialogsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogSample(
    modifyShowDialog: (Boolean) -> Unit,
    stateMiliSecondsDateDialogDatePickerSetter: (Long) -> Unit,
) {
    // Decoupled snackbar host state from scaffold state for demo purposes.
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)
    val openDialog = remember { mutableStateOf(true) }
// TODO demo how to read the selected date from the state.
    if (openDialog.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled =
            remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

        DialogsTheme(
        ){
            DatePickerDialog(
                onDismissRequest = {
                    openDialog.value = false
                    modifyShowDialog(false)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false

                            datePickerState.selectedDateMillis?.let {
                                stateMiliSecondsDateDialogDatePickerSetter(
                                    it
                                )
                            }
                            modifyShowDialog(false)
                        },
                        enabled = confirmEnabled.value
                    ) {
                        Text(
                            "OK",
                            color = Color(0xFFDCE775)
                        )
                    }
                },
                modifier = Modifier.border(2.dp, Color.Red, RoundedCornerShape(20.dp)),
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            modifyShowDialog(false)
                        }
                    ) {
                        Text(
                            "Cancel",
                            color = Color(0xFFDCE775)
                        )
                    }
                },
            ) {
                DatePicker(state = datePickerState)
            }
        }

    }
}