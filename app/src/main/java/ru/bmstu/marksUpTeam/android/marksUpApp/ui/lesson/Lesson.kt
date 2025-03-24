package ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseClass
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.baseStudentProfileDomain

data class LessonState(
    val lesson: Class,
    val profile: ProfileDomain
)


val baseLesson = LessonState(baseClass, baseStudentProfileDomain)