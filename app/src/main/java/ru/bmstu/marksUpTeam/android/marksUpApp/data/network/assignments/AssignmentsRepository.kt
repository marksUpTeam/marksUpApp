package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment
import java.io.IOException

class AssignmentsRepository(private val assignmentsApi: AssignmentsApi ,jwtUnformatted: String) {
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