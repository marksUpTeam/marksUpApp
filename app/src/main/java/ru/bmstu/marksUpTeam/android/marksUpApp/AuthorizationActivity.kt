package ru.bmstu.marksUpTeam.android.marksUpApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Authorization

class AuthorizationActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Authorization(onAuth = {a, b -> {println(a); println(b)}})
        }
    }
}

