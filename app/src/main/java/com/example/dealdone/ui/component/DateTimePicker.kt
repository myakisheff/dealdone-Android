package com.example.dealdone.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.dealdone.R
import com.example.dealdone.data.extensions.formatToDateString
import com.example.dealdone.data.extensions.formatToTimeString
import com.example.dealdone.ui.theme.DealDoneTheme
import java.util.Calendar

/**
 * Component with 2 buttons and checkbox with dialogs for date
 *
 * @param isInfinityTime if `true` buttons not enabled
 * @param onInfinityTimeChanged called on checkbox change
 * @param currentCalendar current time and date of task
 * @param onTimeConfirm calls when time selected
 * @param onTimeDismiss calls when time not selected
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePicker(
    isInfinityTime: Boolean,
    onInfinityTimeChanged: (Boolean) -> Unit,
    currentCalendar: Calendar?,
    onTimeConfirm: (TimePickerState) -> Unit,
    onTimeDismiss: () -> Unit,
    onDateConfirm: (Long?) -> Unit,
    onDateDismiss: () -> Unit,
    isShowingTimePicker: Boolean,
    isShowingDatePicker: Boolean,
    onTimePickerClick: () -> Unit,
    onDatePickerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement
                .spacedBy(dimensionResource(R.dimen.extra_small_padding)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                enabled = !isInfinityTime,
                onClick = onDatePickerClick,
                shape = RoundedCornerShape(
                    topStart = dimensionResource(R.dimen.small_padding),
                    topEnd = 0.dp,
                    bottomEnd = 0.dp,
                    bottomStart = dimensionResource(R.dimen.small_padding)
                ),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                    disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = currentCalendar?.formatToDateString() ?: Calendar.getInstance().formatToDateString()
                )
            }
            Button(
                enabled = !isInfinityTime,
                onClick = onTimePickerClick,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = dimensionResource(R.dimen.small_padding),
                    bottomEnd = dimensionResource(R.dimen.small_padding),
                    bottomStart = 0.dp
                    ),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                    disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = currentCalendar?.formatToTimeString() ?: Calendar.getInstance().formatToTimeString()
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isInfinityTime,
                onCheckedChange = onInfinityTimeChanged,
            )
            Text(
                text = stringResource(R.string.no_deadline)
            )
        }
    }

    if(isShowingTimePicker) {
        CustomTimePicker(
            currentTime = currentCalendar ?: Calendar.getInstance(),
            onConfirm = onTimeConfirm,
            onDismiss = onTimeDismiss
        )
    } else if(isShowingDatePicker) {
        DatePickerModal(
            onDismiss = onDateDismiss,
            onDateSelected = onDateConfirm
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePicker(
    currentTime: Calendar,
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    CustomTimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState) },
    ) {
        TimePicker(state = timePickerState)
    }
}

@Composable
fun CustomTimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = dimensionResource(R.dimen.medium_elevation),
            modifier =
            Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.large_padding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(R.dimen.large_padding)),
                    text = stringResource(R.string.select_time),
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.time_picker_buttons_height))
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) }
                    TextButton(onClick = onConfirm) { Text(stringResource(R.string.ok)) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DateTimePickerInfinityPreview() {
    DealDoneTheme {
        DateTimePicker(
            isInfinityTime = true,
            currentCalendar = null,
            onTimeDismiss = {},
            onTimeConfirm = {},
            isShowingTimePicker = false,
            onTimePickerClick = {},
            onDatePickerClick = {},
            onDateConfirm = {},
            onDateDismiss = {},
            isShowingDatePicker = false,
            onInfinityTimeChanged = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DateTimePickerPreview() {
    DealDoneTheme {
        DateTimePicker(
            isInfinityTime = false,
            currentCalendar = null,
            onTimeConfirm = {},
            onTimeDismiss = {},
            isShowingTimePicker = false,
            onTimePickerClick = {},
            onDatePickerClick = {},
            isShowingDatePicker = false,
            onDateConfirm = {},
            onDateDismiss = {},
            onInfinityTimeChanged = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AdvancedTimePickerPreview() {
    DealDoneTheme {
        CustomTimePickerDialog(
            onDismiss = { },
            onConfirm = {  },
        ) {
            TimePicker(state = TimePickerState(
                initialHour = Calendar.HOUR_OF_DAY,
                initialMinute = Calendar.MINUTE,
                is24Hour = true
            ))
        }
    }
}