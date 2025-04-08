package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment

interface AssignmentsApi {

    @GET("api/assignments")
    suspend fun getAllAssignments(): Response<List<Assignment>>

    @POST("api/assignments")
    suspend fun addAssignment(@Body assignment: Assignment): Response<Assignment>
}