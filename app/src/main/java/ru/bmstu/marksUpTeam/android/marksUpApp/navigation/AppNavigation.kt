package ru.bmstu.marksUpTeam.android.marksUpApp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.PersonType
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.baseStudentProfileDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.BaseButton
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.assignment.AssignmentScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.authorization.AuthorizationScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.favourites.FavouritesScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.grade.GradeScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson.AddLessonScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson.LessonScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.mainActivity.MainActivityViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.mainActivity.Route
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.profile.ProfileScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.schedule.ScheduleScreen
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.setValue

@Composable
fun AppNavigation(viewModel: MainActivityViewModel = koinViewModel()) {
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

    Scaffold(
        bottomBar = {
            if (currentRoute != Route.Login.name) {
                Selector(isForTeacher = isTeacher)
            }
        },
        contentWindowInsets = WindowInsets.systemBars
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Schedule.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.Assignment.name) { AssignmentScreen() }
            composable(Route.Login.name) { AuthorizationScreen(navController = navController) }
            composable(Route.Schedule.name) { ScheduleScreen(navController = navController) }
            composable(Route.AddLesson.name) { AddLessonScreen() }
            composable(Route.Lesson.name) { LessonScreen() }
            composable(Route.Favourites.name) { FavouritesScreen() }
            composable(Route.Grade.name) { GradeScreen() }
            composable(Route.Profile.name) { ProfileScreen() }
        }
    }
}

@Composable
fun Selector(
    modifier: Modifier = Modifier,
    viewModel: MainActivityViewModel = koinViewModel(),
    setCurrentScreen: (Int) -> Unit = {},
    backgroundColor: Color = colorResource(id = R.color.white),
    tint: Color = colorResource(id = R.color.grey),
    selectedTint: Color = colorResource(id = R.color.blue),
    isForTeacher: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(
                bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding(),
                start = 8.dp,
                end = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var buttonClicked by rememberSaveable { mutableIntStateOf(1) }

        BaseButton(
            painter = painterResource(R.drawable.calendar),
            onClick = {
                viewModel.changeScreenTo(Route.Schedule.name)
                setCurrentScreen(1)
                buttonClicked = 1
            },
            contentDescription = stringResource(R.string.classes),
            tint = if (buttonClicked == 1) selectedTint else tint,
            isSelected = buttonClicked == 1
        )

        BaseButton(
            painter = painterResource(R.drawable.favorite),
            onClick = {
                viewModel.changeScreenTo(Route.Favourites.name)
                setCurrentScreen(2)
                buttonClicked = 2
            },
            contentDescription = stringResource(R.string.favourites),
            tint = if (buttonClicked == 2) selectedTint else tint,
            isSelected = buttonClicked == 2
        )

        if (isForTeacher) {
            BaseButton(
                painter = painterResource(R.drawable.manager),
                onClick = {
                    viewModel.changeScreenTo(Route.AddLesson.name)
                    setCurrentScreen(3)
                    buttonClicked = 3
                },
                contentDescription = stringResource(R.string.classesManager),
                tint = if (buttonClicked == 3) selectedTint else tint,
                isSelected = buttonClicked == 3
            )
        } else {
            BaseButton(
                painter = painterResource(R.drawable.mark),
                onClick = {
                    viewModel.changeScreenTo(Route.Grade.name)
                    setCurrentScreen(3)
                    buttonClicked = 3
                },
                contentDescription = stringResource(R.string.marks),
                tint = if (buttonClicked == 3) selectedTint else tint,
                isSelected = buttonClicked == 3
            )
        }

        BaseButton(
            painter = painterResource(R.drawable.profile),
            onClick = {
                viewModel.changeScreenTo(Route.Profile.name)
                setCurrentScreen(4)
                buttonClicked = 4
            },
            contentDescription = stringResource(R.string.profile),
            tint = if (buttonClicked == 4) selectedTint else tint,
            isSelected = buttonClicked == 4
        )
    }
}