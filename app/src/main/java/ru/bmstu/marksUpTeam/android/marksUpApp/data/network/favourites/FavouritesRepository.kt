package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.favourites

import android.content.Context
import retrofit2.Response
import ru.bmstu.marksUpTeam.android.marksUpApp.data.FavouritesItem
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.FavouritesItemDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicInterceptedRetrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicRetrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getJwt
import java.io.IOException

class FavouritesRepository(api: String, context: Context, jwtUnformatted: String = "") {
    private val retrofit = getBasicInterceptedRetrofit(context, api, if(jwtUnformatted.isNotEmpty()) jwtUnformatted else getJwt(context) ?: "")
    private val favouritesApi = retrofit.create(FavouritesApi::class.java)

    private suspend fun getFavourites(): Response<List<FavouritesItem>> {
        return favouritesApi.getFavourites()
    }

    private suspend fun postFavourite(favouritesItem: FavouritesItem): Response<FavouritesItem>{
        return favouritesApi.addFavourite(favouritesItem)
    }

    suspend fun getFavouritesDomain(): Result<List<FavouritesItemDomain>>{
        val response = getFavourites()
        if (response.isSuccessful && response.body() != null) {
            val favouritesItemDomainList = FavouritesMapper().mapList(response.body()!!)
            return Result.success(favouritesItemDomainList)
        }
        else return Result.failure(IOException(response.errorBody().toString()))
    }

    suspend fun postFavouriteDomain(domain: FavouritesItemDomain): Result<FavouritesItemDomain>{
        val toPost = FavouritesMapper().toDto(domain)
        val postResult = postFavourite(toPost)
        return if (postResult.isSuccessful && postResult.body() != null) {
            Result.success(FavouritesMapper().map(postResult.body()!!))
        } else Result.failure(IOException(postResult.errorBody().toString()))
    }


}