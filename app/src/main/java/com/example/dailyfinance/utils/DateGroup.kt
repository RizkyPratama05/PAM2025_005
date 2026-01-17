package com.example.dailyfinance.utils

import java.util.*

fun groupLabel(time: Long): String {
    val now = Calendar.getInstance()
    val target = Calendar.getInstance().apply { timeInMillis = time }

    return when {
        isSameDay(now, target) -> "Hari ini"
        isYesterday(now, target) -> "Kemarin"
        else -> formatTanggal(time)
    }
}

private fun isSameDay(a: Calendar, b: Calendar): Boolean =
    a.get(Calendar.YEAR) == b.get(Calendar.YEAR) &&
            a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR)

private fun isYesterday(now: Calendar, target: Calendar): Boolean {
    now.add(Calendar.DAY_OF_YEAR, -1)
    return isSameDay(now, target)
}
