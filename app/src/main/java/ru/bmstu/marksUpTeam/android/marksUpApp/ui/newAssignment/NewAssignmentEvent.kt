package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newAssignment

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student

sealed interface NewAssignmentEvent {
    data class DisciplineSelected(val discipline: Discipline) : NewAssignmentEvent
    data class StudentSelected(val student: Student) : NewAssignmentEvent
    data class DateSelected(val date: LocalDate) : NewAssignmentEvent
    data class TimeSelected(val time: LocalTime) : NewAssignmentEvent
    data class TitleChanged(val title: String) : NewAssignmentEvent
    data class DescriptionChanged(val description: String) : NewAssignmentEvent
    object Submit : NewAssignmentEvent
}