package dev.ccuevas.mvvmtraining.ui.todo_list.compose_views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.ccuevas.mvvmtraining.domain.models.TodoDomain
import dev.ccuevas.mvvmtraining.ui.theme.MvvmTrainingTheme
import dev.ccuevas.mvvmtraining.ui.todo_list.TodoListViewModel
import dev.ccuevas.mvvmtraining.util.UiGenericEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onNavigate: (UiGenericEvent.Navigate) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    viewModel.todoListState.collectAsState(emptyList())

    Scaffold(
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
                onClick = { },
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
            items(1) { todo ->
                TodoItem(
                    todo = TodoDomain("testing", "this si a test", true),
                    onDeleteClick = {
                    },
                    onDoneChange = { isDone ->

                    },
                    onItemClick = {

                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    MvvmTrainingTheme {
        TodoListScreen()
    }
}
