package ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseClass
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseStudentProfile

data class LessonState(
    val lesson: Class,
    val profile: Profile
)


val baseLesson = LessonState(baseClass, baseStudentProfile)