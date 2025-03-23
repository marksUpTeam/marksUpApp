package ru.bmstu.marksUpTeam.android.marksUpApp.ui.Schedule

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScheduleViewModel {
    private val _stateFlow: MutableStateFlow<ScheduleState> = MutableStateFlow(baseSchedule)
    val stateFlow = _stateFlow.asStateFlow()
}