package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newLesson

import android.app.DatePickerDialog
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NewLessonScreen(viewModel: NewLessonViewModel = koinViewModel()) {
    val state by viewModel.stateFlow.collectAsState()
    val isTeacher = state.profile.teacher != null
    val notifications = listOf("за 10 минут", "за 30 минут", "за 60 минут")


    if (state.isLoading && !LocalInspectionMode.current) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
            )
        }
    } else if (!state.isLoading) {
        if (isTeacher) {

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.newLesson),
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    )
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = { viewModel.handleEvent(NewLessonEvent.Submit) },
                        icon = { Icon(Icons.Default.Done, null) },
                        text = { Text(stringResource(R.string.newLesson)) }
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    SectionTitle(text = stringResource(R.string.chooseDay))
                    DaysOfWeekGrid(
                        selectedDay = state.selectedDay,
                        onDaySelected = { viewModel.handleEvent(NewLessonEvent.DayChanged(it)) }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle(text = stringResource(R.string.choosePeriod))
                    DateRangePicker(
                        startDate = state.startDate,
                        endDate = state.endDate,
                        onStartDateSelected = {
                            viewModel.handleEvent(
                                NewLessonEvent.StartDatesChanged(
                                    it
                                )
                            )
                        },
                        onEndDateSelected = {
                            viewModel.handleEvent(
                                NewLessonEvent.EndDatesChanged(
                                    it
                                )
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    TimeRangePicker(
                        startTime = state.startTime,
                        endTime = state.endTime,
                        onStartTimeSelected = {
                            viewModel.handleEvent(
                                NewLessonEvent.StartTimeChanged(
                                    it
                                )
                            )
                        },
                        onEndTimeSelected = {
                            viewModel.handleEvent(
                                NewLessonEvent.EndTimeChanged(
                                    it
                                )
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        notifications.forEach { notification ->
                            Button(
                                onClick = { TODO() },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp)
                            ) {
                                Text(text = notification, fontSize = 12.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))


                    SectionTitle(text = stringResource(R.string.chooseStudent))
                    StudentsSection(
                        students = state.students,
                        selectedStudent = state.selectedStudent,
                        onStudentSelected = {
                            viewModel.handleEvent(NewLessonEvent.StudentSelected(it))
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle(text = stringResource(R.string.chooseSubject))
                    DisciplineSection(
                        disciplines = state.disciplines,
                        selectedDiscipline = state.selectedDiscipline,
                        onDisciplineSelected = {
                            viewModel.handleEvent(NewLessonEvent.DisciplineSelected(it))
                        }
                    )

                }
            }
        }
    }
}


@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun DaysOfWeekGrid(
    selectedDay: String,
    onDaySelected: (String) -> Unit
) {
    val daysOfWeek =
        listOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье")

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
                    onClick = { onDaySelected(day) },
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
fun DateRangePicker(
    startDate: LocalDate,
    endDate: LocalDate,
    onStartDateSelected: (LocalDate) -> Unit,
    onEndDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DatePickerButton(
            label = stringResource(R.string.startDay),
            selectedDate = startDate,
            onDateSelected = onStartDateSelected,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        DatePickerButton(
            label = stringResource(R.string.endDay),
            selectedDate = endDate,
            onDateSelected = onEndDateSelected,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
private fun DatePickerButton(
    label: String,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val dateFormat = LocalDate.Format {
        dayOfMonth()
        char('/')
        monthNumber()
        char('/')
        year()
    }

    val datePicker = remember {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                onDateSelected(
                    LocalDate(
                        year = year,
                        monthNumber = month + 1,
                        dayOfMonth = day
                    )
                )
            },
            selectedDate.year,
            selectedDate.monthNumber - 1,
            selectedDate.dayOfMonth
        )
    }

    Column(modifier = Modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedButton(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = selectedDate.format(dateFormat),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    if (showDialog) {
        datePicker.show()
        showDialog = false
    }
}


@Composable
fun TimeRangePicker(
    startTime: LocalTime,
    endTime: LocalTime,
    onStartTimeSelected: (LocalTime) -> Unit,
    onEndTimeSelected: (LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TimePickerButton(
            label = stringResource(R.string.chooseStartTime),
            selectedTime = startTime,
            onTimeSelected = onStartTimeSelected,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        TimePickerButton(
            label = stringResource(R.string.chooseEndTime),
            selectedTime = endTime,
            onTimeSelected = onEndTimeSelected,
            modifier = Modifier.weight(1f)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerButton(
    label: String,
    selectedTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val timeState = rememberTimePickerState(is24Hour = true)
    val timeFormat = LocalTime.Format {
        hour()
        char(':')
        minute()
    }

    Column(modifier = Modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedButton(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = selectedTime.format(timeFormat),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
    if (showDialog) {
        TimePickerDialog(
            onCancel = { showDialog = false },
            onConfirm = {
                val newTime = LocalTime(timeState.hour % 24, timeState.minute % 60)
                onTimeSelected(newTime)
                showDialog = false
            }
        ) {
            TimePicker(state = timeState)
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
                        Text(text = stringResource(R.string.cancel))
                    }
                    TextButton(onClick = onConfirm) {
                        Text(text = stringResource(R.string.ok))
                    }
                }
            }
        }
    }
}


@Composable
private fun StudentsSection(
    students: List<Student>,
    selectedStudent: Student?,
    onStudentSelected: (Student) -> Unit
) {
    DropdownSelector(
        items = students,
        selectedItem = selectedStudent,
        itemToString = { "${it.person.name} ${it.person.surname}" },
        onItemSelected = onStudentSelected,
        placeholder = stringResource(R.string.chooseStudent)
    )
}


@Composable
private fun DisciplineSection(
    disciplines: List<Discipline>,
    selectedDiscipline: Discipline?,
    onDisciplineSelected: (Discipline) -> Unit
) {
    DropdownSelector(
        items = disciplines,
        selectedItem = selectedDiscipline,
        itemToString = { "${it.name} ${it.complexity}" },
        onItemSelected = onDisciplineSelected,
        placeholder = stringResource(R.string.chooseSubject)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownSelector(
    items: List<T>,
    selectedItem: T?,
    itemToString: (T) -> String,
    onItemSelected: (T) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }
    val displayValue = selectedItem?.let { itemToString(it) } ?: placeholder

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (!readOnly) expanded = !expanded },
        modifier = Modifier
    ) {
        TextField(
            value = displayValue,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            placeholder = { Text(text = placeholder) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .fillMaxWidth(),
            enabled = enabled && !readOnly
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (items.isEmpty()) {
                DropdownMenuItem(
                    text = {
                        Text(
                            "No data",
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    },
                    onClick = {}
                )
            } else {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = itemToString(item)) },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


