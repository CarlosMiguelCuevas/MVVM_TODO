package dev.ccuevas.mvvmtraining.data

import dev.ccuevas.mvvmtraining.data.database.TodoDao
import dev.ccuevas.mvvmtraining.data.transformer.toTodo
import dev.ccuevas.mvvmtraining.data.transformer.toTodoDomain
import dev.ccuevas.mvvmtraining.domain.TodoRepository
import dev.ccuevas.mvvmtraining.domain.models.TodoDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao
): TodoRepository {
    override suspend fun insertTodo(todo: TodoDomain) {
        dao.insertTodo(todo.toTodo())
    }

    override suspend fun deleteTodo(todo: TodoDomain) {
        dao.deleteTodo(todo.toTodo())
    }

    override suspend fun getTodoById(id: Int): TodoDomain? =
        dao.getTodoById(id)?.toTodoDomain()

    override fun getTodos(): Flow<List<TodoDomain>> =
        dao.getAllTodos().map {
            it.map { todo ->
                todo.toTodoDomain()
            }
        }
}