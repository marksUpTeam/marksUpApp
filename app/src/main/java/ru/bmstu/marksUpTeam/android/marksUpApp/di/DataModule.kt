package ru.bmstu.marksUpTeam.android.marksUpApp.di

import org.koin.dsl.module
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments.AssignmentsRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization.AuthorizationRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes.ClassesRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.favourites.FavouritesRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getJwt

val dataModule = module {
    single {
        AssignmentsRepository(assignmentsApi = get(),jwtUnformatted =  getJwt(context = get()).orEmpty())
    }
    single {
        AuthorizationRepository(authorizationApi = get())
    }
    single{
        ClassesRepository(classesApi = get(),jwtUnformatted =  getJwt(context = get()).orEmpty())
    }
    single {
        FavouritesRepository(favouritesApi = get(), jwtUnformatted = getJwt(context = get()).orEmpty())
    }
    single{
        ProfileRepository(profileApi = get(), jwtUnformatted = getJwt(context = get()).orEmpty())
    }
}
