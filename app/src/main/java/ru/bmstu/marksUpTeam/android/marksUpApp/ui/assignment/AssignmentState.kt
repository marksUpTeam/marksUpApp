package ru.bmstu.marksUpTeam.android.marksUpApp.ui.assignment

import ru.bmstu.marksUpTeam.android.marksUpApp.domain.AssignmentDomain

data class AssignmentState(
    val assignments: List<AssignmentDomain>,
    val route: String? = null
)