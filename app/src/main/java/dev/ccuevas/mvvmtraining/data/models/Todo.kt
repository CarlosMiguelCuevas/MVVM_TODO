package dev.ccuevas.mvvmtraining.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Todo(
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false,
    @PrimaryKey val id: Int? = null
)