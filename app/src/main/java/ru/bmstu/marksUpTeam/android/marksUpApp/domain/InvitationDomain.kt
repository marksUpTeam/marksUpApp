package ru.bmstu.marksUpTeam.android.marksUpApp.domain

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Teacher

sealed class InvitationType {
    data class TeacherInvite(val students: List<Student>): InvitationType()
    data class StudentInvite(val teachers: List<Teacher>): InvitationType()
    data class ParentInvite(val children: List<Student>): InvitationType()
}

data class InvitationDomain(
    val id: Long,
    val identifier: String,
    val invitationType: InvitationType,
)