package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment

interface AssignmentsApi {

    @GET("api/assignments")
    suspend fun getAllAssignments(): Response<List<Assignment>>

    @POST("api/assignments")
    suspend fun addAssignment(@Body assignment: Assignment): Response<Assignment>

    @Multipart
    @POST("api/assignments")
    suspend fun attachFile(
        @Part file: MultipartBody.Part,
    ): Response<Unit>
}