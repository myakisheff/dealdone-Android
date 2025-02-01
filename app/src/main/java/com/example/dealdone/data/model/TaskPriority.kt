package com.example.dealdone.data.model

import androidx.annotation.StringRes
import com.example.dealdone.R

enum class TaskPriority(@StringRes val title: Int) {
    LOW(title = R.string.low_priority),
    MEDIUM(title = R.string.medium_priority),
    HIGH(title = R.string.high_priority)
}