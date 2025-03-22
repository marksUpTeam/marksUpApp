package ru.bmstu.marksUpTeam.android.marksUpApp.ui.grade

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseStudent


data class GradesState(
    val student: Student
)

val baseGradesState = GradesState(baseStudent)