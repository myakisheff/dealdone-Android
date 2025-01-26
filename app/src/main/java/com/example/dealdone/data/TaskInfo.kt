package com.example.dealdone.data

import java.util.Calendar
import java.util.UUID

data class TaskInfo(
    val ID: UUID,
    val name: String,
    val description: String,
    val targetDate: Calendar,
    val taskPriority: TaskPriority,
    val taskStatus: TaskStatus,
    val parentTaskID: UUID,
)
