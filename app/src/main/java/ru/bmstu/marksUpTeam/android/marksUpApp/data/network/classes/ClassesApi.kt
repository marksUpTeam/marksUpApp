package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class

interface ClassesApi {
    @GET("api/classes")
    suspend fun getClasses(): Response<List<Class>>

    @POST("api/classes")
    suspend fun addClass(@Body clazz: Class): Response<Class>
}