package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.registration

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Invitation
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile

interface RegistrationApi {

    @POST("/api/registration")
    suspend fun postNewProfile(@Body profile: Profile): Response<Profile>

    @GET("/api/registration")
    suspend fun getInvitation(): Response<Invitation>

}