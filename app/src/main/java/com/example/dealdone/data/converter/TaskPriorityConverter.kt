package com.example.dealdone.data.converter

import androidx.room.TypeConverter
import com.example.dealdone.data.model.TaskPriority

class TaskPriorityConverter {
    @TypeConverter
    fun fromPriority(priority: TaskPriority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priorityString: String): TaskPriority {
        return TaskPriority.valueOf(priorityString)
    }
}