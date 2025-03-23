package ru.bmstu.marksUpTeam.android.marksUpApp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseTeacherProfile
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Schedule.ScheduleScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Selector
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.favourites.FavouritesScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.grade.GradeScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson.AddLessonScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson.LessonScreen

@Composable
fun AppNavigation() {
    val profile = baseTeacherProfile
    val isTeacher = profile.teacher != null
    val navController = rememberNavController()

    Scaffold(bottomBar = { Selector(navController, isForTeacher = isTeacher) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "schedule",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("schedule") { ScheduleScreen(navController) }
            composable("addLesson") { AddLessonScreen() }
            composable("lesson") { LessonScreen() }
            composable("favourites") { FavouritesScreen() }
            composable("grade") { GradeScreen() }
        }
    }
}


