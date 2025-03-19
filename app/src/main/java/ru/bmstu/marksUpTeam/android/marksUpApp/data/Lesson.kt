package ru.bmstu.marksUpTeam.android.marksUpApp.data

data class LessonState(
    val lesson: Class,
    val profile: Profile
)

val baseLesson = LessonState(baseClass, baseTeacherProfile)