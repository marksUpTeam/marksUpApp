package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newAssignment

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain

@Immutable
data class NewAssignmentState(
    val disciplines: List<Discipline>,
    val students: List<Student>,
    val profile: ProfileDomain,
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
