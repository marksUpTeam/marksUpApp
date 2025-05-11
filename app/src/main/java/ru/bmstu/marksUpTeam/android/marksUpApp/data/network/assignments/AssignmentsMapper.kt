package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments
import android.net.Uri
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.AssignmentDomain

class AssignmentsMapper() {
     fun map(assignment: Assignment, filesUri:List<Uri?>): AssignmentDomain {


           return AssignmentDomain(
                id = assignment.id,
                student = assignment.student,
                teacher = assignment.teacher,
                discipline = assignment.discipline,
                issuedOn = assignment.issuedOn,
                deadline = assignment.deadline,
                description = assignment.description,
                status = assignment.status,
                grade = assignment.grade ?: 0,
                filesUri = filesUri
            )
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
            grade = assignmentDomain.grade,
            filesName = mutableListOf()
        )
    }
}