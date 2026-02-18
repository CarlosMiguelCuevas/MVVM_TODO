package dev.ccuevas.mvvmtraining.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.ccuevas.mvvmtraining.data.models.Todo

@Database(
    entities = [Todo::class],
    version = 1
)
abstract class TodoDatabase: RoomDatabase() {
    abstract val dao: TodoDao
}