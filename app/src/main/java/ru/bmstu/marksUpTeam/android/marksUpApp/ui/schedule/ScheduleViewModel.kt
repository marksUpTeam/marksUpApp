package ru.bmstu.marksUpTeam.android.marksUpApp.ui.schedule

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes.ClassesRepository

class ScheduleViewModel(private val classesRepository: ClassesRepository) : ViewModel() {

    private val _stateFlow: MutableStateFlow<ScheduleState> = MutableStateFlow(baseSchedule)
    val stateFlow = _stateFlow.asStateFlow()
    fun changeScreenTo(route: String) {
        _stateFlow.value = _stateFlow.value.copy(route = route)
    }

    fun resetRoute() {
        _stateFlow.value = _stateFlow.value.copy(route = null)
    }
}