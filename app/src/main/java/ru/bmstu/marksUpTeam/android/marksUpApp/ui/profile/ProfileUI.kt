package ru.bmstu.marksUpTeam.android.marksUpApp.ui.profile

import android.content.Context
import androidx.activity.contextaware.ContextAware
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import ru.bmstu.marksUpTeam.android.marksUpApp.R
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
            is ProfileState.ContentParent -> {ContentParentScreen(onRefresh = onRefresh, state.profile, onCurrentStudentChange = onCurrentStudentChange)}
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
val previewHandler = AsyncImagePreviewHandler{
    ColorImage(Color.Red.toArgb())
}


@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ContentTeacherScreen( // TODO: Fill out composable
    onRefresh: () -> Unit = {},
    teacher: Profile = baseTeacherProfile,
    context: Context = LocalContext.current,
) {
    MarksUpTheme {
        PullToRefreshBox(isRefreshing = false, onRefresh = onRefresh) {
            Column(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    modifier = Modifier.padding(10.dp).height(50.dp),
                ) {}
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
                        AsyncImage(
                            model = teacher.teacher?.person?.imgUrl,
                            contentDescription = "",
                            modifier = Modifier.width(128.dp).height(128.dp).shadow(
                                elevation = 50.dp,
                                clip = true,
                                shape = RoundedCornerShape(30.dp),
                                spotColor = MaterialTheme.colorScheme.primary,
                                ambientColor = MaterialTheme.colorScheme.primary
                            ),
                        )
                        Column(modifier = Modifier.width(45.dp)) {}
                        Column {
                            Text(
                                text = "${teacher.teacher?.person?.surname}\n${teacher.teacher?.person?.name}\n${teacher.teacher?.person?.patronymic}",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = context.getString(R.string.teacher),
                                color = MaterialTheme.colorScheme.tertiary,
                                fontStyle = FontStyle.Italic,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Preview
@Composable
private fun ContentStudentScreen( // TODO: Fill out composable
    onRefresh: () -> Unit = {},
    student: Profile = baseStudentProfile,
    context: Context = LocalContext.current,
){
    MarksUpTheme {
        PullToRefreshBox(isRefreshing = false, onRefresh = onRefresh) {
            Column(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    modifier = Modifier.padding(10.dp).height(50.dp),
                ) {}
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
                        AsyncImage(
                            model = student.student?.person?.imgUrl,
                            contentDescription = "",
                            modifier = Modifier.width(128.dp).height(128.dp).shadow(
                                elevation = 50.dp,
                                clip = true,
                                shape = RoundedCornerShape(30.dp),
                                spotColor = MaterialTheme.colorScheme.primary,
                                ambientColor = MaterialTheme.colorScheme.primary
                            ),
                        )
                        Column(modifier = Modifier.width(45.dp)) {}
                        Column {
                            Text(
                                text = "${student.student?.person?.surname}\n${student.student?.person?.name}\n${student.student?.person?.patronymic}",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = context.getString(R.string.student),
                                color = MaterialTheme.colorScheme.tertiary,
                                fontStyle = FontStyle.Italic,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Preview
@Composable
private fun ContentParentScreen( // TODO: Fill out composable
    onRefresh: () -> Unit = {},
    parent: Profile = baseParentProfile,
    onCurrentStudentChange: (Student) -> Unit = {},
    context: Context = LocalContext.current,
){
    MarksUpTheme {
        PullToRefreshBox(isRefreshing = false, onRefresh = onRefresh) {
            Column(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    modifier = Modifier.padding(10.dp).height(50.dp),
                ) {}
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
                        AsyncImage(
                            model = parent.parent?.person?.imgUrl,
                            contentDescription = "",
                            modifier = Modifier.width(128.dp).height(128.dp).shadow(
                                elevation = 50.dp,
                                clip = true,
                                shape = RoundedCornerShape(30.dp),
                                spotColor = MaterialTheme.colorScheme.primary,
                                ambientColor = MaterialTheme.colorScheme.primary
                            ),
                        )
                        Column(modifier = Modifier.width(45.dp)) {}
                        Column {
                            Text(
                                text = "${parent.parent?.person?.surname}\n${parent.parent?.person?.name}\n${parent.parent?.person?.patronymic}",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = context.getString(R.string.parent),
                                color = MaterialTheme.colorScheme.tertiary,
                                fontStyle = FontStyle.Italic,
                            )
                        }
                    }
                }
            }
        }
    }
}

