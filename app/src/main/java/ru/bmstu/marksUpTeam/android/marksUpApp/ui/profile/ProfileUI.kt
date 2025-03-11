package ru.bmstu.marksUpTeam.android.marksUpApp.ui.profile

import android.content.Context
import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Person
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseStudentProfile
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.Black
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.Grey
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.LighterBlack
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.Purple500
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.White

enum class ProfileType{
    Teacher, Student, Parent
}



@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
fun ProfileUI(
    modifier: Modifier = Modifier,
    tint: Color = White,
    subTint: Color = Grey,
    backgroundColor: Color = Black,
    onBackgroundColor: Color = LighterBlack,
    mainColor: Color = Purple500,
    profileData: Profile = baseStudentProfile,
    context: Context = LocalContext.current,
){
    val previewHandler = AsyncImagePreviewHandler{
        ColorImage(Color.Red.toArgb())
    }
    var person: Person
    val profileType: ProfileType
    when {
        (profileData.teacher != null) -> {profileType = ProfileType.Teacher; person = profileData.teacher.person}
        (profileData.student != null) -> {profileType = ProfileType.Student; person = profileData.student.person}
        (profileData.parent != null) -> {profileType = ProfileType.Parent; person = profileData.parent.person}
        else -> {Log.println(Log.ERROR, "ProfileUI", "Profile illegal model"); return }
    }

    Column(modifier = modifier.fillMaxSize().background(backgroundColor), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.padding(10.dp).height(50.dp)) {}
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
                AsyncImage(
                    model = person.imgUrl, contentDescription = "", modifier = Modifier.width(128.dp).height(128.dp).shadow(elevation = 50.dp, clip = true, shape = RoundedCornerShape(30.dp), spotColor = mainColor, ambientColor = mainColor)
                )
                Column(modifier = Modifier.width(45.dp)){}
                Column() {
                    Text(
                        text = "${person.surname}\n${person.name}\n${person.patronymic}",
                        color = tint, modifier = Modifier, fontSize = 28.sp, fontWeight = FontWeight.Bold
                    )
                    when (profileType) {
                        ProfileType.Teacher -> {
                            val teacherString = context.getString(R.string.teacher)
                            val disciplineString = profileData.teacher?.disciplines?.joinToString(", ")
                            Text(
                                text = "$teacherString: $disciplineString",
                                color = subTint,
                                fontStyle = FontStyle.Italic,
                            )
                        }
                        ProfileType.Student -> {
                            Text(
                                text = context.getString(R.string.student),
                                color = subTint,
                                fontStyle = FontStyle.Italic,
                            )
                        }
                        ProfileType.Parent -> {
                            Text(
                                text = context.getString(R.string.parent),
                                color = subTint,
                                fontStyle = FontStyle.Italic,
                            )
                        }
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()){

        }

    }
}