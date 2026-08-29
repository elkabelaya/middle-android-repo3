package ru.yandex.architectureproject.domain

import ru.yandex.architectureproject.data.repository.TaskRepository

class CompleteTaskUseCase(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(taskId: Int) {
        repository.completeTask(taskId)
        // реализовано в строках 54-55 TaskViewModel,
        // показалось более подходящим местом
    }
}
