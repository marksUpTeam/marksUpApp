package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newLesson

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student

@Immutable
data class NewLessonState(
    val students: List<Student>,
    val disciplines: List<Discipline>,
    val profile: Profile,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFormValid: Boolean = false,
    val selectedDay: String,
    val selectedStudent: Student? = null,
    val selectedDiscipline: Discipline? = null,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val startDate: LocalDate,
    val endDate: LocalDate,
)

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
