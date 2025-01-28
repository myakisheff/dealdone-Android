package com.example.dealdone.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.dealdone.R
import com.example.dealdone.data.model.ExampleTasks
import com.example.dealdone.data.model.TaskInfo
import com.example.dealdone.ui.theme.DealDoneTheme

@Composable
fun TaskScreen(
    currentTask: TaskInfo,
    subtasks: List<TaskInfo>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        CurrentTaskInformation(currentTask = currentTask)

    }
}

@Composable
fun CurrentTaskInformation(currentTask: TaskInfo) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
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
                    text = currentTask.targetDate.time.toString(),
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
        CurrentTaskInformation(currentTask = ExampleTasks.getRandomTask())
    }
}

@Preview
@Composable
fun TaskScreenPreview() {
    DealDoneTheme {
        TaskScreen(
            currentTask = ExampleTasks.getRandomTask(),
            subtasks = listOf(
                ExampleTasks.getRandomTask(),
                ExampleTasks.getRandomTask(),
                ExampleTasks.getRandomTask(),
                ExampleTasks.getRandomTask(),
                ExampleTasks.getRandomTask(),
                ExampleTasks.getRandomTask()
            )
        )
    }
}
