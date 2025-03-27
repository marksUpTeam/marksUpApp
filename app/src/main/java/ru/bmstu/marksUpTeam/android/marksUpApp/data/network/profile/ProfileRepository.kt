package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile

import android.content.Context
import retrofit2.Response
import retrofit2.Retrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicRetrofit
import java.io.IOException
import kotlin.Result

class ProfileRepository(api: String, context: Context) {
    private val retrofit: Retrofit = getBasicRetrofit(context, api)
    private val profileApi = retrofit.create(ProfileApi::class.java)

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

