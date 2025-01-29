package com.example.dealdone.ui.screen.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dealdone.DealDoneApplication
import com.example.dealdone.data.model.TaskInfo
import com.example.dealdone.data.model.TasksMock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Stack
import java.util.UUID

class TaskViewModel(

) : ViewModel() {
    var tasksUiState: TasksUiState by mutableStateOf(TasksUiState.Waiting)
        private set

    private val _currentTaskUiState = MutableStateFlow(TaskUiState())
    val currentTaskUiState: StateFlow<TaskUiState> = _currentTaskUiState

    private var tasks by mutableStateOf(emptyList<TaskInfo>())

    private val stackOfTasks: Stack<UUID> = Stack()

    init {
        getTasks()
    }

    fun expandCurrentTask() {
        _currentTaskUiState.update {
            it.copy(
                isExpanded = !it.isExpanded
            )
        }
    }

    fun selectTask(taskId: UUID) {
        stackOfTasks.push(_currentTaskUiState.value.selectedTask?.id)

        _currentTaskUiState.update {
            it.copy(
                isExpanded = false,
                selectedTask = tasks.find { task -> task.id == taskId }
            )
        }
    }

    fun selectPreviousTask() {
        val prevTaskId = stackOfTasks.pop()

        _currentTaskUiState.update {
            it.copy(
                isExpanded = false,
                selectedTask = tasks.find { task -> task.id == prevTaskId }
            )
        }
    }

    fun deselectTask() {
        stackOfTasks.clear()

        _currentTaskUiState.update {
            it.copy(
                isExpanded = false,
                selectedTask = null
            )
        }
    }

    fun getSubtasks() : List<TaskInfo> {
        return buildList {
            tasks.forEach {
                if(_currentTaskUiState.value.selectedTask == null && it.parentTaskID == null) {
                    add(it)
                }
                else if(it.parentTaskID == _currentTaskUiState.value.selectedTask?.id) {
                    add(it)
                }
            }
        }
    }

    private fun getTasks() {
        tasks = TasksMock.getRandomTask(15)

        // TODO: get tasks from repository
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DealDoneApplication)

                TaskViewModel()
            }
        }
    }
}