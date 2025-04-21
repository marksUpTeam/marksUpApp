package ru.bmstu.marksUpTeam.android.marksUpApp.ui.assignment

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseAssignment
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments.AssignmentsRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getFileForRequest

class AssignmentViewModel(private val assignmentsRepository: AssignmentsRepository) : ViewModel() {
    private val _stateFlow: MutableStateFlow<AssignmentState> = MutableStateFlow(
        AssignmentState(
            listOf(baseAssignment)
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

    fun pickFile(uri: Uri, assignmentId:Long, contentResolver: ContentResolver){
        val updatedAssignments = _stateFlow.value.assignments.map {
            if (it.id == assignmentId) {
                it.copy(files = it.files + uri.toString())
            } else it
        }

        _stateFlow.value = _stateFlow.value.copy(assignments = updatedAssignments)
        Log.d("files", _stateFlow.value.assignments.toString())
        viewModelScope.launch {
            runCatching {
                val file = getFileForRequest(fileUri = uri, contentResolver )
                val assignmentsResponse = file?.let { assignmentsRepository.attachFile(it) }
                /*
                if(assignmentsResponse.isSuccess){
                   val assignmentUri = assignmentsResponse.getOrNull()?: throw Exception("Bad response")
                    _fileUri.value.add(_fileUri.value.size,assignmentUri)
                }
                */
            }
        }
    }

    fun updateFlow() {
        viewModelScope.launch {
            runCatching {
                val assignmentsResponse = assignmentsRepository.getAllAssignments()
                if (assignmentsResponse.isSuccess) {
                    val assignmentsDomain =
                        assignmentsResponse.getOrNull() ?: throw Exception("Bad response")
                    _stateFlow.value = _stateFlow.value.copy(assignments = assignmentsDomain)
                }
            }
        }
    }


}