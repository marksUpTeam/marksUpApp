package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization

import android.content.Context
import retrofit2.Retrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicRetrofit

class AuthorizationRepository(api: String, context: Context) {

    private val retrofit: Retrofit = getBasicRetrofit(context, api)

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