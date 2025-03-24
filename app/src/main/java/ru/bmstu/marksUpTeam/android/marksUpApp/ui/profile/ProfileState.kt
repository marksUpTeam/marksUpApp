package ru.bmstu.marksUpTeam.android.marksUpApp.ui.profile

import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain

sealed class ProfileState {
    data object Loading : ProfileState()
    data class Error(val errorMessage: String) : ProfileState()
    data class ContentTeacher(val profile: ProfileDomain) : ProfileState()
    data class ContentParent(val profile: ProfileDomain) : ProfileState()
    data class ContentStudent(val profile: ProfileDomain) : ProfileState()
}