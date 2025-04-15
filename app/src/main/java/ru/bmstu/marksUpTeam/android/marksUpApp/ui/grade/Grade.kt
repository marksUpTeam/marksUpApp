package ru.bmstu.marksUpTeam.android.marksUpApp.ui.grade

import ru.bmstu.marksUpTeam.android.marksUpApp.data.DisciplineGrade
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseStudent


data class GradesState(val grades: List<DisciplineGrade>)

val baseGradesState = GradesState(baseStudent.disciplineGrades)