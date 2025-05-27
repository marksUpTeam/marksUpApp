package ru.bmstu.marksUpTeam.android.marksUpApp.di

import org.koin.dsl.module
import ru.bmstu.marksUpTeam.android.marksUpApp.data.LessonRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments.AssignmentsMapper
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments.AssignmentsRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization.AuthorizationRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes.ClassesRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.favourites.FavouritesMapper
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.favourites.FavouritesRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileMapper
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileRepository

val dataModule = module {
    single {
        AssignmentsRepository(assignmentsApi = get(), assignmentsMapper = get(), fileManager = get())
    }
    single {
        AuthorizationRepository(authorizationApi = get())
    }
    single{
        ClassesRepository(classesApi = get())
    }
    single {
        FavouritesRepository(favouritesApi = get(), favouritesMapper = get())
    }
    single{
        ProfileRepository(profileApi = get(), profileMapper = get())
    }
    single {
        AssignmentsMapper()
    }
    single {
        FavouritesMapper()
    }
    single {
        ProfileMapper()
    }
    single {
        LessonRepository(lessonApi = get())
    }
}
