package ru.bmstu.marksUpTeam.android.marksUpApp.ui.grade

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GradeViewModel {
    private val _stateFlow: MutableStateFlow<GradesState> = MutableStateFlow(baseGradesState)
    val stateFlow = _stateFlow.asStateFlow()
}