package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming
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

    @GET("api/files/{filename}")
    @Streaming
    suspend fun downloadFile(@Path("filename") filename: String): Response<ResponseBody>

}