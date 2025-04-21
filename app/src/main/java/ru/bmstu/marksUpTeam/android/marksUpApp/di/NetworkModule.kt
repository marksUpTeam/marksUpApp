package ru.bmstu.marksUpTeam.android.marksUpApp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.BuildConfig
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments.AssignmentsApi
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization.AuthorizationApi
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes.ClassesApi
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.favourites.FavouritesApi
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileApi
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getBasicInterceptedRetrofit
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getJwt

val networkModule = module{
    single {
        getBasicInterceptedRetrofit(api =  BuildConfig.API_URL,context = get(), jwtUnformatted = getJwt(context = get()).orEmpty())
    }
    single{
        get<Retrofit>().create(AssignmentsApi::class.java)
    }
    single{
        get<Retrofit>().create(AuthorizationApi::class.java)
    }
    single{
        get<Retrofit>().create(ClassesApi::class.java)
    }
    single{
        get<Retrofit>().create(FavouritesApi::class.java)
    }
    single{
        get<Retrofit>().create(ProfileApi::class.java)
    }
    single<Application> { androidContext().applicationContext as Application}
}