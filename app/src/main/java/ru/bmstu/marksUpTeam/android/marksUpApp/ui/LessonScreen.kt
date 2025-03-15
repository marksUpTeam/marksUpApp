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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.MarksUpTheme


@Composable
fun LessonScreen(baseClass:Class) {
    val outLineTextFieldModifier = Modifier.padding(6.dp).clip(RoundedCornerShape(16.dp))
    val marksList = listOf("5","4","3","2","1")

    MarksUpTheme {
        Box(Modifier.fillMaxSize()){
        Column {

            var homework by remember { mutableStateOf("") }
            var comment by remember { mutableStateOf("") }

            Spacer(modifier = Modifier.height(40.dp))

            Text(text = stringResource(R.string.teachClasss), fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Text(stringResource(R.string.pupil))

            TextField(
                baseClass.student.person.surname + " " + baseClass.student.person.name, onValueChange = {}, readOnly = true, modifier = outLineTextFieldModifier.fillMaxWidth()
            )


            Text(stringResource(R.string.subject))

            TextField(
                baseClass.discipline.name,onValueChange = {}, readOnly = true, modifier = outLineTextFieldModifier.fillMaxWidth()
            )

            Text(stringResource(R.string.timeAndDate))

            Row(modifier = Modifier.fillMaxWidth()) {

                TextField(
                   baseClass.datetimeStart.time.toString(),onValueChange = {}, readOnly = true, modifier = outLineTextFieldModifier.weight(1f)
                )

                Spacer(modifier = Modifier.weight(0.5f))

                TextField(
                    baseClass.datetimeStart.date.toString(),
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
                    DropDownList("", marksList, textFieldModifier = outLineTextFieldModifier)
                }

                Spacer(modifier = Modifier.weight(0.5f))

                Box( modifier = Modifier.weight(1f)) {
                    DropDownList("", marksList, textFieldModifier = outLineTextFieldModifier)
                }
            }

            Text(stringResource(R.string.homework))

            TextField(
                value = homework,
                onValueChange = { homework = it },
                modifier = outLineTextFieldModifier.fillMaxWidth()
            )
            Text(stringResource(R.string.addMaterials))

            BaseButton()

            Text(stringResource(R.string.teacherComment))

            TextField(
                value = comment,
                onValueChange = { comment = it },
                modifier = outLineTextFieldModifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth().align(Alignment.BottomCenter)
        ){
            Selector()
        }
    }
}
}



