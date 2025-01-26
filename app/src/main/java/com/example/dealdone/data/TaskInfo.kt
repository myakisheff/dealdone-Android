package com.example.dealdone.data

import java.util.Date
import java.util.UUID

data class TaskInfo(
    val ID: UUID,
    val name: String,
    val description: String,
    val targetDate: Date,
    val taskPriority: TaskPriority,
    val taskStatus: TaskStatus,
    val parentTaskID: UUID,
)
