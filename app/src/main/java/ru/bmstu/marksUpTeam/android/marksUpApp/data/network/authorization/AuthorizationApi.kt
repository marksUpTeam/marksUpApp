package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthorizationApi {

    @GET("api")
    suspend fun testProfile(@Header("Authorization") jwt: String): Response<Boolean>
}