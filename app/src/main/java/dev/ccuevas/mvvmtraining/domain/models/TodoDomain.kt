package dev.ccuevas.mvvmtraining.domain.models

class TodoDomain(
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false,
)