package ru.bmstu.marksUpTeam.android.marksUpApp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class as LessonClass

object NotificationManager {

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleClassNotification(context: Context, classItem: LessonClass) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val startInstant = classItem.datetimeStart.toInstant(TimeZone.currentSystemDefault())
        val timeBeforeClass = startInstant.minus(30, DateTimeUnit.MINUTE, TimeZone.currentSystemDefault())
        val now = Clock.System.now()

        if (timeBeforeClass > now) {
            createNotification(context, alarmManager, classItem, timeBeforeClass,
                "Через 30 минут: ${classItem.discipline.name}")
        }
        if (startInstant > now) {
            createNotification(context, alarmManager, classItem, startInstant,
                "Сейчас: ${classItem.discipline.name}")
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun createNotification(
        context: Context,
        alarmManager: AlarmManager,
        classItem: LessonClass,
        triggerTime: Instant,
        message: String
    ) {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("subject", classItem.discipline.name)
            putExtra("time", message)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            classItem.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            triggerTime.toEpochMilliseconds(),
            pendingIntent
        )
    }
}