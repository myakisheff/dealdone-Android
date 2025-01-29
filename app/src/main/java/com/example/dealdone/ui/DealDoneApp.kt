package com.example.dealdone.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dealdone.ui.screen.task.TaskScreen
import com.example.dealdone.ui.screen.task.TaskViewModel

@Composable
fun DealDoneApp() {
    val navController: NavHostController = rememberNavController()

    val viewModel : TaskViewModel = viewModel()
    val taskUiState = viewModel.currentTaskUiState.collectAsState().value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = DealDoneScreen.TASK_LIST.name
        ) {
            composable(route = DealDoneScreen.TASK_LIST.name) {
                TaskScreen(
                    taskUiState = taskUiState,
                    onBackClick = viewModel::selectPreviousTask,
                    onTaskClick = { viewModel.selectTask(it) },
                    expandTask = viewModel::expandCurrentTask,
                    subtasks = viewModel.getSubtasks()
                )
            }

            composable(route = DealDoneScreen.TASK_CREATION.name) {

            }

            composable(route = DealDoneScreen.SETTINGS.name) {

            }
        }
    }
}