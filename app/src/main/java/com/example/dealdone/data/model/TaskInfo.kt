package com.example.dealdone.data.model

import java.util.Calendar
import java.util.UUID

data class TaskInfo(
    val id: UUID,
    val name: String,
    val description: String,
    val targetDate: Calendar,
    val taskPriority: TaskPriority,
    val taskStatus: TaskStatus,
    val parentTaskID: UUID,
)

object ExampleTasks {
    fun getRandomTask(): TaskInfo {
        val randomStatus = TaskStatus.entries.shuffled().first()
        val randomPriority = TaskPriority.entries.shuffled().first()

        return TaskInfo(
            id = UUID.randomUUID(),
            name = "${randomStatus.name} Task - ${randomPriority.name}",
            description = "Task for a testing a preview of this task with long text example lorem ipsum dolor sit amet",
            targetDate = Calendar.getInstance(),
            parentTaskID = UUID.randomUUID(),
            taskPriority = randomPriority,
            taskStatus = randomStatus
        )
    }
}
