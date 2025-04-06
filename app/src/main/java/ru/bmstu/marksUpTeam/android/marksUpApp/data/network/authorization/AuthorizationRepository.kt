package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization

import retrofit2.Retrofit

class AuthorizationRepository(private val retrofit: Retrofit) {

    private val authorizationApi = retrofit.create(AuthorizationApi::class.java)


    suspend fun testProfile(jwt: String): Boolean{
        val response = authorizationApi.testProfile("Bearer $jwt")
        if (response.isSuccessful && response.body() != null){
            return response.body()!! == "exists"
        }
        else {
            return false
        }
    }
}