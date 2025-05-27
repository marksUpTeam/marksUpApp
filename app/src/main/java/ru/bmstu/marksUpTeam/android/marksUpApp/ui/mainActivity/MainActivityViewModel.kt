package ru.bmstu.marksUpTeam.android.marksUpApp.ui.mainActivity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivityViewModel: ViewModel() {
    private val _stateFlow = MutableStateFlow(MainState(route = null))
    val stateFlow = _stateFlow.asStateFlow()

    fun changeScreenTo(route: String) {
        _stateFlow.value = _stateFlow.value.copy(route = route)
    }

    fun resetRoute(){
        _stateFlow.value = _stateFlow.value.copy(route = null)
    }
}



enum class Route {
    Login, Schedule, AddLesson, Lesson, Favourites, Grade, Profile, Assignment, NewLesson, NewAssignment
}