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
import com.example.dealdone.data.model.TaskPriority
import com.example.dealdone.data.model.TaskStatus
import com.example.dealdone.data.model.TasksMock
import com.example.dealdone.ui.screen.newtask.NewTaskUiState
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
    private val stackOfPreviousTasks: Stack<UUID> = Stack()

    private val _newTaskUiState = MutableStateFlow(NewTaskUiState())
    val newTaskUiState: StateFlow<NewTaskUiState> = _newTaskUiState

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
        stackOfPreviousTasks.push(_currentTaskUiState.value.selectedTask?.id)

        _currentTaskUiState.update {
            it.copy(
                isExpanded = false,
                selectedTask = tasks.find { task -> task.id == taskId }
            )
        }
    }

    fun selectPreviousTask() {
        val prevTaskId = stackOfPreviousTasks.pop()

        _currentTaskUiState.update {
            it.copy(
                isExpanded = false,
                selectedTask = tasks.find { task -> task.id == prevTaskId }
            )
        }
    }

    fun deselectTask() {
        stackOfPreviousTasks.clear()

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

    fun addNewTask() {
        if(newTaskUiState.value.isFastModeOfCreationTask) {
            newTaskUiState.value.fastCreationTaskText.split("\n").forEach { taskText ->
                tasks = tasks + generateNewFastTask(taskText)
            }

            _newTaskUiState.update {
                it.copy(
                    fastCreationTaskText = ""
                )
            }
        }
        else {

        }
    }

    private fun getTasks() {
        tasks = TasksMock.getRandomTask(15)

        // TODO: get tasks from repository
    }

    private fun generateNewFastTask(taskText: String) : TaskInfo {
        return TaskInfo(
            id = UUID.randomUUID(),
            parentTaskID = currentTaskUiState.value.selectedTask?.id,
            name = taskText.substringBefore(" "),
            description = taskText,
            targetDate = currentTaskUiState.value.selectedTask?.targetDate,
            taskPriority = currentTaskUiState.value.selectedTask?.taskPriority ?: TaskPriority.LOW,
            taskStatus = currentTaskUiState.value.selectedTask?.taskStatus ?: TaskStatus.IN_PROGRESS,
        )
    }

    fun updateNewTaskMode(isFast: Boolean) {
        _newTaskUiState.update {
            it.copy(
                isFastModeOfCreationTask = isFast
            )
        }
    }

    fun updateNewTaskText(newText: String) {
        _newTaskUiState.update {
            it.copy(
                fastCreationTaskText = newText
            )
        }
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