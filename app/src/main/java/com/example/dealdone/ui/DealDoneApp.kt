package com.example.dealdone.ui

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dealdone.R
import com.example.dealdone.ui.component.NavigationItemContent
import com.example.dealdone.ui.screen.newtask.NewTaskScreen
import com.example.dealdone.ui.screen.newtask.NewTaskViewModel
import com.example.dealdone.ui.screen.task.TaskScreen
import com.example.dealdone.ui.screen.task.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealDoneApp() {
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()


    val taskViewModel: TaskViewModel = viewModel()
    val taskUiState = taskViewModel.currentTaskUiState.collectAsState().value

    val newTaskViewModel: NewTaskViewModel = viewModel()
    val newTaskUiState = newTaskViewModel.newTaskUiState.collectAsState().value
    val newDefaultTaskUiState = newTaskViewModel.newDefaultTaskUiState.collectAsState().value

    val currentScreen = DealDoneScreen.valueOf(
        backStackEntry?.destination?.route ?: DealDoneScreen.TASK_LIST.name
    )

    val navigationItemContentList = listOf(
        NavigationItemContent(
            screen = DealDoneScreen.TASK_LIST,
            icon = Icons.AutoMirrored.Filled.List,
            text = stringResource(R.string.task_list)
        ),
        NavigationItemContent(
            screen = DealDoneScreen.TASK_CREATION,
            icon = Icons.Filled.Create,
            text = stringResource(R.string.create_new_task)
        ),
        NavigationItemContent(
            screen = DealDoneScreen.SETTINGS,
            icon = Icons.Default.Settings,
            text = stringResource(R.string.settings)
        )
    )

    val title = when(currentScreen) {
        DealDoneScreen.TASK_LIST -> taskUiState.selectedTask?.name ?: stringResource(currentScreen.title)
        DealDoneScreen.TASK_CREATION,
        DealDoneScreen.SETTINGS -> stringResource(currentScreen.title)
    }

    Scaffold(
        topBar = {
            DealDoneTopBar(
                title = title,
                canNavigateBack = taskUiState.selectedTask != null && currentScreen == DealDoneScreen.TASK_LIST,
                onBackClick = taskViewModel::selectPreviousTask,
                onHomeClick = taskViewModel::deselectTask
            )
        },
        bottomBar = {
            DealDoneBottomBar(
                currentScreen = currentScreen,
                navigationItemContentList = navigationItemContentList,
                onTabPressed = { screen: DealDoneScreen ->
                    navController.navigate(route = screen.name)
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DealDoneScreen.TASK_LIST.name
        ) {
            composable(route = DealDoneScreen.TASK_LIST.name) {
                TaskScreen(
                    taskUiState = taskUiState,
                    onTaskClick = { taskViewModel.selectTask(it) },
                    expandTask = taskViewModel::expandCurrentTask,
                    subtasks = taskViewModel.getSubtasks(),
                    modifier = Modifier.padding(innerPadding)
                )
            }

            composable(route = DealDoneScreen.TASK_CREATION.name) {
                NewTaskScreen(
                    isFastCreation = newTaskUiState.isFastModeOfCreationTask,
                    onCreatePressed = {
                        taskViewModel.addNewTasks(
                            newTaskViewModel.addNewTask(
                                currentTaskUiState = taskUiState
                            )
                        )
                    },
                    onChangeModePressed = { isFastMode ->
                        newTaskViewModel.updateNewTaskMode(
                            isFastMode
                        )
                    },
                    onFastCreationTextChanged = { fastText ->
                        newTaskViewModel.updateNewTaskText(
                            fastText
                        )
                    },
                    fastCreationText = newTaskUiState.fastCreationTaskText,
                    defaultTaskUiState = newDefaultTaskUiState,
                    onDefaultTaskTitleChanged = { defaultTitle ->
                        newTaskViewModel.changeDefaultTaskTitle(
                            defaultTitle
                        )
                    },
                    onDefaultTaskDescriptionChanged = { defaultDescription ->
                        newTaskViewModel.changeDefaultDescription(
                            defaultDescription
                        )
                    },
                    onChangeSelectedPriority = { defaultTaskPriority ->
                        newTaskViewModel.changeDefaultPriority(
                            defaultTaskPriority
                        )
                    },
                    onChangeInfinityTime = {isInfinity ->
                        newTaskViewModel.changeInfinityTime(isInfinity)
                    },
                    onTimeConfirm = newTaskViewModel::changeTime,
                    onTimeDismiss = newTaskViewModel::hideTimePicker,
                    onDatePickerClick = newTaskViewModel::showDatePicker,
                    onTimePickerClick = newTaskViewModel::showTimePicker,
                    onDateConfirm = newTaskViewModel::changeDate,
                    onDateDismiss = newTaskViewModel::hideDatePicker,
                    modifier = Modifier.padding(
                        top = innerPadding.calculateTopPadding(),
                        start = innerPadding.calculateStartPadding(layoutDirection = LocalLayoutDirection.current)
                                + dimensionResource(R.dimen.small_padding),
                        end = innerPadding.calculateEndPadding(layoutDirection = LocalLayoutDirection.current)
                                + dimensionResource(R.dimen.small_padding),
                        bottom = innerPadding.calculateBottomPadding()

                    )
                )
            }

            composable(route = DealDoneScreen.SETTINGS.name) {
                Text(text = "Settings")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealDoneTopBar(
    title: String,
    canNavigateBack: Boolean,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = {
            if(canNavigateBack) {
                IconButton(onClick = onHomeClick) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = stringResource(R.string.home)
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun DealDoneBottomBar(
    currentScreen: DealDoneScreen,
    onTabPressed: ((DealDoneScreen) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        for(navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentScreen == navItem.screen,
                onClick = { onTabPressed(navItem.screen) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                }
            )
        }
    }
}