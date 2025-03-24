package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.AssignmentDomain
import kotlinx.datetime.LocalDate

class AssignmentsMapper {
    fun map(assignment: Assignment): AssignmentDomain {
        return AssignmentDomain(
            id = assignment.id,
            student = assignment.student,
            teacher = assignment.teacher,
            discipline = assignment.discipline,
            issuedOn = assignment.issuedOn,
            deadline = assignment.deadline,
            description = assignment.description,
            status = assignment.status,
            grade = assignment.grade ?: 0
        )
    }

    fun mapList(assignments: List<Assignment>): List<AssignmentDomain> {
        return assignments.map { map(it) }
    }

    fun toDto(assignmentDomain: AssignmentDomain): Assignment {
        return Assignment(
            id = assignmentDomain.id,
            student = assignmentDomain.student,
            teacher = assignmentDomain.teacher,
            discipline = assignmentDomain.discipline,
            issuedOn = assignmentDomain.issuedOn,
            deadline = assignmentDomain.deadline,
            description = assignmentDomain.description,
            status = assignmentDomain.status,
            grade = assignmentDomain.grade
        )
    }
}