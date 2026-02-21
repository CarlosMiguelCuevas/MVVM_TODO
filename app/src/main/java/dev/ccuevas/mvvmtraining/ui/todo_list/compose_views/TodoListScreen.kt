package dev.ccuevas.mvvmtraining.ui.todo_list.compose_views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.ccuevas.mvvmtraining.domain.models.TodoDomain
import dev.ccuevas.mvvmtraining.ui.theme.MvvmTrainingTheme
import dev.ccuevas.mvvmtraining.ui.todo_list.TodoListUiEvent
import dev.ccuevas.mvvmtraining.ui.todo_list.TodoListViewModel
import dev.ccuevas.mvvmtraining.util.UiGenericEvent

/**
 * Stateful composable - handles ViewModel, state collection, and navigation events
 */
@Composable
fun TodoListScreen(
    onNavigate: (UiGenericEvent.Navigate) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val todoList = viewModel.todoListState.collectAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.uiGenericEvent.collect { event ->
            when (event) {
                is UiGenericEvent.Navigate -> {
                    onNavigate(event)
                }
                is UiGenericEvent.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onUiEvent(TodoListUiEvent.OnUndoDeleteClick)
                    }
                }
                else -> Unit
            }
        }
    }

    TodoListContent(
        todos = todoList.value,
        snackbarHostState = snackbarHostState,
        onUiEvent = viewModel::onUiEvent
    )
}

/**
 * Stateless composable - pure UI, receives state as parameters
 * Easy to preview and test
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListContent(
    todos: List<TodoDomain>,
    snackbarHostState: SnackbarHostState,
    onUiEvent: (TodoListUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Todo List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onUiEvent(TodoListUiEvent.OnAddTodoClick) },
                containerColor = Color(0xFF03DAC5)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Todo",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(todos) { todo ->
                TodoItem(
                    todo = todo,
                    onUiEvent = onUiEvent,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListContentPreview() {
    MvvmTrainingTheme {
        TodoListContent(
            todos = listOf(
                TodoDomain(1, "Buy groceries", "Milk, Eggs, Bread", false),
                TodoDomain(2, "Call Mom", null, true),
                TodoDomain(3, "Finish project", "Due next week", false)
            ),
            snackbarHostState = SnackbarHostState(),
            onUiEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListContentEmptyPreview() {
    MvvmTrainingTheme {
        TodoListContent(
            todos = emptyList(),
            snackbarHostState = SnackbarHostState(),
            onUiEvent = {}
        )
    }
}
