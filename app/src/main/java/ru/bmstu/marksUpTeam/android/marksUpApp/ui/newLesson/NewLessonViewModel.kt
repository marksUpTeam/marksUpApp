package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newLesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseTeacherProfile

class NewLessonViewModel() : ViewModel() {
    private val _stateFlow: MutableStateFlow<NewLessonState> = MutableStateFlow(
        NewLessonState(
            emptyList(),
            emptyList(),
            profile = baseTeacherProfile,
            isLoading = false,
            error = null
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

/*    fun loadInitialData() = viewModelScope.launch {
        _stateFlow.update { it.copy(isLoading = true) }

        try {
            val studentsList = repository.getStudents()
            val disciplineList = repository.getDisciplines()
            _stateFlow.update {
                it.copy(
                    studentsList = studentsList,
                    disciplineList = disciplineList,
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            _stateFlow.update {
                it.copy(
                    error = e.message ?: "Unknown Error",
                    isLoading = false
                )
            }
        }
    }


     init {
        loadInitialData()
        viewModelScope.launch {
            val studentsList = repository.getStudents()
            _stateFlow.value = _stateFlow.value.copy(students = studentsList)
        }
        viewModelScope.launch {
            val disciplineList = repository.getDisciplines()
            _stateFlow.value = _stateFlow.value.copy(disciplines = disciplineList)
        }
        viewModelScope.launch {
            val profile = repository.getProfile()
            _stateFlow.value = _stateFlow.value.copy(profile = profile)
        }
    } */
}