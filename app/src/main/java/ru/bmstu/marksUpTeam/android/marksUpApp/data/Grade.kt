package ru.bmstu.marksUpTeam.android.marksUpApp.data


data class GradesState(
    val student: Student
)

val baseGradesState = GradesState(baseStudent)