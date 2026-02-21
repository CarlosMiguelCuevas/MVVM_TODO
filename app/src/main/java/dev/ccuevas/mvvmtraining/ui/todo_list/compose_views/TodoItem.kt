package dev.ccuevas.mvvmtraining.ui.todo_list.compose_views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.ccuevas.mvvmtraining.domain.models.TodoDomain
import dev.ccuevas.mvvmtraining.ui.theme.MvvmTrainingTheme
import dev.ccuevas.mvvmtraining.ui.todo_list.TodoListUiEvent

@Composable
fun TodoItem(
    todo: TodoDomain,
    onUiEvent: (TodoListUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onUiEvent(TodoListUiEvent.OnTodoClick(todo)) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = todo.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { onUiEvent(TodoListUiEvent.DeleteTodo(todo)) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Todo"
                    )
                }
            }
            todo.description?.let { description ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
        Checkbox(
            checked = todo.isDone,
            onCheckedChange = { isChecked ->
                onUiEvent(TodoListUiEvent.OnDoneChange(todo, isChecked))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    MvvmTrainingTheme {
        TodoItem(
            todo = TodoDomain(
                id= 1,
                title = "Buy groceries",
                description = "Milk, eggs, bread",
                isDone = false
            ),
            onUiEvent = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemDonePreview() {
    MvvmTrainingTheme {
        TodoItem(
            todo = TodoDomain(
                id = 2,
                title = "Complete project",
                description = "Finish the MVVM training app",
                isDone = true
            ),
            onUiEvent = { }
        )
    }
}
