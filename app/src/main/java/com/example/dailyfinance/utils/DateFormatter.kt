package com.example.dailyfinance.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatTanggal(time: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
    return sdf.format(Date(time))
}
