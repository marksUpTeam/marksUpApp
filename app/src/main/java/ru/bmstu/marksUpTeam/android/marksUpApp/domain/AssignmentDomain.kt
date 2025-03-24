package ru.bmstu.marksUpTeam.android.marksUpApp.domain

import kotlinx.datetime.LocalDate
import ru.bmstu.marksUpTeam.android.marksUpApp.data.AssignmentStatus
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Teacher

data class AssignmentDomain(
    val id: Long,
    val student: Student,
    val teacher: Teacher,
    val discipline: Discipline,
    val issuedOn: LocalDate,
    val deadline: LocalDate,
    val description: String,
    val status: AssignmentStatus,
    val grade: Int,
)