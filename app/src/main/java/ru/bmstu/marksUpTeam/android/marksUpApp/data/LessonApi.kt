package ru.bmstu.marksUpTeam.android.marksUpApp.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LessonApi {
    @GET("api/students")
    suspend fun getStudent(): Response<List<Student>>

    @GET("api/disciplines")
    suspend fun getDiscipline(): Response<List<Discipline>>

    @POST("api/lesson")
    suspend fun postLesson(@Body lesson: Lesson): Response<Unit>
}