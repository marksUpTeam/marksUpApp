package ru.bmstu.marksUpTeam.android.marksUpApp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.assignment.AssignmentViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.authorization.AuthorizationViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.grade.GradeViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson.LessonViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.mainActivity.MainActivityViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.profile.ProfileViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.schedule.ScheduleViewModel

val viewModelModule = module {
    viewModel{
        MainActivityViewModel()
    }
    viewModel{
        AssignmentViewModel(assignmentsRepository = get(), fileManger = get())
    }
    viewModel{
        AuthorizationViewModel(authorizationRepository = get())
    }
    viewModel{
        GradeViewModel()
    }
    viewModel{
        LessonViewModel()
    }
    viewModel {
        ProfileViewModel(profileRepository = get())
    }
    viewModel{
        ScheduleViewModel(classesRepository = get())
    }

}