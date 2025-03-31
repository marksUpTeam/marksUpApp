package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments

import android.content.Context
import retrofit2.Retrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicInterceptedRetrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicRetrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getJwt
import java.io.IOException

class AssignmentsRepository(api: String, context: Context, jwtUnformatted: String = "") {
    private val retrofit: Retrofit = getBasicInterceptedRetrofit(context, api, if(jwtUnformatted.isNotEmpty()) jwtUnformatted else getJwt(context) ?: "")
    private val assignmentsApi: AssignmentsApi = retrofit.create(AssignmentsApi::class.java)

    suspend fun getAllAssignments(): Result<List<Assignment>>{
        val response = assignmentsApi.getAllAssignments()
        if (response.isSuccessful && response.body() != null){
            return Result.success(response.body()!!)
        }
        return Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
    }

    suspend fun addAssignment(assignment: Assignment): Result<Assignment>{
        val response = assignmentsApi.addAssignment(assignment)
        if (response.isSuccessful && response.body() != null){
            return Result.success(response.body()!!)
        }
        return Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
    }
}