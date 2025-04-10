package ru.bmstu.marksUpTeam.android.marksUpApp.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.TokenHolder

class AuthorizationInterceptor(private val tokenHolder: TokenHolder): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = "Bearer ${tokenHolder.jwt}"
        val original = chain.request()
        val new = original.newBuilder().header("Authorization", jwt).build()
        return chain.proceed(new)
    }
}