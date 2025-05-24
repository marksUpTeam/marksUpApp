package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newAssignment

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import org.koin.androidx.compose.koinViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.PersonType


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewAssignmentScreen(
    viewModel: NewAssignmentViewModel = koinViewModel()
) {
    val state by viewModel.stateFlow.collectAsState()


    if (state.profile.personType is PersonType.TeacherType) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.newAssignment)) }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.handleEvent(NewAssignmentEvent.Submit) },
                    icon = { Icon(Icons.Default.Done, stringResource(R.string.save)) },
                    text = { Text(stringResource(R.string.save)) },
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                } else {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {

                        DisciplineSection(
                            disciplines = state.disciplines,
                            selectedDiscipline = state.selectedDiscipline,
                            onDisciplineSelected = {
                                viewModel.handleEvent(NewAssignmentEvent.DisciplineSelected(it))
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))


                        Box(modifier = Modifier.weight(1f)) {
                            StudentsSection(
                                students = state.students,
                                selectedStudent = state.selectedStudent,
                                onStudentSelected = {
                                    viewModel.handleEvent(NewAssignmentEvent.StudentSelected(it))
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            DatePickerButton(
                                label = stringResource(R.string.deadDate),
                                selectedDate = state.dueDate,
                                onDateSelected = {
                                    viewModel.handleEvent(
                                        NewAssignmentEvent.DateSelected(
                                            it
                                        )
                                    )
                                },
                                modifier = Modifier.weight(1f)
                            )

                            TimePickerButton(
                                label = stringResource(R.string.deadTime),
                                selectedTime = state.dueTime,
                                onTimeSelected = {
                                    viewModel.handleEvent(
                                        NewAssignmentEvent.TimeSelected(
                                            it
                                        )
                                    )
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = state.title,
                            onValueChange = {
                                viewModel.handleEvent(
                                    NewAssignmentEvent.TitleChanged(
                                        it
                                    )
                                )
                            },
                            label = { Text(stringResource(R.string.assignmentName)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))


                        TextField(
                            value = state.description,
                            onValueChange = {
                                viewModel.handleEvent(
                                    NewAssignmentEvent.DescriptionChanged(
                                        it
                                    )
                                )
                            },
                            label = { Text(stringResource(R.string.assignmentDescription)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            maxLines = 5
                        )

                        state.error?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
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
                            stringResource(R.string.noData),
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