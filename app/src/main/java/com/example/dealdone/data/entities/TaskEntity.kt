package com.example.dealdone.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dealdone.data.converter.TaskPriorityConverter
import com.example.dealdone.data.model.TaskInfo
import com.example.dealdone.data.model.TaskPriority
import com.example.dealdone.data.model.TaskStatus
import java.util.Calendar

@Entity(tableName = "tasks")
@TypeConverters(TaskPriorityConverter::class)
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val targetDate: Long?,
    val creationDate: Long,
    val isCompleted: Boolean,
    val taskPriority: TaskPriority,
    val parentTaskID: Int?
)

fun TaskEntity.toTaskInfo() : TaskInfo = TaskInfo(
    id = id,
    name = name,
    description = description,
    targetDate = longToCalendar(targetDate),
    creationDate = longToCalendar(creationDate) ?: Calendar.getInstance(),
    taskPriority = taskPriority,
    taskStatus = getTaskStatus(this),
    parentTaskID = parentTaskID
)

fun getTaskStatus(taskEntity: TaskEntity) : TaskStatus {
    return if(taskEntity.isCompleted) TaskStatus.COMPLETED
    else if(taskEntity.targetDate == null) return TaskStatus.IN_PROGRESS
    else if(taskEntity.targetDate < Calendar.getInstance().timeInMillis) TaskStatus.IN_PROGRESS
    else TaskStatus.EXPIRED
}

fun longToCalendar(timeInMillis: Long?): Calendar? {
    if(timeInMillis == null) return null
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis
    return calendar
}