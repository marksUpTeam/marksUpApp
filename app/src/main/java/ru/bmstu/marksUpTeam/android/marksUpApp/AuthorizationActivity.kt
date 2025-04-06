package ru.bmstu.marksUpTeam.android.marksUpApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.authorization.AuthorizationScreen

class AuthorizationActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(BuildConfig.API_URL)
        setContent {
            AuthorizationScreen(context = this)
        }
    }
}



