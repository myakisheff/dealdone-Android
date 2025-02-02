package com.example.dealdone.ui.screen.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dealdone.DealDoneApplication
import com.example.dealdone.data.entities.toTaskInfo
import com.example.dealdone.data.model.TaskInfo
import com.example.dealdone.data.model.TasksMock
import com.example.dealdone.data.model.toTaskEntity
import com.example.dealdone.data.repository.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Stack

class TaskViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {
    var tasksUiState: TasksUiState by mutableStateOf(TasksUiState.Waiting)
        private set

    private val _currentTaskUiState = MutableStateFlow(TaskUiState())
    val currentTaskUiState: StateFlow<TaskUiState> = _currentTaskUiState

    private var tasks by mutableStateOf(emptyList<TaskInfo>())
    private val stackOfPreviousTasks: Stack<Int> = Stack()

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

    fun selectTask(taskId: Int) {
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

    fun addNewTasks(newTasks: List<TaskInfo>) {
        viewModelScope.launch {
            newTasks.forEach { task ->
                tasksRepository.insertTask(task.toTaskEntity())
            }
        }
    }

    private fun getTasks() {
        viewModelScope.launch {
           tasksRepository.getAllTasks().collect { value ->
                tasks = value.map { it.toTaskInfo() }
            }
        }
        tasks = TasksMock.getRandomTask(15)
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DealDoneApplication)
                val repository = application.container.tasksRepository
                TaskViewModel(tasksRepository = repository)
            }
        }
    }
}