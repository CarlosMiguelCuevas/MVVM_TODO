package dev.ccuevas.mvvmtraining.ui.add_edit_todo.compose_views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.ccuevas.mvvmtraining.ui.add_edit_todo.AddEditTodoViewModel
import dev.ccuevas.mvvmtraining.ui.add_edit_todo.UiEvent
import dev.ccuevas.mvvmtraining.ui.theme.MvvmTrainingTheme
import dev.ccuevas.mvvmtraining.util.UiGenericEvent

/**
 * Stateful composable - handles ViewModel, state collection, and navigation events
 */
@Composable
fun AddEditTodoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.uiGenericEvent.collect { event ->
            when (event) {
                is UiGenericEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiGenericEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )
                }
                else -> Unit
            }
        }
    }

    AddEditTodoContent(
        title = viewModel.title,
        description = viewModel.description,
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
fun AddEditTodoContent(
    title: String,
    description: String,
    snackbarHostState: SnackbarHostState,
    onUiEvent: (UiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Add/Edit Todo") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = { onUiEvent(UiEvent.OnSaveTodoClick) },
                    containerColor = Color(0xFF03DAC5)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save Todo",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                SnackbarHost(hostState = snackbarHostState)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TextField(
                value = title,
                onValueChange = { onUiEvent(UiEvent.OnTitleChange(it)) },
                placeholder = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = description,
                onValueChange = { onUiEvent(UiEvent.OnDescriptionChange(it)) },
                placeholder = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditTodoContentPreview() {
    MvvmTrainingTheme {
        AddEditTodoContent(
            title = "Go to Mass",
            description = "Every Sunady",
            snackbarHostState = SnackbarHostState(),
            onUiEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditTodoContentEmptyPreview() {
    MvvmTrainingTheme {
        AddEditTodoContent(
            title = "",
            description = "",
            snackbarHostState = SnackbarHostState(),
            onUiEvent = {}
        )
    }
}
