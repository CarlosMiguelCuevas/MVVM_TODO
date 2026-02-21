package dev.ccuevas.mvvmtraining.data.transformer

import dev.ccuevas.mvvmtraining.data.models.Todo
import dev.ccuevas.mvvmtraining.domain.models.TodoDomain

fun TodoDomain.toTodo(id: Int? = null): Todo {
    return Todo(
        title = this.title,
        description = this.description,
        isDone = this.isDone,
        id = id
    )
}

fun Todo.toTodoDomain(): TodoDomain {
    return TodoDomain(
        id = this.id!!,
        title = this.title,
        description = this.description,
        isDone = this.isDone
    )
}