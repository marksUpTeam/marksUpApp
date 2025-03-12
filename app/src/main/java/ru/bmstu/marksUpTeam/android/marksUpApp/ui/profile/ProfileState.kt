package ru.bmstu.marksUpTeam.android.marksUpApp.ui.profile

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile

sealed class ProfileState {
    data object Loading : ProfileState()
    data class Error(val errorMessage: String) : ProfileState()

    data class ContentTeacher(val profile: Profile): ProfileState()
    data class ContentParent(val profile: Profile): ProfileState()
    data class ContentStudent(val profile: Profile): ProfileState()
}