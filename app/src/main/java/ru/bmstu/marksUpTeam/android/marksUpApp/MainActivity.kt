package ru.bmstu.marksUpTeam.android.marksUpApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseClass
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseTeacherProfile
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.LessonScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                LessonScreen(baseClass = baseClass, baseProfile = baseTeacherProfile)
            }
        }
    }
