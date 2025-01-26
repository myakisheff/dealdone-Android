package com.example.dealdone.ui.component

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dealdone.R
import com.example.dealdone.data.TaskInfo
import com.example.dealdone.data.TaskPriority
import com.example.dealdone.data.TaskStatus
import com.example.dealdone.ui.theme.DealDoneTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@Composable
fun TaskCard(
    task: TaskInfo,
    modifier: Modifier
) {
    val dateFormat = SimpleDateFormat(stringResource(R.string.date_pattern), Locale.getDefault())
    val date = dateFormat.format(task.targetDate.time).toString()

    Card(
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
                    text = date,
                    style = typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.small_padding)))

                Image(
                    painter = painterResource(id = R.drawable.target_icon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun TaskCardPreview() {
    DealDoneTheme{
        TaskCard(
            task = TaskInfo(
                ID = UUID.randomUUID(),
                name = "Test Task",
                description = "Task for a testing a preview of this task with long text example lorem",
                targetDate = Calendar.getInstance(),
                parentTaskID = UUID.randomUUID(),
                taskPriority = TaskPriority.LOW,
                taskStatus = TaskStatus.IN_PROGRESS
            ),
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.medium_padding))
        )
    }
}