package com.example.dealdone.ui.screen.task

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
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
import com.example.dealdone.data.model.TaskPriority
import com.example.dealdone.data.model.toTaskEntity
import com.example.dealdone.data.repository.TasksRepository
import com.example.dealdone.ui.screen.newtask.NewDefaultTaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
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

    private val _currentTaskEditUiState = MutableStateFlow(NewDefaultTaskUiState())
    val currentTaskEditUiState: StateFlow<NewDefaultTaskUiState> = _currentTaskEditUiState

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

    fun navigateBack() {
        if(_currentTaskUiState.value.isEditMode) {
            _currentTaskUiState.update {
                it.copy(
                    isEditMode = false
                )
            }
        } else {
            selectPreviousTask()
        }
    }

    private fun selectPreviousTask() {
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
    }

    fun enableEditMode() {
        _currentTaskUiState.update {
            it.copy(
                isEditMode = true
            )
        }

        _currentTaskEditUiState.value = _currentTaskUiState.value.toNewDefaultTaskUiState()
    }

    fun saveCurrentTask() {
        _currentTaskUiState.update {
            it.copy(
                selectedTask = _currentTaskEditUiState.value.creationTask,
                isEditMode = false
            )
        }
        viewModelScope.launch {
            _currentTaskUiState.value.selectedTask?.toTaskEntity()
                ?.let { tasksRepository.updateTask(it) }
        }
    }

    fun onTaskEditTitleChanged(title: String) {
        _currentTaskEditUiState.update {
            it.copy(
                creationTask = it.creationTask.copy(
                    name = title
                )
            )
        }
    }

    fun onTaskEditChangeDescription(description: String) {
        _currentTaskEditUiState.update {
            it.copy(
                creationTask = it.creationTask.copy(
                    description = description
                )
            )
        }
    }

    fun onTaskEditChangePriority(taskPriority: TaskPriority) {
        _currentTaskEditUiState.update {
            it.copy(
                creationTask = it.creationTask.copy(
                    taskPriority = taskPriority
                )
            )
        }
    }

    fun onTaskEditChangeInfinityTime(infinity: Boolean) {
        _currentTaskEditUiState.update {
            it.copy(
                isInfinityTime = infinity
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun onTaskEditChangeTime(timePickerState: TimePickerState) {
        val newTime = _currentTaskEditUiState.value.creationTask.targetDate ?: Calendar.getInstance()
        newTime.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
        newTime.set(Calendar.MINUTE, timePickerState.minute)

        _currentTaskEditUiState.update {
            it.copy(
                creationTask = it.creationTask.copy(
                    targetDate = newTime
                ),
                isTimePickerShowing = false
            )
        }
    }

    fun onTaskEditHideTimePicker() {
        _currentTaskEditUiState.update {
            it.copy(
                isTimePickerShowing = false
            )
        }
    }

    fun onTaskEditShowDatePicker() {
        _currentTaskEditUiState.update {
            it.copy(
                isDatePickerShowing = true
            )
        }
    }

    fun onTaskEditShowTimePicker() {
        _currentTaskEditUiState.update {
            it.copy(
                isTimePickerShowing = true
            )
        }
    }

    fun onTaskEditChangeDate(date: Long?) {
        if(date == null) {
            return
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        val newDate = _currentTaskEditUiState.value.creationTask.targetDate ?: Calendar.getInstance()
        newDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        newDate.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        newDate.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))

        _currentTaskEditUiState.update {
            it.copy(
                creationTask = it.creationTask.copy(
                    targetDate = newDate
                ),
                isDatePickerShowing = false
            )
        }
    }

    fun onTaskEditHideDatePicker() {
        _currentTaskEditUiState.update {
            it.copy(
                isDatePickerShowing = false
            )
        }
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