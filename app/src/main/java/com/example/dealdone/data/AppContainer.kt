package com.example.dealdone.data

import android.content.Context
import com.example.dealdone.data.database.TaskDatabase
import com.example.dealdone.data.repository.ObjectStorageTasksRepository
import com.example.dealdone.data.repository.TasksRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val tasksRepository : TasksRepository
}

/**
 * [AppContainer] implementation that provides instance of [ObjectStorageTasksRepository]
 */
class DefaultAppContainer(private val context: Context) : AppContainer {
    override val tasksRepository: TasksRepository by lazy {
        ObjectStorageTasksRepository(TaskDatabase.getDatabase(context).taskDao())
    }
}