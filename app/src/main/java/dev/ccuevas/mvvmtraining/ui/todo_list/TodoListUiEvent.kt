package dev.ccuevas.mvvmtraining.ui.todo_list

import dev.ccuevas.mvvmtraining.domain.models.TodoDomain

sealed class TodoListUiEvent {
    data class DeleteTodo(val todo: TodoDomain) : TodoListUiEvent()
    data class OnDoneChange(val todo: TodoDomain, val isDone: Boolean) : TodoListUiEvent()
    object OnUndoDeleteClick : TodoListUiEvent()
    data class OnTodoClick(val todo: TodoDomain) : TodoListUiEvent()
    object OnAddTodoClick : TodoListUiEvent()
}