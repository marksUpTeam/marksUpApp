package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.domain.PersonType
import ru.bmstu.marksUpTeam.android.marksUpApp.data.domain.ProfileDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.profile.ProfileState

class ProfileMapper() {
    fun map(profile: Profile): ProfileDomain {
        return ProfileDomain(
            id = profile.id,
            username = profile.username,
            personType = when {
                profile.student != null -> PersonType.StudentType(profile.student)
                profile.teacher != null -> PersonType.TeacherType(profile.teacher)
                profile.parent != null -> PersonType.ParentType(profile.parent)
                else -> throw Exception("Cannot map to ProfileDomain")
            }
        )
    }

    fun demap(profileDomain: ProfileDomain): Profile {
        return Profile(
            id = profileDomain.id,
            username = profileDomain.username,
            parent = if (profileDomain.personType is PersonType.ParentType) profileDomain.personType.parent else null,
            student = if (profileDomain.personType is PersonType.StudentType) profileDomain.personType.student else null,
            teacher = if (profileDomain.personType is PersonType.TeacherType) profileDomain.personType.teacher else null,
        )
    }
}