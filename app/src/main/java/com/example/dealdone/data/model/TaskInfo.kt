package com.example.dealdone.data.model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import java.util.Calendar
import java.util.UUID

data class TaskInfo(
    val id: UUID,
    val name: String,
    val description: String,
    val targetDate: Calendar?,
    val creationDate: Calendar = Calendar.getInstance(),
    val taskPriority: TaskPriority,
    val taskStatus: TaskStatus,
    val parentTaskID: UUID?,
)

object TasksMock {
    fun getRandomTask(number: Int = 1): List<TaskInfo> {
        val generatedIds = buildList<UUID> {
            repeat(number) {
                add(UUID.randomUUID())
            }
        }

        return buildList {
            repeat(number) { index ->
                val randomStatus = TaskStatus.entries.shuffled().first()
                val randomPriority = TaskPriority.entries.shuffled().first()

                add(
                    TaskInfo(
                        id = generatedIds[index],
                        name = "${randomStatus.name} Task - ${randomPriority.name}",
                        description = LoremIpsum(words = 150).values.shuffled().joinToString(separator = " "),
                        targetDate = Calendar.getInstance().apply { this.set(Calendar.DAY_OF_MONTH, this.get(Calendar.DAY_OF_MONTH) + (1..4).random()) },
                        parentTaskID = if((1..4).random() == 1) null else generatedIds.random(),
                        taskPriority = randomPriority,
                        creationDate = Calendar.getInstance(),
                        taskStatus = randomStatus
                    )
                )
            }
        }
    }
}
