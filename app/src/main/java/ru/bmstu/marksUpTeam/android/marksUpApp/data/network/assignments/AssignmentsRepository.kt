package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments

import android.content.Context
import android.util.Log
import okhttp3.MultipartBody
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.AssignmentDomain
import java.io.IOException

class AssignmentsRepository(private val assignmentsApi: AssignmentsApi,private val context:Context) {

    suspend fun getAllAssignments(): Result<List<AssignmentDomain>> {
        val assignmentResponse = assignmentsApi.getAllAssignments()
        if (assignmentResponse.isSuccessful && assignmentResponse.body() != null) {
            val assignmentDomain = AssignmentsMapper(context).mapList(assignmentResponse.body()!!)
            return Result.success(assignmentDomain)
        }
        return Result.failure(IOException(assignmentResponse.errorBody()?.string() ?: "Something went wrong"))
    }

    suspend fun addAssignment(assignment: Assignment): Result<Assignment> {
        val response = assignmentsApi.addAssignment(assignment)
        if (response.isSuccessful && response.body() != null) {
            return Result.success(response.body()!!)
        }
        return Result.failure(IOException(response.errorBody()?.string() ?: "Something went wrong"))
    }

    suspend fun attachFile(file: MultipartBody.Part): Result<Unit> {
        Log.d("files", "request")
        val response = assignmentsApi.attachFile(file)
        if (response.isSuccessful && response.body() != null) {
            return Result.success(response.body()!!)
        }
        return Result.failure(
            IOException(
                response.errorBody()?.string() ?: "Something went wrong"
            )
        )
    }
}