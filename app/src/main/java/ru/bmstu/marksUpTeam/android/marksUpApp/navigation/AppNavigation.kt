package ru.bmstu.marksUpTeam.android.marksUpApp.navigation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.PersonType
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.baseStudentProfileDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Authorization
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.BaseButton
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Schedule.ScheduleScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Schedule.ScheduleViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.favourites.FavouritesScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.grade.GradeScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson.AddLessonScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson.LessonScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.mainActivity.MainActivityViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.mainActivity.Route

@Composable
fun AppNavigation(viewModel: MainActivityViewModel) {
    val state by viewModel.stateFlow.collectAsState()
    val profile = baseStudentProfileDomain
    val isTeacher = profile.personType is PersonType.TeacherType
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    LaunchedEffect(state) {
        state.route?.let { route ->
            navController.navigate(route)
            viewModel.resetRoute()
        }
    }
    //val profileViewModel = ProfileViewModel(api = BuildConfig.API_URL,jwt = getJwt(LocalContext.current).orEmpty(), context = LocalContext.current)
    Scaffold(bottomBar = {
        if (currentRoute != "login") {
            Selector(viewModel = viewModel, isForTeacher = isTeacher)
        }
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "schedule",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.Login.name) { Authorization() }
            composable(Route.Schedule.name) { ScheduleScreen(viewModel = ScheduleViewModel(), navController = navController) }
            composable(Route.AddLesson.name) { AddLessonScreen() }
            composable(Route.Lesson.name) { LessonScreen() }
            composable(Route.Favourites.name) { FavouritesScreen() }
            composable(Route.Grade.name) { GradeScreen() }
            //composable(Route.Profile.name) { ProfileScreen(viewModel =  profileViewModel)}
        }
    }
}

@Composable
fun Selector(
    viewModel: MainActivityViewModel,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    setCurrentScreen: (Int) -> Unit = {},
    backgroundColor: Color = colorResource(id = R.color.black),
    tint: Color = colorResource(id = R.color.white),
    selectedTint: Color = colorResource(id = R.color.purple_500),
    isForTeacher: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var buttonClicked by rememberSaveable { mutableIntStateOf(1) }
        BaseButton(
            painter = painterResource(R.drawable.timetable),
            onClick = {
                viewModel.changeScreenTo(Route.Schedule.name)
                setCurrentScreen(1)
                buttonClicked = 1
            },
            contentDescription = context.getString(R.string.classes),
            tint = tint,
            selectedTint = selectedTint,
            isSelected = buttonClicked == 1
        )
        BaseButton(
            onClick = {
                viewModel.changeScreenTo(Route.Favourites.name)
                setCurrentScreen(2)
                buttonClicked = 2
            },
            contentDescription = context.getString(R.string.favourites),
            tint = tint,
            selectedTint = selectedTint,
            isSelected = buttonClicked == 2
        )
        if (isForTeacher) {
            BaseButton(
                painter = painterResource(R.drawable.manager),
                onClick = {
                    setCurrentScreen(3)
                    buttonClicked = 3
                },
                contentDescription = context.getString(R.string.classesManager),
                tint = tint,
                selectedTint = selectedTint,
                isSelected = buttonClicked == 3
            )
        } else {
            BaseButton(
                painter = painterResource(R.drawable.five),
                onClick = {
                    viewModel.changeScreenTo(Route.Grade.name)
                    setCurrentScreen(3)
                    buttonClicked = 3
                },
                contentDescription = context.getString(R.string.marks),
                tint = tint,
                selectedTint = selectedTint,
                isSelected = buttonClicked == 3
            )
        }
        BaseButton(
            painter = painterResource(R.drawable.profile),
            onClick = {
                //viewModel.changeScreenTo(Route.Profile.name)
                setCurrentScreen(4)
                buttonClicked = 4
            },
            contentDescription = context.getString(R.string.profile),
            tint = tint,
            selectedTint = selectedTint,
            isSelected = buttonClicked == 4
        )

    }
}



