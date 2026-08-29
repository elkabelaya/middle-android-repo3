package ru.yandex.architectureproject.domain

import ru.yandex.architectureproject.data.repository.TaskRepository

class AddTaskUseCase(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(task: String) {
        repository.addTask(task)
    }
}
