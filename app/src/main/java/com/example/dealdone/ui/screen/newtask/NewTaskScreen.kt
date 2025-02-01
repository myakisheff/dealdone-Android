package com.example.dealdone.ui.screen.newtask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.dealdone.R
import com.example.dealdone.data.model.TaskPriority
import com.example.dealdone.ui.component.CustomDropdownMenu
import com.example.dealdone.ui.component.DateTimePicker
import com.example.dealdone.ui.theme.DealDoneTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreen(
    onCreatePressed: () -> Unit,
    onChangeModePressed: (Boolean) -> Unit,
    isFastCreation: Boolean,
    fastCreationText: String,
    onFastCreationTextChanged: (String) -> Unit,
    defaultTaskUiState: NewDefaultTaskUiState,
    onDefaultTaskTitleChanged: (String) -> Unit,
    onDefaultTaskDescriptionChanged: (String) -> Unit,
    onChangeSelectedPriority: (TaskPriority) -> Unit,
    onChangeInfinityTime: (Boolean) -> Unit,
    onTimeConfirm: (TimePickerState) -> Unit,
    onTimeDismiss: () -> Unit,
    onDateConfirm: (Long?) -> Unit,
    onDateDismiss: () -> Unit,
    onTimePickerClick: () -> Unit,
    onDatePickerClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        ModeSwitcher(
            isFastCreation = isFastCreation,
            onChangeModePressed = onChangeModePressed
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_padding)))

        if(isFastCreation) {
            FastCreationTask(
                onCreatePressed = onCreatePressed,
                onTextChanged = onFastCreationTextChanged,
                text = fastCreationText
            )
        } else {
            DefaultCreationTask(
                onCreatePressed = onCreatePressed,
                taskUiState = defaultTaskUiState,
                onTitleChanged = onDefaultTaskTitleChanged,
                onDescriptionChanged = onDefaultTaskDescriptionChanged,
                onChangeSelectedPriority = onChangeSelectedPriority,
                onTimeConfirm = onTimeConfirm,
                onTimeDismiss = onTimeDismiss,
                onDatePickerClick = onDatePickerClick,
                onTimePickerClick = onTimePickerClick,
                onDateConfirm = onDateConfirm,
                onDateDismiss = onDateDismiss,
                onChangeInfinityTime = onChangeInfinityTime
            )
        }
    }
}

@Composable
fun ModeSwitcher(
    onChangeModePressed: (Boolean) -> Unit,
    isFastCreation: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        ModeItem(
            onClick = { onChangeModePressed(false) },
            isSelected = !isFastCreation,
            modeName = stringResource(R.string.default_mode),
            modifier = Modifier.weight(1f)
        )
        ModeItem(
            onClick = { onChangeModePressed(true) },
            isSelected = isFastCreation,
            modeName = stringResource(R.string.fast_mode),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ModeItem(
    modeName: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = modeName,
            style = MaterialTheme.typography.labelLarge
        )
        HorizontalDivider(
            thickness = dimensionResource(R.dimen.large_thickness),
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        )
    }
}

@Composable
fun FastCreationTask(
    onCreatePressed: () -> Unit,
    onTextChanged: (String) -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        TextField(
            value = text,
            onValueChange = onTextChanged,
            label = { Text(text = stringResource(R.string.fast_creation_task_label) ) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = onCreatePressed) {
            Text(text = stringResource(R.string.create_task))
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.extra_large_padding)))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultCreationTask(
    onCreatePressed: () -> Unit,
    taskUiState: NewDefaultTaskUiState,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onChangeSelectedPriority: (TaskPriority) -> Unit,
    onChangeInfinityTime: (Boolean) -> Unit,
    onTimeConfirm: (TimePickerState) -> Unit,
    onTimeDismiss: () -> Unit,
    onDateConfirm: (Long?) -> Unit,
    onDateDismiss: () -> Unit,
    onTimePickerClick: () -> Unit,
    onDatePickerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        TextField(
            value = taskUiState.creationTask.name,
            onValueChange = onTitleChanged,
            label = { Text(text = stringResource(R.string.enter_task_title) ) },
            modifier = Modifier.fillMaxWidth()
        )

        CustomDropdownMenu(
            selectedItem = stringResource(taskUiState.creationTask.taskPriority.title),
            listItems = TaskPriority.entries.map { item -> Pair(item, stringResource(item.title)) },
            onChangeSelectedItem = onChangeSelectedPriority,
        )

        DateTimePicker(
            isInfinityTime = taskUiState.isInfinityTime,
            onInfinityTimeChanged = onChangeInfinityTime,
            currentCalendar = taskUiState.creationTask.targetDate,
            onTimeConfirm = onTimeConfirm,
            onTimeDismiss = onTimeDismiss,
            onDatePickerClick = onDatePickerClick,
            onTimePickerClick = onTimePickerClick,
            isShowingDatePicker = taskUiState.isDatePickerShowing,
            isShowingTimePicker = taskUiState.isTimePickerShowing,
            onDateConfirm = onDateConfirm,
            onDateDismiss = onDateDismiss,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = taskUiState.creationTask.description,
            onValueChange = onDescriptionChanged,
            label = { Text(text = stringResource(R.string.enter_task_description) ) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = onCreatePressed) {
            Text(text = stringResource(R.string.create_task))
        }
    }
}

@Preview
@Composable
fun ChangeModeButtonsPreview() {
    DealDoneTheme {
        ModeSwitcher(
            isFastCreation = false,
            onChangeModePressed = {}
        )
    }
}

@Preview
@Composable
fun FastCreationTaskPreview() {
    DealDoneTheme {
        FastCreationTask(
            onCreatePressed = {},
            onTextChanged = {},
            text = "Sample of text",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DefaultCreationTaskPreview() {
    DealDoneTheme {
        DefaultCreationTask(
            onCreatePressed = {},
            taskUiState = NewDefaultTaskUiState(),
            onTitleChanged = {},
            onDescriptionChanged = {},
            onChangeSelectedPriority = {},
            onTimeConfirm = {},
            onTimeDismiss = {},
            onDatePickerClick = {},
            onTimePickerClick = {},
            onDateDismiss = {},
            onDateConfirm = {},
            onChangeInfinityTime = {}
        )
    }
}

@Preview
@Composable
fun DatePickerPreview() {
    DealDoneTheme {

    }
}