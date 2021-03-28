package com.example.sampleproject.utils

import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*

fun Long.formatToDate():String{
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}

fun String.validateEmailFormat():Boolean{
    return if (TextUtils.isEmpty(this)) {
        false;
    } else {
        android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches();
    }
}