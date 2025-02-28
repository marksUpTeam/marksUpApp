package ru.bmstu.marksUpTeam.android.marksUpApp

import android.app.Application
import android.util.Log
import com.vk.id.VKID

class MarksUpApp : Application() {
    override fun onCreate() {
        super.onCreate()
        VKID.init(this)
        VKID.logsEnabled = true
        Log.println(Log.INFO, "vkId", "Started VKID")
    }
}