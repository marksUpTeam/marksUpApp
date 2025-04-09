package ru.bmstu.marksUpTeam.android.marksUpApp.ui.registration

import ru.bmstu.marksUpTeam.android.marksUpApp.domain.InvitationDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain

sealed class RegistrationState {
    data object Loading : RegistrationState()
    sealed class Error : RegistrationState() {
        data class GeneralError(val message: String) : Error()
        data object NotInvitedError : Error()
        data object AlreadyExistsError : Error()
    }
    data class Content(val invite: InvitationDomain) : RegistrationState()
    data class Finished(val profile: ProfileDomain) : RegistrationState()
}