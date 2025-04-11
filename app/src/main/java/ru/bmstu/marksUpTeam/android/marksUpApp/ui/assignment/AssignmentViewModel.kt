package ru.bmstu.marksUpTeam.android.marksUpApp.ui.assignment

import android.content.ContentResolver
import android.net.Uri
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
    private val _fileUri = MutableStateFlow<List<Uri>>(emptyList())
    val stateFlow = _stateFlow.asStateFlow()
    val fileUri = _fileUri.asStateFlow()

    fun pickFile(uri: Uri, contentResolver: ContentResolver){
        _fileUri.value += uri
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