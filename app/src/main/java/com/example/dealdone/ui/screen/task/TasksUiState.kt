package com.example.dealdone.ui.screen.task

import com.example.dealdone.data.model.TaskInfo

sealed interface TasksUiState {
    data class Success(val tasks: List<TaskInfo>) : TasksUiState
    data class Error(val errorMessage: String?) : TasksUiState
    data object Loading : TasksUiState
    data object Waiting : TasksUiState
}

data class TaskUiState (
    val selectedTask: TaskInfo? = null,
    val isExpanded: Boolean = false,
)