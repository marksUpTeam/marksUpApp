package ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LessonViewModel {
    private val _stateFlow: MutableStateFlow<LessonState> = MutableStateFlow(baseLesson)
    val stateFlow = _stateFlow.asStateFlow()
}