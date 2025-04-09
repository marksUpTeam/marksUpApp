package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile

import retrofit2.Response
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Teacher
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain
import java.io.IOException

class ProfileRepository(private val profileApi: ProfileApi) {
    private suspend fun getProfile(): Response<Profile>{
        return profileApi.getProfile()
    }
    private suspend fun getLinkerStudent(): Result<List<Teacher>> {
        val response = profileApi.getLinkerDataStudent()
        if (response.isSuccessful && response.body() != null){
            return Result.success(response.body()!!)
        }
        else {
            return Result.failure(IOException("Failed to fetch linker data for student"))
        }
    }
    private suspend fun getLinkerTeacher(): Result<List<Student>> {
        val response = profileApi.getLinkerDataTeacher()
        if (response.isSuccessful && response.body() != null){
            return Result.success(response.body()!!)
        }
        else {
            return Result.failure(IOException("Failed to fetch linker data for teacher"))
        }
    }

    suspend fun getProfileDomain(): Result<ProfileDomain> {
        val profileResponse = getProfile()
        if (profileResponse.isSuccessful && profileResponse.body() != null) {
            val profile = profileResponse.body()!!
            if (profile.student != null) {
                val linkerResponse = getLinkerStudent()
                profile.student.assignedTeachers = linkerResponse.getOrThrow()
            }
            else if (profile.teacher != null) {
                val linkerResponse = getLinkerTeacher()
                profile.teacher.assignedStudents = linkerResponse.getOrThrow()
            }
            val profileDomain = ProfileMapper().map(profile)
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

