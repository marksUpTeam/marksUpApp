package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments

import android.net.Uri
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment
import java.io.IOException

class AssignmentsRepository(private val assignmentsApi: AssignmentsApi) {

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

    suspend fun attachFile(uri: Uri): Result<Uri>{
        val response = assignmentsApi.attachFile(uri)
        if (response.isSuccessful && response.body() != null){
            return Result.success(response.body()!!)
        }
        return Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
    }
}