package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.registration

import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileMapper
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.InvitationDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain
import java.io.IOException


data object AccountAlreadyExistsException : Exception() {
    private fun readResolve(): Any = AccountAlreadyExistsException
}


class RegistrationRepository(val registrationApi: RegistrationApi) {
    suspend fun register(profile: ProfileDomain): Result<ProfileDomain> {
        val profileDto = ProfileMapper().toDto(profile)
        val response = registrationApi.postNewProfile(profileDto)
        if (response.code() == 400){
            return Result.failure(AccountAlreadyExistsException)
        }
        return if (response.isSuccessful && response.body() != null) {
            Result.success(ProfileMapper().map(response.body()!!))
        } else {
            Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
        }
    }
    suspend fun getInvite(): Result<InvitationDomain> {
        val response = registrationApi.getInvitation()
        if (response.isSuccessful && response.body() != null) {
            val invite = response.body()!!
            val inviteDomain = RegistrationMapper().map(invite)
            return Result.success(inviteDomain)
        }
        else {
            return Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
        }
    }

}