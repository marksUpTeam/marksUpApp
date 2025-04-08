package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile

import retrofit2.Response
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain
import java.io.IOException

class ProfileRepository(private val profileApi: ProfileApi) {
    private suspend fun getProfile(): Response<Profile>{
        return profileApi.getProfile()
    }

    suspend fun getProfileDomain(): Result<ProfileDomain> {
        val profileResponse = getProfile()
        if (profileResponse.isSuccessful && profileResponse.body() != null) {
            val profileDomain = ProfileMapper().map(profileResponse.body()!!)
            return Result.success(profileDomain)
        }
        else return Result.failure(IOException(profileResponse.errorBody()?.string() ?: "Something went wrong"))
    }
    private suspend fun modifyProfile(profile: Profile): Response<String> {
        return profileApi.modifyProfile(profile)
    }

    suspend fun modifyProfileDomain(profileDomain: ProfileDomain): Result<String> {
        val profileToPost = ProfileMapper().toDto(profileDomain)
        val postResult = modifyProfile(profileToPost)
        return if (postResult.isSuccessful && postResult.body() != null) {
            Result.success(postResult.body()!!)
        } else Result.failure(IOException(postResult.errorBody()?.string() ?: "Something went wrong"))
    }
}

