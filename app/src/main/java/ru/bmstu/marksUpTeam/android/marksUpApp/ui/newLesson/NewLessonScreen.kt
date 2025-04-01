package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newLesson

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.DropDownList
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NewLessonScreen(viewModel: NewLessonViewModel = viewModel()) {
    val state by viewModel.stateFlow.collectAsState()
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()
    val isTeacher = state.profile.teacher != null
    val durationList = listOf("за 10 минут", "за 30 минут", "за 60 минут")
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    var stateFirst = rememberTimePickerState(is24Hour = true)
    var stateSecond = rememberTimePickerState(is24Hour = true)
    val formatter = remember { SimpleDateFormat("HH:mm", java.util.Locale.getDefault()) }
    var startTime by remember { mutableStateOf(formatter.format(mCalendar.time)) }
    var endTime by remember { mutableStateOf(formatter.format(mCalendar.time)) }


    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val startDate = remember { mutableStateOf("") }
    val endDate = remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            startDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )

    val _mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            endDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )

    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                /* .align(Alignment.Center) */
                .size(64.dp)
        )
    }

    if (!state.isLoading) {

        Box(Modifier.fillMaxSize()) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(R.string.newLesson),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(R.string.chooseDay),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                }

                DaysOfWeekGrid(
                    selectedDay = startDate.value,
                    onDaySelected = { startDate.value = it })

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(R.string.choosePeriod),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(all = 4.dp)
                ) {

                    Text(stringResource(R.string.startDay), modifier = Modifier.padding(all = 2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Button(
                            onClick = { mDatePickerDialog.show() }, Modifier
                                .padding(2.dp),
                            border = BorderStroke(
                                1.dp, Color.Blue
                            ),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                        )
                        {
                            Text(
                                text = stringResource(R.string.chooseStartDate),
                                color = Color.White
                            )
                        }

                        TextField(
                            value = startDate.value, onValueChange = {}, modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .padding(2.dp)
                                .align(Alignment.CenterVertically),
                            enabled = false
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    }
                    Spacer(modifier = Modifier.padding(all = 4.dp))

                    Text(stringResource(R.string.endDay), modifier = Modifier.padding(all = 2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Button(
                            onClick = { _mDatePickerDialog.show() },
                            Modifier
                                .padding(2.dp),
                            border = BorderStroke(
                                1.dp, Color.Blue
                            ),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                        )
                        {
                            Text(
                                text = stringResource(R.string.chooseEndDate),
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                        TextField(
                            value = endDate.value, onValueChange = {}, modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .padding(2.dp)
                                .align(Alignment.CenterVertically),
                            enabled = false
                        )


                    }
                    Spacer(modifier = Modifier.padding(all = 4.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(R.string.chooseTime),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Button(
                        onClick = { showStartTimePicker = true }, Modifier
                            .padding(2.dp),
                        border = BorderStroke(
                            1.dp, Color.Blue
                        ),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                    )
                    {
                        Text(
                            text = startTime,
                            color = Color.White
                        )
                    }


                    Text(modifier = Modifier, text = "-")

                    Button(
                        onClick = { showEndTimePicker = true }, Modifier
                            .padding(2.dp),
                        border = BorderStroke(
                            1.dp, Color.Blue
                        ),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                    )
                    {
                        Text(
                            text = endTime,
                            color = Color.White
                        )
                    }


                    if (showStartTimePicker) {

                        TimePickerDialog(
                            onCancel = { showStartTimePicker = false },
                            onConfirm = {
                                mCalendar.set(Calendar.HOUR_OF_DAY, stateFirst.hour)
                                mCalendar.set(Calendar.MINUTE, stateFirst.minute)
                                mCalendar.isLenient = false
                                startTime = formatter.format(mCalendar.time)
                                showStartTimePicker = false
                            },
                        ) {
                            TimePicker(state = stateFirst)
                        }
                    }

                    if (showEndTimePicker) {

                        TimePickerDialog(
                            onCancel = { showEndTimePicker = false },
                            onConfirm = {
                                mCalendar.set(Calendar.HOUR_OF_DAY, stateSecond.hour)
                                mCalendar.set(Calendar.MINUTE, stateSecond.minute)
                                mCalendar.isLenient = false
                                endTime = formatter.format(mCalendar.time)
                                showEndTimePicker = false
                            },
                        ) {
                            TimePicker(state = stateSecond)
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    durationList.forEach { duration ->
                        Button(
                            onClick = { /*  */ },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                        ) {
                            Text(text = duration, fontSize = 12.sp)
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(R.string.chooseStudent),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        DropDownList(
                            currentItem = "",
                            listItems = emptyList(), /* state.students.map { "${it.person.name} ${it.person.surname}" } */
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            readonly = !isTeacher,
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(R.string.chooseSubject),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        DropDownList(
                            currentItem = "",
                            listItems = emptyList() /*state.disciplines.map { "${it.name} ${it.complexity}" } */,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            readonly = !isTeacher
                        )
                    }
                }
            }
        }
        @Composable
        fun ValidateDates() {
            if (startTime > endTime) {
                error(message = stringResource(R.string.errorTime))
            }
        }
    }
}

@Composable
private fun DaysOfWeekGrid(
    selectedDay: String,
    onDaySelected: (String) -> Unit
) {
    val daysOfWeek =
        listOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье")
    var selectedDay: String by remember { mutableStateOf(daysOfWeek[0]) }

    Column {
        Text(
            text = stringResource(R.string.chooseDay),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp),
        ) {
            items(daysOfWeek) { day ->
                Button(
                    { selectedDay = day },
                    Modifier
                        .padding(1.dp),
                    border = BorderStroke(
                        1.dp,
                        if (selectedDay == day) Color.Blue else Color.Gray
                    ),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        if (selectedDay == day) Color.Blue else Color.LightGray
                    )
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = day,
                            fontSize = 12.sp,
                            color = if (selectedDay == day) Color.White else Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimePickerDialog(
    title: String = stringResource(R.string.chooseStartTime),
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onCancel) {
                        Text("Cancel")
                    }
                    TextButton(onClick = onConfirm) {
                        Text("Ok")
                    }
                }
            }
        }
    }
}
