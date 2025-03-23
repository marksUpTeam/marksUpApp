package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes

import android.content.Context
import retrofit2.Retrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicRetrofit
import java.io.IOException

class ClassesRepository(api: String, context: Context, jwtUnformatted: String) {
    private val retrofit: Retrofit = getBasicRetrofit(context, api)
    private val classesApi = retrofit.create(ClassesApi::class.java)
    private val jwt = "Bearer $jwtUnformatted"

    suspend fun getClasses(): Result<List<Class>>{
        val response = classesApi.getClasses(jwt)
        return if(response.isSuccessful && response.body() != null){
            Result.success(response.body()!!)
        } else Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
    }

    suspend fun addClass(clazz: Class): Result<Class>{
        val response = classesApi.addClass(jwt, clazz)
        return if(response.isSuccessful && response.body() != null){
            Result.success(response.body()!!)
        } else Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
    }
}