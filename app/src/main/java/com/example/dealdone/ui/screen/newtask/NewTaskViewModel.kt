package com.example.dealdone.ui.screen.newtask

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import com.example.dealdone.data.model.TaskInfo
import com.example.dealdone.data.model.TaskPriority
import com.example.dealdone.data.model.TaskStatus
import com.example.dealdone.ui.screen.task.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
import java.util.UUID

class NewTaskViewModel(

) : ViewModel() {
    private val _newTaskUiState = MutableStateFlow(NewTaskUiState())
    val newTaskUiState: StateFlow<NewTaskUiState> = _newTaskUiState

    private val _newDefaultTaskUiState = MutableStateFlow(NewDefaultTaskUiState())
    val newDefaultTaskUiState: StateFlow<NewDefaultTaskUiState> = _newDefaultTaskUiState

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

    /**
     * Returns list of new tasks
     */
    fun addNewTask(currentTaskUiState: TaskUiState): List<TaskInfo> {
        val newTasks = mutableListOf<TaskInfo>()

        if(newTaskUiState.value.isFastModeOfCreationTask && newTaskUiState.value.fastCreationTaskText.isNotBlank()) {
            newTaskUiState.value.fastCreationTaskText.split("\n").forEach { taskText ->
                newTasks.add(generateNewFastTask(taskText, currentTaskUiState))
            }

            _newTaskUiState.update {
                it.copy(
                    fastCreationTaskText = ""
                )
            }
        }
        else {
            newTasks.add(
                TaskInfo(
                    id = UUID.randomUUID(),
                    parentTaskID = currentTaskUiState.selectedTask?.id,
                    name = newDefaultTaskUiState.value.creationTask.name,
                    description = newDefaultTaskUiState.value.creationTask.description,
                    targetDate = if(newDefaultTaskUiState.value.isInfinityTime) null
                                else newDefaultTaskUiState.value.creationTask.targetDate,
                    creationDate = Calendar.getInstance(),
                    taskPriority = newDefaultTaskUiState.value.creationTask.taskPriority,
                    taskStatus = getTaskStatus(),
                )
            )

            _newDefaultTaskUiState.update {
                it.copy(
                    creationTask = TaskInfo(
                        id = UUID.randomUUID(),
                        parentTaskID = null,
                        name = "",
                        description = "",
                        targetDate = null,
                        taskPriority = newDefaultTaskUiState.value.creationTask.taskPriority,
                        taskStatus = TaskStatus.IN_PROGRESS,
                    ),
                    isInfinityTime = false,
                    isDatePickerShowing = false,
                    isTimePickerShowing = false,
                )
            }
        }

        return newTasks
    }

    private fun getTaskStatus(): TaskStatus {
        return if(newDefaultTaskUiState.value.creationTask.targetDate == null ||
            newDefaultTaskUiState.value.isInfinityTime) TaskStatus.IN_PROGRESS
        else if(newDefaultTaskUiState.value.creationTask.targetDate!! > Calendar.getInstance()) {
            TaskStatus.IN_PROGRESS
        } else TaskStatus.EXPIRED
    }

    private fun generateNewFastTask(taskText: String, currentTaskUiState: TaskUiState) : TaskInfo {
        return TaskInfo(
            id = UUID.randomUUID(),
            parentTaskID = currentTaskUiState.selectedTask?.id,
            name = taskText.substringBefore(" "),
            description = taskText,
            targetDate = currentTaskUiState.selectedTask?.targetDate,
            taskPriority = currentTaskUiState.selectedTask?.taskPriority ?: TaskPriority.LOW,
            creationDate = Calendar.getInstance(),
            taskStatus = currentTaskUiState.selectedTask?.taskStatus ?: TaskStatus.IN_PROGRESS,
        )
    }

    fun changeDefaultTaskTitle(defaultTitle: String) {
        _newDefaultTaskUiState.update {
            it.copy(
                creationTask = _newDefaultTaskUiState.value.creationTask.copy(
                    name = defaultTitle
                )
            )
        }
    }

    fun changeDefaultDescription(defaultDescription: String) {
        _newDefaultTaskUiState.update {
            it.copy(
                creationTask = _newDefaultTaskUiState.value.creationTask.copy(
                    description = defaultDescription
                )
            )
        }
    }

    fun changeDefaultPriority(defaultTaskPriority: TaskPriority) {
        _newDefaultTaskUiState.update {
            it.copy(
                creationTask = _newDefaultTaskUiState.value.creationTask.copy(
                    taskPriority = defaultTaskPriority
                )
            )
        }
    }

    fun changeInfinityTime(infinity: Boolean) {
        _newDefaultTaskUiState.update {
            it.copy(
                isInfinityTime = infinity
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun changeTime(timePickerState: TimePickerState) {
        val newTime = _newDefaultTaskUiState.value.creationTask.targetDate ?: Calendar.getInstance()
        newTime.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
        newTime.set(Calendar.MINUTE, timePickerState.minute)

        _newDefaultTaskUiState.update {
            it.copy(
                creationTask = _newDefaultTaskUiState.value.creationTask.copy(
                    targetDate = newTime
                ),
                isTimePickerShowing = false
            )
        }
    }

    fun hideTimePicker() {
        _newDefaultTaskUiState.update {
            it.copy(
                isTimePickerShowing = false
            )
        }
    }

    fun showDatePicker() {
        _newDefaultTaskUiState.update {
            it.copy(
                isDatePickerShowing = true
            )
        }
    }

    fun showTimePicker() {
        _newDefaultTaskUiState.update {
            it.copy(
                isTimePickerShowing = true
            )
        }
    }

    fun changeDate(date: Long?) {
        if(date == null) {
            return
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        val newDate = _newDefaultTaskUiState.value.creationTask.targetDate ?: Calendar.getInstance()
        newDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        newDate.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        newDate.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))

        _newDefaultTaskUiState.update {
            it.copy(
                creationTask = _newDefaultTaskUiState.value.creationTask.copy(
                    targetDate = newDate
                ),
                isDatePickerShowing = false
            )
        }
    }

    fun hideDatePicker() {
        _newDefaultTaskUiState.update {
            it.copy(
                isDatePickerShowing = false
            )
        }
    }
}