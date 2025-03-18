package ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.bmstu.marksUpTeam.android.marksUpApp.data.LessonState
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseLesson

class LessonViewModel {
    private val _stateFlow: MutableStateFlow<LessonState> = MutableStateFlow(baseLesson)
    val stateFlow = _stateFlow.asStateFlow()
}