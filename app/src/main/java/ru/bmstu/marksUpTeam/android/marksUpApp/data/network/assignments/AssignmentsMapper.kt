package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments

import android.content.Context
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.AssignmentDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getFileUriByName

class AssignmentsMapper(private val context: Context, private val assignmentsRepository: AssignmentsRepository) {
    private suspend fun map(assignment: Assignment): AssignmentDomain {
        return coroutineScope {
            val filesUri = assignment.filesName.map { fileName ->
                async {
                    val uri = getFileUriByName(context, fileName)
                    if (uri == null) {
                        assignmentsRepository.downloadFile(fileName)
                    }
                    uri
                }
            }.awaitAll()

            AssignmentDomain(
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
    }


    suspend fun mapList(assignments: List<Assignment>): List<AssignmentDomain> {
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
            grade = assignmentDomain.grade,
            filesName = mutableListOf()
        )
    }
}