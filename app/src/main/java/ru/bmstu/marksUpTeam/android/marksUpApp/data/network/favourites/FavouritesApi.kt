package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.favourites

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.bmstu.marksUpTeam.android.marksUpApp.data.FavouritesItem

interface FavouritesApi {
    @GET("api/user/favourites")
    suspend fun getFavourites(@Header("Authorization") jwt: String): Response<List<FavouritesItem>>

    @POST("api/user/favourites")
    suspend fun addFavourite(@Header("Authorization") jwt: String, @Body favouritesItem: FavouritesItem): Response<FavouritesItem>
}