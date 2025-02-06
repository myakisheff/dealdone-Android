package com.example.dealdone.ui.screen.task

import com.example.dealdone.data.model.TaskInfo
import com.example.dealdone.data.model.TaskPriority
import com.example.dealdone.data.model.TaskStatus
import com.example.dealdone.ui.screen.newtask.NewDefaultTaskUiState
import java.util.Calendar

sealed interface TasksUiState {
    data class Success(val tasks: List<TaskInfo>) : TasksUiState
    data class Error(val errorMessage: String?) : TasksUiState
    data object Loading : TasksUiState
    data object Waiting : TasksUiState
}

data class TaskUiState (
    val selectedTask: TaskInfo? = null,
    val isExpanded: Boolean = false,
    val isEditMode: Boolean = false,
)

fun TaskUiState.toNewDefaultTaskUiState() : NewDefaultTaskUiState = NewDefaultTaskUiState(
    creationTask = selectedTask ?: TaskInfo(
        id = 0,
        name = "",
        description = "",
        targetDate = null,
        creationDate = Calendar.getInstance(),
        taskPriority = TaskPriority.LOW,
        taskStatus = TaskStatus.IN_PROGRESS,
        parentTaskID = null
    ),
    isInfinityTime = selectedTask?.targetDate == null,
    isTimePickerShowing = false,
    isDatePickerShowing = false,
)
