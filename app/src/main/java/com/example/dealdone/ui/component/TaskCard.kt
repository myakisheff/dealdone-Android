package com.example.dealdone.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dealdone.R
import com.example.dealdone.data.extensions.formatToDateString
import com.example.dealdone.data.model.TaskInfo
import com.example.dealdone.data.model.TaskPriority
import com.example.dealdone.data.model.TaskStatus
import com.example.dealdone.ui.theme.DealDoneTheme
import java.util.Calendar
import java.util.UUID

/**
 *  A card containing information about the task
 *
 *  @param task [TaskInfo] present an information of the task
 */
@Composable
fun TaskCard(
    task: TaskInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var color = when(task.taskPriority) {
        TaskPriority.LOW -> colorResource(R.color.Low_priority)
        TaskPriority.MEDIUM -> colorResource(R.color.Medium_priority)
        TaskPriority.HIGH -> colorResource(R.color.High_priority)
    }

    color = when(task.taskStatus) {
        TaskStatus.IN_PROGRESS -> color
        TaskStatus.COMPLETED -> colorResource(R.color.Completed)
        TaskStatus.EXPIRED -> colorResource(R.color.Expired)
    }

    Card(
        border = BorderStroke(1.dp, color),
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
        ) {
            Text(
                text = task.name,
                style = typography.bodyLarge
            )
            Text(
                text = task.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodySmall
            )
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = task.targetDate?.formatToDateString() ?: stringResource(R.string.no_target_date),
                    style = typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.small_padding)))

                Image(
                    painter = painterResource(id = R.drawable.target_icon),
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                )
            }
        }
    }
}

@Preview
@Composable
fun TaskCardPreview() {
    DealDoneTheme(darkTheme = false){
        Column {
            TaskCard(
                task = TaskInfo(
                    id = UUID.randomUUID(),
                    name = "Low Task In_Progress",
                    description = "Task for a testing a preview of this task with long text example lorem",
                    targetDate = Calendar.getInstance(),
                    parentTaskID = UUID.randomUUID(),
                    taskPriority = TaskPriority.LOW,
                    taskStatus = TaskStatus.IN_PROGRESS
                ),
                onClick = {},
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.medium_padding))
            )

            TaskCard(
                task = TaskInfo(
                    id = UUID.randomUUID(),
                    name = "Medium Task In_Progress",
                    description = "Task for a testing a preview of this task with long text example lorem",
                    targetDate = Calendar.getInstance(),
                    parentTaskID = UUID.randomUUID(),
                    taskPriority = TaskPriority.MEDIUM,
                    taskStatus = TaskStatus.IN_PROGRESS
                ),
                onClick = {},
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.medium_padding))
            )

            TaskCard(
                task = TaskInfo(
                    id = UUID.randomUUID(),
                    name = "High Task In_Progress",
                    description = "Task for a testing a preview of this task with long text example lorem",
                    targetDate = Calendar.getInstance(),
                    parentTaskID = UUID.randomUUID(),
                    taskPriority = TaskPriority.HIGH,
                    taskStatus = TaskStatus.IN_PROGRESS
                ),
                onClick = {},
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.medium_padding))
            )

            TaskCard(
                task = TaskInfo(
                    id = UUID.randomUUID(),
                    name = "Expired Task",
                    description = "Task for a testing a preview of this task with long text example lorem",
                    targetDate = Calendar.getInstance(),
                    parentTaskID = UUID.randomUUID(),
                    taskPriority = TaskPriority.MEDIUM,
                    taskStatus = TaskStatus.EXPIRED
                ),
                onClick = {},
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.medium_padding))
            )

            TaskCard(
                task = TaskInfo(
                    id = UUID.randomUUID(),
                    name = "Completed Task",
                    description = "Task for a testing a preview of this task with long text example lorem",
                    targetDate = Calendar.getInstance(),
                    parentTaskID = UUID.randomUUID(),
                    taskPriority = TaskPriority.HIGH,
                    taskStatus = TaskStatus.COMPLETED
                ),
                onClick = {},
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.medium_padding))
            )
        }
    }
}