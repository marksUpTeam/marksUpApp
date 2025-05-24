package ru.bmstu.marksUpTeam.android.marksUpApp.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain

interface LessonApi {
    @GET("")
    suspend fun getStudent(): Response<List<Student>>

    @GET("")
    suspend fun getDiscipline(): Response<List<Discipline>>

    @GET("")
    suspend fun getProfile(): Response<ProfileDomain>

    @POST("")
    suspend fun postLesson(@Body lesson: Lesson): Response<Unit>
}