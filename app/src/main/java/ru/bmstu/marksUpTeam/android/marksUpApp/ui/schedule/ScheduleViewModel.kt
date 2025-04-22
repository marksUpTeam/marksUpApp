package ru.bmstu.marksUpTeam.android.marksUpApp.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes.ClassesRepository

class ScheduleViewModel(private val classesRepository: ClassesRepository) : ViewModel() {

    private val _stateFlow: MutableStateFlow<ScheduleState> = MutableStateFlow(baseSchedule)
    val stateFlow = _stateFlow.asStateFlow()

    fun updateFlow() {
        viewModelScope.launch {
            runCatching {
                val classesResponse = classesRepository.getClasses()
                if (classesResponse.isSuccess) {
                    val classesDomain = classesResponse.getOrNull() ?: throw Exception("Bad response")
                    _stateFlow.value = _stateFlow.value.copy(classes = classesDomain)
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