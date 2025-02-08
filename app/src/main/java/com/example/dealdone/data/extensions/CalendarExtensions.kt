package com.example.dealdone.data.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar.formatToDateTimeString(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy | HH:mm", Locale.getDefault())
    return dateFormat.format(this.time).toString()
}

fun Calendar.formatToTimeString(): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(this.time).toString()
}

fun Calendar.formatToDateString(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(this.time).toString()
}

fun Calendar.isExpired(): Boolean {
    return this.timeInMillis > Calendar.getInstance().timeInMillis
}

