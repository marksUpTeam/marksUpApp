package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newLesson

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student

sealed interface NewLessonEvent {
    data class StartDatesChanged(val startDate: LocalDate) : NewLessonEvent
    data class EndDatesChanged(val endDate: LocalDate) : NewLessonEvent
    data class StartTimeChanged(val start: LocalTime) : NewLessonEvent
    data class EndTimeChanged(val end: LocalTime) : NewLessonEvent
    data class StudentSelected(val student: Student) : NewLessonEvent
    data class DisciplineSelected(val discipline: Discipline) : NewLessonEvent
    data class DayChanged(val day: String) : NewLessonEvent
    object Submit : NewLessonEvent
}
