package ru.bmstu.marksUpTeam.android.marksUpApp.di

import org.koin.dsl.module
import ru.bmstu.marksUpTeam.android.marksUpApp.BuildConfig
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments.AssignmentsRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization.AuthorizationRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes.ClassesRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.favourites.FavouritesRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicRetrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getJwt

val dataModule = module {
    single {
        AssignmentsRepository(retrofit = get(),jwtUnformatted =  getJwt(context = get()).orEmpty())
    }
    single {
        AuthorizationRepository(retrofit = get())
    }
    single{
        ClassesRepository(retrofit = get(),jwtUnformatted =  getJwt(context = get()).orEmpty())
    }
    single {
        FavouritesRepository(retrofit = get(), jwtUnformatted = getJwt(context = get()).orEmpty())
    }
    single{
        ProfileRepository(retrofit = get(), jwtUnformatted = getJwt(context = get()).orEmpty())
    }

}

val networkModule = module{
    single {
        getBasicRetrofit(api =  BuildConfig.API_URL,context = get())
    }
}