package ru.bmstu.marksUpTeam.android.marksUpApp.ui.assignment

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Assignment
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseAssignment
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.formatDate

@Composable
fun AssignmentScreen(viewModel: AssignmentViewModel = koinViewModel()) {
    val state by viewModel.stateFlow.collectAsState()

    LaunchedEffect(state) {
        viewModel.updateFlow()

    }

    Text(
        text = stringResource(R.string.homework),
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
        items(state.assignments) { assignment ->
            AssignmentCard(
                assignment
            )
        }
    }
}

@Preview
@Composable
fun AssignmentCard(assignment: Assignment = baseAssignment) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.84f)
            .height(180.dp)
            .padding(bottom = 30.dp)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp)), contentAlignment = Alignment.Center

    ) {

        Column(modifier = Modifier.fillMaxWidth().padding(start = 12.dp)) {
            Text(
                "${assignment.discipline.name}, ${stringResource(R.string.until)} ${
                    formatDate(
                        assignment.deadline
                    )
                } ",
                maxLines = 2,
                fontSize = 24.sp,
                color = Color.Black
            )

            Text(assignment.description, fontSize = 24.sp, color = Color.Black)
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.file_attach),
                    contentDescription = "FileAttach",
                    modifier = Modifier.size(30.dp),
                    tint = colorResource(R.color.light_red)
                )
            }
        }
    }

}
