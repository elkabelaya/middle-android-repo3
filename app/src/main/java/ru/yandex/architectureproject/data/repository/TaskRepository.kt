package ru.yandex.architectureproject.data.repository

import kotlinx.coroutines.flow.Flow
import ru.yandex.architectureproject.data.db.TaskDao
import ru.yandex.architectureproject.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun addTask(task: String) {
        taskDao.addTask(Task(text = task))
    }

    suspend fun completeTask(taskId: Int) {
        taskDao.updateTaskStatus(taskId, true)
    }

    suspend fun incompleteTask(taskId: Int) {
        taskDao.updateTaskStatus(taskId, false)
    }

    suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }
}
