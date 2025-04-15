package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile

interface ProfileApi {

    @GET("api/user")
    suspend fun getProfile(): Response<Profile>

    @POST("api/user")
    suspend fun modifyProfile(@Body profile: Profile): Response<String>
}