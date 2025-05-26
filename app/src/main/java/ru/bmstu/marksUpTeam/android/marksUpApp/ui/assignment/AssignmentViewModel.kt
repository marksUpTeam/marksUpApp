package ru.bmstu.marksUpTeam.android.marksUpApp.ui.assignment

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments.AssignmentsRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.FileManager

class AssignmentViewModel(
    private val assignmentsRepository: AssignmentsRepository,
    public val fileManger: FileManager
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<AssignmentState> = MutableStateFlow(
        AssignmentState(
            listOf()
        )
    )

    val stateFlow = _stateFlow.asStateFlow()


    fun pickFile(uri: Uri, assignmentId: Long) {

        viewModelScope.launch {
            runCatching {
                val file = fileManger.getFileForRequest(fileUri = uri)
                if (file != null) {
                    val assignmentsResponse = file.let { assignmentsRepository.attachFile(it) }
                    if (assignmentsResponse.isSuccess) {
                        val updatedAssignments = _stateFlow.value.assignments.map {
                            if (it.id == assignmentId) {
                                it.copy(filesUri = it.filesUri + uri)
                            } else it
                        }
                        _stateFlow.value = _stateFlow.value.copy(assignments = updatedAssignments)
                        Log.d("files", _stateFlow.value.assignments.toString())
                    }
                }
            }
        }
    }


    fun updateFlow() {
        viewModelScope.launch {
            runCatching {
                val assignmentsResponse = assignmentsRepository.getAllAssignments()
                if (assignmentsResponse.isSuccess) {
                    _stateFlow.value.copy(
                        assignments = assignmentsResponse.getOrNull()
                            ?: throw Exception("Bad response")
                    )
                }
            }
        }
    }

    fun changeScreenTo(route: String) {
        _stateFlow.value = _stateFlow.value.copy(route = route)
    }

    fun resetRoute() {
        _stateFlow.value = _stateFlow.value.copy(route = null)
    }
}