package com.example.dealdone.data.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar.formatToDateString(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy | HH:mm", Locale.getDefault())
    return dateFormat.format(this.time).toString()
}