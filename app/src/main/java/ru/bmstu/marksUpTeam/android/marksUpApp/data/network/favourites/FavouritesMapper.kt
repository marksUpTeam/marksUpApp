package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.favourites

import ru.bmstu.marksUpTeam.android.marksUpApp.data.FavouritesItem
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.FavouritesItemContent
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.FavouritesItemDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileMapper

class FavouritesMapper {
    fun map(favouritesItem: FavouritesItem): FavouritesItemDomain {
        return FavouritesItemDomain(
            id = favouritesItem.id,
            profile = ProfileMapper().map(favouritesItem.profile),
            favouritesItemContent = when {
                favouritesItem.classObj != null -> FavouritesItemContent.ClassFavourite(favouritesItem.classObj)
                favouritesItem.assignment != null -> FavouritesItemContent.AssignmentFavourite(favouritesItem.assignment)
                else -> throw Exception("Cannot map to FavouritesItemDomain")
            }
        )
    }

    fun mapList(favouritesItems: List<FavouritesItem>): List<FavouritesItemDomain> {
        return favouritesItems.map { map(it) }
    }

    fun toDto(favouritesItemDomain: FavouritesItemDomain): FavouritesItem {
        return FavouritesItem(
            id = favouritesItemDomain.id,
            profile = ProfileMapper().toDto(favouritesItemDomain.profile),
            assignment = if (favouritesItemDomain.favouritesItemContent is FavouritesItemContent.AssignmentFavourite) favouritesItemDomain.favouritesItemContent.assignment else null,
            classObj = if (favouritesItemDomain.favouritesItemContent is FavouritesItemContent.ClassFavourite) favouritesItemDomain.favouritesItemContent.clazz else null,
        )
    }
}