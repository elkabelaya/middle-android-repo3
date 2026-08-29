package ru.yandex.architectureproject.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import ru.yandex.architectureproject.data.model.Task
import ru.yandex.architectureproject.presentation.state.TaskAction
import ru.yandex.architectureproject.presentation.state.TaskState
import ru.yandex.architectureproject.presentation.ui.theme.ArchitectureProjectTheme
import ru.yandex.architectureproject.presentation.viewmodel.TaskViewModel
import ru.yandex.architectureproject.presentation.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArchitectureProjectTheme {
                TodoApp()
            }
        }
    }

    @Composable
    fun TodoApp() {
        val viewModel by viewModels<TaskViewModel> {
            TaskViewModelFactory()
        }

        val state by viewModel.state.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is TaskState.Loading -> CircularProgressIndicator()

                is TaskState.Loaded -> {
                    val tasks = (state as TaskState.Loaded).tasks
                    TodoList(tasks = tasks, onAction = { action -> viewModel.reduce(action) })
                }

                is TaskState.Error -> Text(text = (state as TaskState.Error).message)
            }
        }
    }

    @Composable
    fun TodoList(tasks: List<Task>, onAction: (TaskAction) -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val taskText = remember { mutableStateOf("") }

            Row {
                TextField(
                    value = taskText.value,
                    onValueChange = { taskText.value = it },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (taskText.value.isNotBlank()) {
                        onAction(TaskAction.AddTask(taskText.value))
                        taskText.value = ""
                    }
                }) {
                    Text("Добавить")
                }
            }

            LazyColumn {
                items(tasks) { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            task.text,
                            style = if (task.isDone) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
                        )
                        Row {
                            Checkbox(
                                checked = task.isDone,
                                onCheckedChange = {
                                    onAction(
                                        TaskAction.UpdateTaskStatus(
                                            task.id,
                                            it
                                        )
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { onAction(TaskAction.DeleteTask(task.id)) }) {
                                Text("Удалить")
                            }
                        }
                    }
                }
            }
        }
    }
}
