package com.example.dealdone.data.repository

import com.example.dealdone.data.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [TaskEntity] from a given data source.
 */
interface TasksRepository {

    /**
     * Insert task in the data source
     */
    suspend fun insertTask(task: TaskEntity)

    /**
     * Update task in the data source
     */
    suspend fun updateTask(task: TaskEntity)

    /**
     * Delete task from the data source
     */
    suspend fun deleteTask(task: TaskEntity)

    /**
     * Retrieve all the tasks from the the given data source.
     */
    fun getAllTasks(): Flow<List<TaskEntity>>

    /**
     * Retrieve an tasks from the given data source that matches with the [taskId].
     */
    fun getTaskById(taskId: Int): Flow<TaskEntity?>

    /**
     * Retrieve all the tasks with given [parentId] from the the given data source.
     */
    fun getSubTasksByParentId(parentId: Int): Flow<List<TaskEntity>>
}

/**
 * Repository that provides get and send of [TaskEntity] from a given Object Storage.
 */
interface NetworkTasksRepository : TasksRepository {
    /**
     * Retrieve all the tasks from the the given data source.
     */
    suspend fun getTasksFromObjectStorage(): Flow<List<TaskEntity>>

    /**
     * Sends all the tasks to the the given data source.
     */
    suspend fun sendTasksToObjectStorage()
}