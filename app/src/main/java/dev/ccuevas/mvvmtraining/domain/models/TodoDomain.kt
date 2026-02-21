package dev.ccuevas.mvvmtraining.domain.models

data class TodoDomain(
    val id: Int,
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false,
)