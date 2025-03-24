package ru.bmstu.marksUpTeam.android.marksUpApp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.bmstu.marksUpTeam.android.marksUpApp.Route
import ru.bmstu.marksUpTeam.android.marksUpApp.data.domain.PersonType
import ru.bmstu.marksUpTeam.android.marksUpApp.data.domain.baseStudentProfileDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Authorization
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Schedule.ScheduleScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Selector
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.favourites.FavouritesScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.grade.GradeScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson.AddLessonScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson.LessonScreen

@Composable
fun AppNavigation() {
    val profile = baseStudentProfileDomain
    val isTeacher = profile.personType is PersonType.TeacherType
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    //val profileViewModel = ProfileViewModel(api = BuildConfig.API_URL,jwt = getJwt(LocalContext.current).orEmpty(), context = LocalContext.current)
    Scaffold(bottomBar = {
        if (currentRoute != "login") {
            Selector(navController, isForTeacher = isTeacher)
        }
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "schedule",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.Login.name) { Authorization() }
            composable(Route.Schedule.name) { ScheduleScreen(navController) }
            composable(Route.AddLesson.name) { AddLessonScreen() }
            composable(Route.Lesson.name) { LessonScreen() }
            composable(Route.Favourites.name) { FavouritesScreen() }
            composable(Route.Grade.name) { GradeScreen() }
            //composable(Route.Profile.name) { ProfileScreen(viewModel =  profileViewModel)}
        }
    }
}




