package com.example.dealdone.ui.screen.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.dealdone.R
import com.example.dealdone.data.extensions.formatToDateTimeString
import com.example.dealdone.data.model.TaskInfo
import com.example.dealdone.data.model.TasksMock
import com.example.dealdone.ui.component.TaskCard
import com.example.dealdone.ui.theme.DealDoneTheme
import java.util.UUID

@Composable
fun TaskScreen(
    taskUiState: TaskUiState,
    subtasks: List<TaskInfo>,
    onTaskClick: (UUID) -> Unit,
    expandTask: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(dimensionResource(R.dimen.small_padding))
            .fillMaxHeight()
    ) {
        if(taskUiState.selectedTask != null){
            item {
                CurrentTaskInformation(
                    currentTask = taskUiState.selectedTask,
                    isExpanded = taskUiState.isExpanded,
                    expandClick = expandTask
                )
            }
        }

        items(subtasks) { task ->
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_padding)))
            TaskCard(
                task = task,
                onClick = { onTaskClick(task.id) }
            )
        }
    }
}

@Composable
fun CurrentTaskInformation(
    currentTask: TaskInfo,
    isExpanded: Boolean,
    expandClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expandClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.medium_elevation)
        ),
        shape = RoundedCornerShape(
            topStart = dimensionResource(R.dimen.zero_cornerRadius),
            topEnd = dimensionResource(R.dimen.zero_cornerRadius),
            bottomStart = dimensionResource(R.dimen.large_cornerRadius),
            bottomEnd = dimensionResource(R.dimen.large_cornerRadius)
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.large_padding))
                .fillMaxWidth()
        ) {
            Text(
                text = currentTask.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = currentTask.description,
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentTask.targetDate?.formatToDateTimeString() ?: stringResource(R.string.no_target_date),
                    style = MaterialTheme.typography.bodySmall
                )
                Column {
                    Text(
                        text = currentTask.taskPriority.name,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = currentTask.taskStatus.name,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Text(
                text = if (isExpanded) stringResource(R.string.task_collapse)
                else
                    stringResource(R.string.task_expand),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
fun CurrentTaskPreview() {
    DealDoneTheme {
        CurrentTaskInformation(
            currentTask = TasksMock.getRandomTask()[0],
            expandClick = {},
            isExpanded = false
        )
    }
}

@Preview
@Composable
fun TaskScreenPreview() {
    DealDoneTheme {
        TaskScreen(
            taskUiState = TaskUiState(),
            expandTask = {},
            onTaskClick = {},
            subtasks = TasksMock.getRandomTask(number = 7)
        )
    }
}
