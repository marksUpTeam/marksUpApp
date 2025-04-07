package ru.bmstu.marksUpTeam.android.marksUpApp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(jwtUnformatted: String): Interceptor {
    val jwt: String = "Bearer $jwtUnformatted"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val new = original.newBuilder().header("Authorization", jwt).build()
        return chain.proceed(new)
    }
}