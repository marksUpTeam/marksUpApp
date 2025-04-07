package ru.bmstu.marksUpTeam.android.marksUpApp.tools

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

fun formatTime(time: LocalTime): String {
    return time.hour.toString().padStart(2, '0') + ":" + time.minute.toString().padStart(2, '0')
}


fun formatDate(date: LocalDate): String {
    return date.dayOfMonth.toString().padStart(2, '0') + "." + date.monthNumber.toString()
        .padStart(2, '0') + "." + date.year
}