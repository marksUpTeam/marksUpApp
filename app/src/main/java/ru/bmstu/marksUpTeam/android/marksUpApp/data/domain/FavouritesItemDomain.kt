package ru.bmstu.marksUpTeam.android.marksUpApp.data.domain

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class

sealed class FavouritesItemContent{
    data class ClassFavourite(val clazz: Class): FavouritesItemContent()
    data class AssignmentFavourite(val assignment: Assignment): FavouritesItemContent()
}



data class FavouritesItemDomain(
    val id: Long,
    val profile: ProfileDomain,
    val favouritesItemContent: FavouritesItemContent
)
