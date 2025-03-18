package ru.bmstu.marksUpTeam.android.marksUpApp.ui.grade

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.bmstu.marksUpTeam.android.marksUpApp.data.GradesState
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseGradesState

class GradeViewModel {
    private val _stateFlow: MutableStateFlow<GradesState> = MutableStateFlow(baseGradesState)
    val stateFlow = _stateFlow.asStateFlow()
}