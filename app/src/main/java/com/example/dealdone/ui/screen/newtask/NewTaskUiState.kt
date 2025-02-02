package com.example.dealdone.ui.screen.newtask

import com.example.dealdone.data.model.TaskInfo
import com.example.dealdone.data.model.TaskPriority
import com.example.dealdone.data.model.TaskStatus
import java.util.Calendar

data class NewTaskUiState(
    val fastCreationTaskText: String = "",
    val isFastModeOfCreationTask: Boolean = false,
)

data class NewDefaultTaskUiState(
    val creationTask: TaskInfo = TaskInfo(
        id = 0,
        name = "",
        description = "",
        targetDate = null,
        creationDate = Calendar.getInstance(),
        taskPriority = TaskPriority.LOW,
        taskStatus = TaskStatus.IN_PROGRESS,
        parentTaskID = null
    ),
    val isInfinityTime: Boolean = false,
    val isTimePickerShowing: Boolean = false,
    val isDatePickerShowing: Boolean = false,
)
