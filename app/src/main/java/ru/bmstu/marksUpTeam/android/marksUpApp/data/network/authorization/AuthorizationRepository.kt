package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization

class AuthorizationRepository(private val authorizationApi:AuthorizationApi) {

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