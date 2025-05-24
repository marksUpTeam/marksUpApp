package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newLesson

import androidx.compose.runtime.Immutable
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain


private val currentDateTime =
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

@Immutable
data class NewLessonState(
    val students: List<Student>,
    val disciplines: List<Discipline>,
    val profile: ProfileDomain,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFormValid: Boolean = false,
    val selectedDay: String = "",
    val selectedStudent: Student? = null,
    val selectedDiscipline: Discipline? = null,
    val startTime: LocalTime = currentDateTime.time,
    val endTime: LocalTime = currentDateTime.time,
    val startDate: LocalDate = currentDateTime.date,
    val endDate: LocalDate = currentDateTime.date,
)