package ru.bmstu.marksUpTeam.android.marksUpApp.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment
import ru.bmstu.marksUpTeam.android.marksUpApp.data.AssignmentStatus
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Teacher

sealed class AssignmentDueStatus {
    data class IsDue(val assignment: Assignment) : AssignmentDueStatus()
    data object NotDue : AssignmentDueStatus()
}



data class ClassDomain(
    val id: Long,
    val discipline: Discipline,
    val teacher: Teacher,
    val student: Student,
    val datetimeStart: LocalDateTime,
    val datetimeEnd: LocalDateTime,
    val grade: Int,
    val assignmentDueStatus: AssignmentDueStatus,
    val teacherComment: String,
)