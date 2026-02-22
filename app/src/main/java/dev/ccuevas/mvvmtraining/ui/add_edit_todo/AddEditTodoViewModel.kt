package dev.ccuevas.mvvmtraining.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ccuevas.mvvmtraining.domain.TodoRepository
import dev.ccuevas.mvvmtraining.domain.models.TodoDomain
import dev.ccuevas.mvvmtraining.util.UiGenericEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var todo by mutableStateOf<TodoDomain?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiGenericEvent = Channel<UiGenericEvent>()
    val uiGenericEvent = _uiGenericEvent.receiveAsFlow()

    init {
        val todoIdToShow = savedStateHandle.get<Int>("todoId")!!
        if(todoIdToShow >= 0){
            viewModelScope.launch {
                repository.getTodoById(todoIdToShow)?.let {
                    title = it.title
                    description = it.description ?: ""
                    todo = it
                }
            }
        }
    }

    fun onUiEvent(event: UiEvent) {
        when(event){
            is UiEvent.OnTitleChange -> {
                title = event.title
            }

            is UiEvent.OnDescriptionChange -> {
                description = event.description
            }

            UiEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if(title.isBlank()){
                        sendUiGenericEvent(
                            UiGenericEvent.ShowSnackbar("Title can't be empty")
                        )
                        return@launch
                    }

                    repository.insertTodo(TodoDomain(
                        id = todo?.id,
                        title = title,
                        description = description,
                        isDone = todo?.isDone ?: false
                    ))
                    sendUiGenericEvent(UiGenericEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiGenericEvent(event: UiGenericEvent) {
        viewModelScope.launch {
            _uiGenericEvent.send(event)
        }
    }
}