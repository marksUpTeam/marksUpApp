package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newAssignment

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student

@Immutable
data class NewAssignmentState(
    val disciplines: List<Discipline>,
    val students: List<Student>,
    val profile: Profile,
    val selectedStudent: Student? = null,
    val selectedDiscipline: Discipline? = null,
    val dueDate: LocalDate,
    val dueTime: LocalTime,
    val title: String = "",
    val description: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
)

sealed interface NewAssignmentEvent {
    data class DisciplineSelected(val discipline: Discipline) : NewAssignmentEvent
    data class StudentSelected(val student: Student) : NewAssignmentEvent
    data class DateSelected(val date: LocalDate) : NewAssignmentEvent
    data class TimeSelected(val time: LocalTime) : NewAssignmentEvent
    data class TitleChanged(val title: String) : NewAssignmentEvent
    data class DescriptionChanged(val description: String) : NewAssignmentEvent
    object Submit : NewAssignmentEvent
}