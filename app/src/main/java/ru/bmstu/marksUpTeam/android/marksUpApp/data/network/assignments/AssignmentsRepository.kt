package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments

import android.content.Context
import retrofit2.Retrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicRetrofit
import java.io.IOException

class AssignmentsRepository(api: String, context: Context, jwtUnformatted: String) {
    private val retrofit: Retrofit = getBasicRetrofit(context, api)
    private val assignmentsApi: AssignmentsApi = retrofit.create(AssignmentsApi::class.java)
    private val jwt = "Bearer $jwtUnformatted"

    suspend fun getAllAssignments(): Result<List<Assignment>>{
        val response = assignmentsApi.getAllAssignments(jwt)
        if (response.isSuccessful && response.body() != null){
            return Result.success(response.body()!!)
        }
        return Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
    }

    suspend fun addAssignment(assignment: Assignment): Result<Assignment>{
        val response = assignmentsApi.addAssignment(jwt, assignment)
        if (response.isSuccessful && response.body() != null){
            return Result.success(response.body()!!)
        }
        return Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
    }
}