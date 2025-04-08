package ru.bmstu.marksUpTeam.android.marksUpApp.ui.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseAssignment
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments.AssignmentsRepository

class AssignmentViewModel(private val assignmentsRepository: AssignmentsRepository) : ViewModel() {
    private val _stateFlow: MutableStateFlow<AssignmentState> = MutableStateFlow(
        AssignmentState(
            listOf(baseAssignment)
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

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