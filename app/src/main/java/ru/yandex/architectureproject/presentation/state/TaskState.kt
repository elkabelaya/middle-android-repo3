package ru.yandex.architectureproject.presentation.state

import ru.yandex.architectureproject.data.model.Task

sealed class TaskState {
    data object Loading : TaskState()
    data class Loaded(val tasks: List<Task>) : TaskState()
    data class Error(val message: String) : TaskState()
}
