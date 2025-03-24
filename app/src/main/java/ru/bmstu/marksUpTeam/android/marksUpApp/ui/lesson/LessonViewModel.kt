package ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LessonViewModel: ViewModel() {
    private val _stateFlow: MutableStateFlow<LessonState> = MutableStateFlow(baseLesson)
    val stateFlow = _stateFlow.asStateFlow()
}