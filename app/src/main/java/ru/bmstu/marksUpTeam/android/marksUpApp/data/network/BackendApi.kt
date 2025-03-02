package ru.bmstu.marksUpTeam.android.marksUpApp.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface BackendApi {

    @GET("api")
    suspend fun testProfile(@Header("Authorization") jwt: String): Response<Boolean>
}