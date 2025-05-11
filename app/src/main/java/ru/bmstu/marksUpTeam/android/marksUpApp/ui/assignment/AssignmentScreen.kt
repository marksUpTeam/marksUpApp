package ru.bmstu.marksUpTeam.android.marksUpApp.ui.assignment

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.AssignmentDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.formatDate

@Composable
fun AssignmentScreen(viewModel: AssignmentViewModel = koinViewModel()) {
    val state by viewModel.stateFlow.collectAsState()
    val context = LocalContext.current

    var assignmentId by remember { mutableStateOf<Long>(0) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.pickFile(it, assignmentId)
            }
        }
    )

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
        Log.d("filesColumn", state.assignments.toString())
        items(state.assignments) { assignment ->
            AssignmentCard(
                assignment, onAttachRequest = { id ->
                    assignmentId = id
                    launcher.launch(arrayOf("*/*"))

                }, viewModel, context
            )
        }
    }
}

@Composable
fun AssignmentCard(
    assignment: AssignmentDomain,
    onAttachRequest: (Long) -> Unit,
    viewModel: AssignmentViewModel,
    context: Context

) {

    Box(
        modifier = Modifier
            .fillMaxWidth(0.84f)
            .padding(bottom = 30.dp)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp)), contentAlignment = Alignment.Center

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 30.dp, bottom = 30.dp)
        ) {
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
            IconButton(onClick = {
                onAttachRequest(assignment.id)
            }) {
                Icon(
                    painter = painterResource(R.drawable.file_attach),
                    contentDescription = "FileAttach",
                    modifier = Modifier.size(30.dp),
                    tint = colorResource(R.color.light_red)
                )
            }
            LazyRow {
                items(assignment.filesUri) { uri ->
                    if (uri != null) {
                        val fileType = context.contentResolver.getType(uri)
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(120.dp)
                                .clickable {
                                    viewModel.fileManger.openFile(uri = uri)
                                })
                        {
                            Column {

                                if (fileType!!.startsWith("image")) {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = uri),
                                        contentDescription = "file",
                                        modifier = Modifier.size(100.dp),
                                        contentScale = ContentScale.Crop,

                                        )
                                } else {
                                    Icon(
                                        ImageVector.vectorResource(R.drawable.document),
                                        contentDescription = "file",
                                        modifier = Modifier.size(100.dp),
                                    )
                                }
                                Text(
                                    viewModel.fileManger.getFileName(uri).orEmpty(),
                                    modifier = Modifier.fillMaxSize(),
                                    textAlign = TextAlign.Center,
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )

                            }

                        }
                    }
                }
            }
        }
    }
}




