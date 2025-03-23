package ru.bmstu.marksUpTeam.android.marksUpApp.ui.Schedule

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseClass
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseTeacherProfile

data class ScheduleState(
    val classes: List<Class>,
    val profile: Profile

)
val baseSchedule = ScheduleState(listOf(baseClass), baseTeacherProfile)