package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.registration

import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileMapper
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain
import java.io.IOException

// RegistrationRepository uses ProfileMapper

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
}