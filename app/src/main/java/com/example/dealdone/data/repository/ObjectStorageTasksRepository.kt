package com.example.dealdone.data.repository

import com.example.dealdone.data.dao.TaskDao
import com.example.dealdone.data.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

class ObjectStorageTasksRepository(
    private val taskDao: TaskDao
) : TasksRepository {
    override suspend fun insertTask(task: TaskEntity) = taskDao.insert(task)

    override suspend fun updateTask(task: TaskEntity) = taskDao.update(task)

    override suspend fun deleteTask(task: TaskEntity) = taskDao.delete(task)

    override fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    override fun getTaskById(taskId: Int): Flow<TaskEntity?> = taskDao.getTaskById(taskId)

    override fun getSubTasksByParentId(parentId: Int): Flow<List<TaskEntity>> = taskDao.getSubTasksByParentId(parentId)
}