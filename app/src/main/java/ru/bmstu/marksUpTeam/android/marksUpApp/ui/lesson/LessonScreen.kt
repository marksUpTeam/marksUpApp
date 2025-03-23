package ru.bmstu.marksUpTeam.android.marksUpApp.ui.lesson


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.DropDownList

@Composable
fun LessonScreen(tint: Color = colorResource(id = R.color.black)) {
    val viewModel = LessonViewModel()
    val state by viewModel.stateFlow.collectAsState()
    val textModifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
    val outLineTextFieldModifier =
        textModifier
            .padding(end = 24.dp)
            .clip(RoundedCornerShape(24.dp))
    val marksList = listOf("", "5", "4", "3", "2", "1")
    val isTeacher = state.profile.teacher != null

    Column {

        var homeworkComment by remember { mutableStateOf(state.lesson.assignmentDue?.description.orEmpty()) }
        var teacherComment by remember { mutableStateOf(state.lesson.teacherComment) }

        Text(
            text = stringResource(R.string.teachClasss),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 60.dp),
            color = tint
        )

        if (isTeacher) {
            Text(stringResource(R.string.pupil), modifier = textModifier, color = tint)
            TextField(
                "${state.lesson.student.person.surname} ${state.lesson.student.person.name}",
                onValueChange = {},
                readOnly = true,
                modifier = outLineTextFieldModifier.fillMaxWidth(),
            )
        } else {
            Text(stringResource(R.string.teacher), modifier = textModifier, color = tint)
            TextField(
                "${state.lesson.teacher.person.surname} ${state.lesson.teacher.person.name}",
                onValueChange = {},
                readOnly = true,
                modifier = outLineTextFieldModifier.fillMaxWidth(),
            )
        }


        Text(stringResource(R.string.subject), modifier = textModifier, color = tint)

        TextField(
            state.lesson.discipline.name,
            onValueChange = {},
            readOnly = true,
            modifier = outLineTextFieldModifier.fillMaxWidth()
        )

        Text(stringResource(R.string.timeAndDate), modifier = textModifier, color = tint)

        Row(modifier = Modifier.fillMaxWidth()) {

            TextField(
                "${formatTime(state.lesson.datetimeStart.time)}-${formatTime(state.lesson.datetimeEnd.time)}",
                onValueChange = {},
                readOnly = true,
                modifier = outLineTextFieldModifier.weight(1f)
            )


            TextField(
                formatDate(state.lesson.datetimeStart.date),
                onValueChange = {},
                readOnly = true,
                modifier = outLineTextFieldModifier.weight(1f)
            )

        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                stringResource(R.string.classMark),
                modifier = textModifier.weight(1f),
                color = tint
            )
            Text(
                stringResource(R.string.homeworkMark),
                modifier = textModifier.weight(1f),
                color = tint
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) {
                DropDownList(
                    currentItem = state.lesson.grade?.toString().orEmpty(),
                    listItems = marksList,
                    modifier = outLineTextFieldModifier,
                    readonly = !isTeacher
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                DropDownList(
                    currentItem = state.lesson.assignmentDue?.grade?.toString().orEmpty(),
                    listItems = marksList,
                    modifier = outLineTextFieldModifier,
                    readonly = !isTeacher
                )
            }
        }

        Text(stringResource(R.string.homework), modifier = textModifier, color = tint)

        TextField(
            value = homeworkComment,
            onValueChange = { homeworkComment = it },
            maxLines = 3,
            readOnly = !isTeacher,
            modifier = outLineTextFieldModifier.fillMaxWidth()
        )

        if (isTeacher) {
            Text(stringResource(R.string.addMaterials), modifier = textModifier, color = tint)

            IconButton(onClick = { }, modifier = textModifier) {
                Icon(
                    painter = painterResource(R.drawable.file_attach),
                    contentDescription = "FileAttach",
                    modifier = Modifier.size(30.dp),
                    tint = colorResource(R.color.light_red)
                )
            }
        }

        Text(stringResource(R.string.teacherComment), modifier = textModifier, color = tint)

        TextField(
            value = teacherComment,
            onValueChange = { teacherComment = it },
            maxLines = 5,
            readOnly = !isTeacher,
            modifier = outLineTextFieldModifier.fillMaxWidth()
        )
    }


}

fun formatTime(time: LocalTime): String {
    return time.hour.toString().padStart(2, '0') + ":" + time.minute.toString().padStart(2, '0')
}


fun formatDate(date: LocalDate): String {
    return date.dayOfMonth.toString().padStart(2, '0') + "." + date.monthNumber.toString()
        .padStart(2, '0') + "." + date.year
}




