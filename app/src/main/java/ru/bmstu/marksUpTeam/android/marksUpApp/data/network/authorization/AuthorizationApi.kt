package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthorizationApi {

    @GET("api")
    suspend fun testProfile(@Header("Authorization") jwt: String): Response<String>

    /*
        Тут оставляем Header@("Authorization"), так как во время вызова этого метода JWT ещё не сохранен,
        его попросту брать неоткуда. По сути, метод testProfile - проверка подлинности JWT, только после неё его можно сохранять.
     */
}