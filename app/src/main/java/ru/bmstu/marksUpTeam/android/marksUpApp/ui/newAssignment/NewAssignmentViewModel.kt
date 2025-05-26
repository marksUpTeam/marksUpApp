package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newAssignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Discipline
import ru.bmstu.marksUpTeam.android.marksUpApp.data.LessonRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseAssignment
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseTeacherProfile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.assignments.AssignmentsRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.baseTeacherProfileDomain
import kotlin.uuid.ExperimentalUuidApi

class NewAssignmentViewModel(
    private val lessonRepository: LessonRepository,
    private val assignmentRepository: AssignmentsRepository
) : ViewModel() {
    private val currentDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    private val _stateFlow: MutableStateFlow<NewAssignmentState> = MutableStateFlow(
        NewAssignmentState(
            emptyList(),
            emptyList(),
            selectedStudent = null,
            selectedDiscipline = null,
            dueDate = currentDateTime.date,
            dueTime = currentDateTime.time,
            title = "",
            description = "",
            error = null,
            isLoading = false,
            isFormValid = false,
        )
    )

    val stateFlow = _stateFlow.asStateFlow()


    init {
        viewModelScope.launch {
            val studentsList = lessonRepository.getStudents()
            _stateFlow.value = _stateFlow.value.copy(students = studentsList)
        }
        viewModelScope.launch {
            val disciplineList = lessonRepository.getDisciplines()
            _stateFlow.value = _stateFlow.value.copy(disciplines = disciplineList)
        }
    }

    fun handleEvent(event: NewAssignmentEvent) {
        when (event) {
            is NewAssignmentEvent.DisciplineSelected -> selectDiscipline(event.discipline)
            is NewAssignmentEvent.StudentSelected -> selectStudent(event.student)
            is NewAssignmentEvent.DateSelected -> updateDate(event.date)
            is NewAssignmentEvent.TimeSelected -> updateTime(event.time)
            is NewAssignmentEvent.TitleChanged -> updateTitle(event.title)
            is NewAssignmentEvent.DescriptionChanged -> updateDescription(event.description)
            NewAssignmentEvent.Submit -> submitAssignment()
        }
    }

    private fun isFormValid(
        dueDate: LocalDate,
        dueTime: LocalTime,
        student: Student?,
        discipline: Discipline?,
        title: String?,
        description: String?,
    ): Boolean {
        return when {
            dueDate < currentDateTime.date -> false
            dueTime <= currentDateTime.time -> false
            student == null -> false
            discipline == null -> false
            title == null -> false
            else -> true
        }
    }

    private fun updateTime(time: LocalTime) {
        _stateFlow.update { currentState ->
            currentState.copy(
                dueTime = time,
                isFormValid = isFormValid(
                    dueDate = currentState.dueDate,
                    dueTime = time,
                    student = currentState.selectedStudent,
                    discipline = currentState.selectedDiscipline,
                    title = currentState.title,
                    description = currentState.description
                )
            )
        }
    }

    private fun updateDate(date: LocalDate) {
        _stateFlow.update { currentState ->
            currentState.copy(
                dueDate = date,
                isFormValid = isFormValid(
                    dueDate = date,
                    dueTime = currentState.dueTime,
                    student = currentState.selectedStudent,
                    discipline = currentState.selectedDiscipline,
                    title = currentState.title,
                    description = currentState.description
                )
            )
        }
    }

    private fun selectStudent(student: Student) {
        _stateFlow.update { currentState ->
            currentState.copy(
                selectedStudent = student,
                isFormValid = isFormValid(
                    dueDate = currentState.dueDate,
                    dueTime = currentState.dueTime,
                    student = student,
                    discipline = currentState.selectedDiscipline,
                    title = currentState.title,
                    description = currentState.description
                )
            )
        }
    }

    private fun selectDiscipline(discipline: Discipline) {
        _stateFlow.update { currentState ->
            currentState.copy(
                selectedDiscipline = discipline,
                isFormValid = isFormValid(
                    dueDate = currentState.dueDate,
                    dueTime = currentState.dueTime,
                    student = currentState.selectedStudent,
                    discipline = discipline,
                    title = currentState.title,
                    description = currentState.description
                )
            )
        }
    }

    private fun updateTitle(title: String) {
        _stateFlow.update { currentState ->
            currentState.copy(
                title = title,
                isFormValid = isFormValid(
                    dueDate = currentState.dueDate,
                    dueTime = currentState.dueTime,
                    student = currentState.selectedStudent,
                    discipline = currentState.selectedDiscipline,
                    title = title,
                    description = currentState.description
                )
            )
        }
    }

    private fun updateDescription(description: String) {
        _stateFlow.update { currentState ->
            currentState.copy(
                description = description,
                isFormValid = isFormValid(
                    dueDate = currentState.dueDate,
                    dueTime = currentState.dueTime,
                    student = currentState.selectedStudent,
                    discipline = currentState.selectedDiscipline,
                    title = currentState.title,
                    description = description
                )
            )
        }
    }



    @OptIn(ExperimentalUuidApi::class)
    private fun submitAssignment() = viewModelScope.launch {
        _stateFlow.update { it.copy(isLoading = true) }

        val student = _stateFlow.value.selectedStudent ?: return@launch
        val discipline = _stateFlow.value.selectedDiscipline ?: return@launch


        val assignment = _stateFlow.value.let { assignment ->
            baseAssignment
        }


        assignmentRepository.addAssignment(assignment).onFailure { error ->
            _stateFlow.update {
                it.copy(
                    isLoading = false,
                    error = error.message ?: "Ошибка сохранения"
                )
            }
        }
            .onSuccess {
                _stateFlow.update { it.copy(isLoading = false, error = null) }
            }


    }

}