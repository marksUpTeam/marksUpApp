package ru.bmstu.marksUpTeam.android.marksUpApp.ui.grade

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GradeViewModel: ViewModel() {
    private val _stateFlow: MutableStateFlow<GradesState> = MutableStateFlow(baseGradesState)
    val stateFlow = _stateFlow.asStateFlow()
}