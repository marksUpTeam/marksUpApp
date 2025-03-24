package ru.bmstu.marksUpTeam.android.marksUpApp.ui.Schedule

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseClass
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.baseTeacherProfileDomain

data class ScheduleState(
    val classes: List<Class>,
    val profile: ProfileDomain,
    val route: String? = null
)
val baseSchedule = ScheduleState(listOf(baseClass), baseTeacherProfileDomain)