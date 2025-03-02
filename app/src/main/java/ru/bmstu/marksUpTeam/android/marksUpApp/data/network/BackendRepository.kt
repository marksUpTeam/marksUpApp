package ru.bmstu.marksUpTeam.android.marksUpApp.data.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class BackendRepository(api: String) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://$api")
        .addConverterFactory(Json {ignoreUnknownKeys = true}
            .asConverterFactory("application/json; charset=UTF-8".toMediaType()))
        .build()

    private val backendApi = retrofit.create(BackendApi::class.java)

    suspend fun testProfile(jwt: String): Boolean{
        val response = backendApi.testProfile("Bearer $jwt")
        return response.isSuccessful
    }
}