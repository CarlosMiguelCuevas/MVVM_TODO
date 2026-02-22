package dev.ccuevas.mvvmtraining.ui.add_edit_todo

sealed class UiEvent {
    data class OnTitleChange(val title: String) : UiEvent()
    data class OnDescriptionChange(val description: String) : UiEvent()
    object OnSaveTodoClick : UiEvent()
}