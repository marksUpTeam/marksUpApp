package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class
import java.io.IOException

class ClassesRepository(private val classesApi:ClassesApi,jwtUnformatted:String) {
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