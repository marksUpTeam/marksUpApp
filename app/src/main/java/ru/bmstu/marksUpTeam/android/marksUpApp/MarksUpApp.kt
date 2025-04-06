package ru.bmstu.marksUpTeam.android.marksUpApp

import android.app.Application
import android.util.Log
import com.vk.id.VKID
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.bmstu.marksUpTeam.android.marksUpApp.di.dataModule
import ru.bmstu.marksUpTeam.android.marksUpApp.di.networkModule
import ru.bmstu.marksUpTeam.android.marksUpApp.di.viewModelModule

class MarksUpApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@MarksUpApp)
            modules(listOf(viewModelModule, dataModule, networkModule))
        }

        VKID.init(this)
        VKID.logsEnabled = true
        Log.println(Log.INFO, "vkId", "Started VKID")
    }
}
