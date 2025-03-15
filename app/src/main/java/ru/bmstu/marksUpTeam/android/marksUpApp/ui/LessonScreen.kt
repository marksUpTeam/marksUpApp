package ru.bmstu.marksUpTeam.android.marksUpApp.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseClass
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.MarksUpTheme

@Preview
@Composable
fun LessonScreen() {
    val outLineTextFieldModifier = Modifier.padding(6.dp).clip(RoundedCornerShape(16.dp))
    val marksList = listOf("","5","4","3","2","1")

    MarksUpTheme {
        Box(Modifier.fillMaxSize()){
        Column {

            var homeworkComment by remember { mutableStateOf(baseClass.assignmentDue.description) }
            var teacherComment by remember { mutableStateOf(baseClass.teacherComment) }

            Spacer(modifier = Modifier.height(40.dp))

            Text(text = stringResource(R.string.teachClasss), fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Text(stringResource(R.string.pupil))

            TextField(
                "${baseClass.student.person.surname} ${baseClass.student.person.name}",
                onValueChange = {},
                readOnly = true,
                modifier = outLineTextFieldModifier.fillMaxWidth()
            )


            Text(stringResource(R.string.subject))

            TextField(
                baseClass.discipline.name,
                onValueChange = {},
                readOnly = true,
                modifier = outLineTextFieldModifier.fillMaxWidth()
            )

            Text(stringResource(R.string.timeAndDate))

            Row(modifier = Modifier.fillMaxWidth()) {

                TextField(
                   "${formatTime(baseClass.datetimeStart.time)}-${formatTime(baseClass.datetimeEnd.time)}",
                    onValueChange = {},
                    readOnly = true,
                    modifier = outLineTextFieldModifier.weight(1f)
                )

                Spacer(modifier = Modifier.weight(0.5f))

                TextField(
                   formatDate(baseClass.datetimeStart.date),
                    onValueChange = {}, readOnly = true, modifier = outLineTextFieldModifier.weight(1f)
                )

            }

            Row(modifier = Modifier.fillMaxWidth()){
                Text(stringResource(R.string.classMark), modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.weight(0.5f))
                Text(stringResource(R.string.homeworkMark), modifier = Modifier.weight(1f))
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Box( modifier = Modifier.weight(1f)) {
                    DropDownList(baseClass.grade?.toString().orEmpty(), marksList, textFieldModifier = outLineTextFieldModifier)
                }

                Spacer(modifier = Modifier.weight(0.5f))

                Box( modifier = Modifier.weight(1f)) {
                    DropDownList(baseClass.assignmentDue.grade?.toString().orEmpty(), marksList, textFieldModifier = outLineTextFieldModifier)
                }
            }

            Text(stringResource(R.string.homework))

            TextField(
                value = homeworkComment,
                onValueChange = { homeworkComment = it },
                modifier = outLineTextFieldModifier.fillMaxWidth()
            )
            Text(stringResource(R.string.addMaterials))

            BaseButton()

            Text(stringResource(R.string.teacherComment))

            TextField(
                value = teacherComment,
                onValueChange = { teacherComment = it },
                modifier = outLineTextFieldModifier.fillMaxWidth()
            )
        }

        Column(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)){
            Selector()
        }
    }
}
}

fun formatTime(time: LocalTime):String{
    return time.hour.toString().padStart(2, '0') + ":" + time.minute.toString().padStart(2, '0')
}



fun formatDate(date: LocalDate):String{
    return date.dayOfMonth.toString().padStart(2, '0') + "." + date.monthNumber.toString().padStart(2, '0') + "." + date.year
}




