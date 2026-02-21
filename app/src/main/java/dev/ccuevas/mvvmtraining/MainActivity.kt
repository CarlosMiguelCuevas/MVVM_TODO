package dev.ccuevas.mvvmtraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.ccuevas.mvvmtraining.ui.theme.MvvmTrainingTheme
import dev.ccuevas.mvvmtraining.ui.todo_list.compose_views.TodoListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MvvmTrainingTheme {
                TodoListScreen()
            }
        }
    }
}
