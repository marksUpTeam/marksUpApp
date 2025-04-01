package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newLesson

import androidx.compose.runtime.Immutable
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student

@Immutable
data class NewLessonState(val students:List<Student>, val disciplines:List<Discipline>, val profile: Profile, val isLoading: Boolean = false, val error: String? = null)
