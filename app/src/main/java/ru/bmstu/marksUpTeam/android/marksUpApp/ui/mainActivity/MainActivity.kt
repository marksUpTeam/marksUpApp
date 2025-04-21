package ru.bmstu.marksUpTeam.android.marksUpApp.ui.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.bmstu.marksUpTeam.android.marksUpApp.navigation.AppNavigation
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.newLesson.NewLessonScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.MarksUpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarksUpTheme {
                    AppNavigation()
                }
        }
    }
}
