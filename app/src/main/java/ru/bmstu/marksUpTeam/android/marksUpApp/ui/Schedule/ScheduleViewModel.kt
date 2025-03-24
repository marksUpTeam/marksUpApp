package ru.bmstu.marksUpTeam.android.marksUpApp.ui.Schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScheduleViewModel : ViewModel() {
    private val _stateFlow: MutableStateFlow<ScheduleState> = MutableStateFlow(baseSchedule)
    val stateFlow = _stateFlow.asStateFlow()

    fun changeScreenTo(route: String) {
        Log.d("viewModel",route)
        _stateFlow.value = _stateFlow.value.copy(route = route)
    }

    fun resetRoute() {
        _stateFlow.value = _stateFlow.value.copy(route = null)
    }
}