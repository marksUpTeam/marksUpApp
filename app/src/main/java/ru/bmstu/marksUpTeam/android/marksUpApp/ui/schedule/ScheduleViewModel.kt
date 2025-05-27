package ru.bmstu.marksUpTeam.android.marksUpApp.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class as LessonClass
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseTeacherProfile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes.ClassesRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.baseTeacherProfileDomain

data class ScheduleState(
    val profile: ProfileDomain,
    val classes: List<LessonClass>,
    val route: String? = null
)

val baseSchedule = ScheduleState(
    profile = baseTeacherProfileDomain,
    classes = emptyList()
)

class ScheduleViewModel(private val classesRepository: ClassesRepository) : ViewModel() {

    private val _stateFlow: MutableStateFlow<ScheduleState> = MutableStateFlow(baseSchedule)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        getClasses()
    }

    private fun getClasses() {
        viewModelScope.launch {
            runCatching {
                classesRepository.getClasses()
            }.onSuccess { classes ->
                _stateFlow.value = _stateFlow.value.copy(classes = classes.getOrNull() ?: emptyList())
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