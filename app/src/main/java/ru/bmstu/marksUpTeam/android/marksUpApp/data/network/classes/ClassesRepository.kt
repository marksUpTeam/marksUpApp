package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes

import android.content.Context
import retrofit2.Retrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicInterceptedRetrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicRetrofit
import java.io.IOException

class ClassesRepository(api: String, context: Context) {
    private val retrofit: Retrofit = getBasicInterceptedRetrofit(context, api)
    private val classesApi = retrofit.create(ClassesApi::class.java)

    suspend fun getClasses(): Result<List<Class>>{
        val response = classesApi.getClasses()
        return if(response.isSuccessful && response.body() != null){
            Result.success(response.body()!!)
        } else Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
    }

    suspend fun addClass(clazz: Class): Result<Class>{
        val response = classesApi.addClass(clazz)
        return if(response.isSuccessful && response.body() != null){
            Result.success(response.body()!!)
        } else Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
    }
}