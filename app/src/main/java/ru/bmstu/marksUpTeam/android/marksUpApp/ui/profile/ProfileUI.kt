package ru.bmstu.marksUpTeam.android.marksUpApp.ui.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseParentProfile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseStudentProfile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseTeacherProfile
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.ErrorScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.LoadingScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.MarksUpTheme

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {
    val state = viewModel.stateFlow.collectAsState()
    ProfileContent(state.value, onRefresh = {viewModel.updateFlow()}, onCurrentStudentChange = {viewModel.pushCurrentStudentChange(it)})
}

@Composable
private fun ProfileContent(
    state: ProfileState,
    onRefresh: () -> Unit,
    onCurrentStudentChange: (Student) -> Unit
) {
    MarksUpTheme {
        when (state) {
            is ProfileState.Loading -> {
                LoadingScreen(modifier = Modifier.fillMaxSize(), backgroundColor = MaterialTheme.colorScheme.background)
            }
            is ProfileState.Error -> {
                ErrorScreen(onRefresh = onRefresh, modifier = Modifier.fillMaxSize(), backgroundColor = MaterialTheme.colorScheme.background, errorMessage = state.errorMessage)
            }
            is ProfileState.ContentStudent -> {ContentStudentScreen(onRefresh = onRefresh, state.profile)}
            is ProfileState.ContentTeacher -> {ContentTeacherScreen(onRefresh = onRefresh, state.profile)}
            is ProfileState.ContentParent -> {ContentParentScreen(onRefresh = onRefresh, state.profile)}
        }
    }
}

@Preview
@Composable
private fun ContentTeacherScreen(
    onRefresh: () -> Unit = {},
    teacher: Profile = baseTeacherProfile,
){

}

@Preview
@Composable
private fun ContentStudentScreen(
    onRefresh: () -> Unit = {},
    student: Profile = baseStudentProfile,
){

}

@Preview
@Composable
private fun ContentParentScreen(
    onRefresh: () -> Unit = {},
    parent: Profile = baseParentProfile,
    onCurrentStudentChange: (Student) -> Unit = {},
){

}

