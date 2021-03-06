package com.example.sampleproject.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatToDate():String{
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}