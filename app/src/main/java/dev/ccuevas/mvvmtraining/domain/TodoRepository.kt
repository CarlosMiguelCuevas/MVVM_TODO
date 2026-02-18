package dev.ccuevas.mvvmtraining.domain

import dev.ccuevas.mvvmtraining.domain.models.TodoDomain
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insertTodo(todo: TodoDomain)
    suspend fun deleteTodo(todo: TodoDomain)
    suspend fun getTodoById(id: Int): TodoDomain?
    fun getTodos(): Flow<List<TodoDomain>>
}