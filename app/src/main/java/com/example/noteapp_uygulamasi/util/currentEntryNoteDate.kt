package com.example.noteapp_uygulamasi.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun currentEntryNoteDate(): String {
    val calender = Calendar.getInstance()

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    return dateFormat.format(calender.time)
}
