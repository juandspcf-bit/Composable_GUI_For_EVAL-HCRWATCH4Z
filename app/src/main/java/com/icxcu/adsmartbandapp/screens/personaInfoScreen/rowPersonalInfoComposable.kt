package com.icxcu.adsmartbandapp.screens.personaInfoScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icxcu.adsmartbandapp.R

@Composable
fun RowOptionComposable(
    getFieldValue: () -> String,
    getVisibilityState: () -> Boolean,
    placeHolder: String,
    onClick: () -> Unit,
    resourceIcon1: Int = R.drawable.ic_launcher_foreground,
    disableRow: Boolean = false,
    textField: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier.alpha(
            if (disableRow.not()) {
                1f
            } else {
                0.5f
            }
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val boxModifier = Modifier
            .fillMaxWidth(0.85f)
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = Color.DarkGray)
            .border(
                BorderStroke(width = 1.dp, color = Color.Green),
                shape = RoundedCornerShape(size = 12.dp)
            )

        Box(
            modifier =
            if (disableRow.not()) {
                boxModifier
                    .clickable {
                        onClick()
                    }

            } else {
                boxModifier
            },


            ) {

            val displayName = if (getFieldValue() == "") {
                placeHolder
            } else {
                getFieldValue()
            }

            Text(
                displayName,
                textAlign = TextAlign.Start,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
            )
        }

        val iconModifier = Modifier
            .fillMaxWidth(1f)
            .size(50.dp)

        Icon(
            painter = painterResource(resourceIcon1),
            contentDescription = "Date Range",
            tint = Color.White,
            modifier =
            if (disableRow.not()) {
                iconModifier
                    .clickable {
                        onClick()
                    }
            } else {
                iconModifier
            }

        )
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        AnimatedVisibility(
            visible = getVisibilityState(),
            enter = expandVertically(animationSpec = tween(durationMillis = 1000)),
            exit = slideOutVertically()
        ) {
            textField()
        }
    }

}

@Preview
@Composable
fun RowPersonalInfoComposablePreview() {
    RowOptionComposable(
        { "data field" },
        { false },
        "data field",
        { },
        resourceIcon1 = R.drawable.monitor_weight_48px,
        textField = { }
    )
}

