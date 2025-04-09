package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.registration

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Invitation
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.InvitationDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.InvitationType

class RegistrationMapper {
    fun map(invite: Invitation): InvitationDomain {
        return InvitationDomain(
            id = invite.id,
            identifier = invite.identifier,
            invitationType = when {
                invite.childrenList != null -> InvitationType.ParentInvite(invite.childrenList)
                invite.studentList != null -> InvitationType.TeacherInvite(invite.studentList)
                invite.teacherList != null -> InvitationType.StudentInvite(invite.teacherList)
                else -> throw Exception("Cannot map to InvitationDomain")
            }
        )
    }
}