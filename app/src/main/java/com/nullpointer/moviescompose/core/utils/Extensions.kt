package com.nullpointer.moviescompose.core.utils

import java.text.Format
import java.text.SimpleDateFormat
import java.util.*

fun Long.convertTime(): String? {
    val date = Date(this)
    val format: Format = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
    return format.format(date)
}
