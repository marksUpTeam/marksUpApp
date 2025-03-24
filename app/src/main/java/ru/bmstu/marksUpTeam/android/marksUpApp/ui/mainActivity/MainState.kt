package ru.bmstu.marksUpTeam.android.marksUpApp.ui.mainActivity

import kotlinx.serialization.Serializable

@Serializable
data class MainState(
    val route: String? = null
)