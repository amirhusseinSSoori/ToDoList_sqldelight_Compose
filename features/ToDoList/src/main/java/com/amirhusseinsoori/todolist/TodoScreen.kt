package com.amirhusseinsoori.todolist

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.amirhusseinsoori.common.ScreenRoute
import com.amirhusseinsoori.domain.entity.TodoModel
import com.amirhusseinsoori.todolist.component.SwipeToDeleteItems
import com.amirhusseinsoori.todolist.component.TodoItemList


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun TodoScreen(navController: NavController, viewModel: ToDoViewModel) {
    val list by viewModel._state.collectAsState()
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (add) = createRefs()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list.tooDoList, { todo: TodoModel -> todo.id!! }) { item ->
                SwipeToDeleteItems(
                    endToStart = { viewModel.handleEvent(TodoEvent.DeleteItemToDo(item)) },
                    startToEnd = { viewModel.handleEvent(TodoEvent.DeleteItemToDo(item)) },
                    showItems = {
                        TodoItemList(item = item, navController = navController)
                    })
            }
        }
        Card(
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    navController.navigate(ScreenRoute.AddDetails.route)
                }
                .constrainAs(add) {
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                },
            shape = CircleShape,
        ) {
            Image(
                painterResource(R.drawable.ic_baseline_add_24),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )

        }

    }
}
