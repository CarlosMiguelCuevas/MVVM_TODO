package dev.ccuevas.mvvmtraining.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ccuevas.mvvmtraining.domain.TodoRepository
import dev.ccuevas.mvvmtraining.domain.models.TodoDomain
import dev.ccuevas.mvvmtraining.navigation.Routes
import dev.ccuevas.mvvmtraining.util.UiGenericEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val todoListState = repository.getTodos()

    private val _uiGenericEvent = Channel<UiGenericEvent>()
    val uiGenericEvent = _uiGenericEvent.receiveAsFlow()

    private var recentlyDeletedTodo: TodoDomain? = null

    fun onUiEvent(event: TodoListUiEvent) {
        when (event) {
            is TodoListUiEvent.DeleteTodo -> {
                viewModelScope.launch {
                    recentlyDeletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    sendUiGenericEvent(
                        UiGenericEvent.ShowSnackbar(
                            message = "Todo deleted",
                            action = "Undo"
                        )
                    )
                }
            }

            is TodoListUiEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(isDone = event.isDone)
                    )
                }
            }

            is TodoListUiEvent.OnUndoDeleteClick -> {
                recentlyDeletedTodo?.let { todo ->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
                }
            }

            TodoListUiEvent.OnAddTodoClick -> {
                sendUiGenericEvent(UiGenericEvent.Navigate(Routes.ADD_EDIT_TODO))
            }

            is TodoListUiEvent.OnTodoClick -> {
                sendUiGenericEvent(UiGenericEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
        }
    }

    private fun sendUiGenericEvent(event: UiGenericEvent) {
        viewModelScope.launch {
            _uiGenericEvent.send(event)
        }
    }
}