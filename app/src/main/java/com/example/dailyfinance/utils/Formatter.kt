package com.example.dailyfinance.utils

import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(value: Int): String {
    val localeID = Locale("in", "ID")
    val formatter = NumberFormat.getNumberInstance(localeID)
    return formatter.format(value)
}
