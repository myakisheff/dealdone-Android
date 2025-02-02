package com.example.dealdone.data.model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.dealdone.data.entities.TaskEntity
import java.util.Calendar

data class TaskInfo(
    val id: Int = 0,
    val name: String,
    val description: String,
    val targetDate: Calendar?,
    val creationDate: Calendar = Calendar.getInstance(),
    val taskPriority: TaskPriority,
    val taskStatus: TaskStatus,
    val parentTaskID: Int?,
)

fun TaskInfo.toTaskEntity() : TaskEntity = TaskEntity(
    id = id,
    name = name,
    description = description,
    targetDate = targetDate?.timeInMillis,
    creationDate = creationDate.timeInMillis,
    taskPriority = taskPriority,
    isCompleted = taskStatus == TaskStatus.COMPLETED,
    parentTaskID = parentTaskID
)

object TasksMock {
    fun getRandomTask(number: Int = 1): List<TaskInfo> {
        val generatedIds = buildList {
            repeat(number) {
                add(it)
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
