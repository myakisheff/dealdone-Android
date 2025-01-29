package com.example.dealdone.ui

import androidx.annotation.StringRes
import com.example.dealdone.R

enum class DealDoneScreen(@StringRes val title: Int) {
    TASK_LIST(title = R.string.tasks),
    TASK_CREATION(title = R.string.create_new_task),
    SETTINGS(title = R.string.settings)
}