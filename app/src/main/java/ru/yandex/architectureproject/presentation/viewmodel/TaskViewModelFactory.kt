package ru.yandex.architectureproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import ru.yandex.architectureproject.App
import ru.yandex.architectureproject.data.db.TaskDatabase
import ru.yandex.architectureproject.data.repository.TaskRepository
import ru.yandex.architectureproject.domain.AddTaskUseCase
import ru.yandex.architectureproject.domain.DeleteTaskUseCase
import ru.yandex.architectureproject.domain.GetAllTasksUseCase
import ru.yandex.architectureproject.domain.CompleteTaskUseCase
import ru.yandex.architectureproject.domain.IncompleteTaskUseCase

class TaskViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val taskDao = TaskDatabase.getInstance(App.context).taskDao()
        val repository = TaskRepository(taskDao)
        val addTaskUseCase = AddTaskUseCase(repository)
        val deleteTaskUseCase = DeleteTaskUseCase(repository)
        val completeTaskUseCase = CompleteTaskUseCase(repository)
        val incompleteTaskUseCase = IncompleteTaskUseCase(repository)
        val getAllTasksUseCase = GetAllTasksUseCase(repository)
        val ioDispatcher = Dispatchers.IO
        return TaskViewModel(
            addTaskUseCase,
            deleteTaskUseCase,
            getAllTasksUseCase,
            completeTaskUseCase,
            incompleteTaskUseCase,
            ioDispatcher,
        ) as T
    }
}
