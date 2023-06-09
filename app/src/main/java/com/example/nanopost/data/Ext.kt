package com.example.nanopost.data

import java.text.SimpleDateFormat
import java.util.*

fun Long.convertLongToTime (): String {
    val date = Date(this)
    return SimpleDateFormat("MMM dd, yyyy hh:mm:ss").format(date)
}