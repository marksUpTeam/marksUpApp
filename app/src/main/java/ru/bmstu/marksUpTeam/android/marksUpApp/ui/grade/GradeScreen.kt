package ru.bmstu.marksUpTeam.android.marksUpApp.ui.grade

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.data.DisciplineGrade

@Composable
fun GradeScreen(viewModel: GradeViewModel = koinViewModel()) {
    val state by viewModel.stateFlow.collectAsState()

    Text(
        text = stringResource(R.string.progress),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 50.dp),
        color = colorResource(id = R.color.black)
    )

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
    ) {
        items(state.student.disciplineGrades) { disciplineGrade ->
            SubjectGradeCard(
                disciplineGrade
            )
        }
    }
}


@Composable
fun SubjectGradeCard(
    disciplineGrade: DisciplineGrade,
    tint: Color = colorResource(id = R.color.black)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.84f)
            .height(140.dp)
            .padding(bottom = 30.dp)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(18.dp)), contentAlignment = Alignment.Center

    ) {
        Text(
            "${disciplineGrade.discipline.name}, ${stringResource(R.string.meanGrade)} ${disciplineGrade.grade}",
            maxLines = 2,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = tint
        )
    }
}