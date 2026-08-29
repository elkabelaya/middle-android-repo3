package ru.yandex.architectureproject.domain

import ru.yandex.architectureproject.data.repository.TaskRepository

class DeleteTaskUseCase(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(taskId: Int) {
        repository.deleteTask(taskId)
    }
}
