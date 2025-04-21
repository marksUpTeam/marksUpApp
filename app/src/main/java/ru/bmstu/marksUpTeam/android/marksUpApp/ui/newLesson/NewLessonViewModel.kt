package ru.bmstu.marksUpTeam.android.marksUpApp.ui.newLesson

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
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Lesson
import ru.bmstu.marksUpTeam.android.marksUpApp.data.LessonRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseTeacherProfile
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class NewLessonViewModel(private val repository: LessonRepository) : ViewModel() {
    private val currentDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    private val _stateFlow: MutableStateFlow<NewLessonState> = MutableStateFlow(
        NewLessonState(
            emptyList(),
            emptyList(),
            profile = baseTeacherProfile,
            isLoading = false,
            error = null,
            isFormValid = false,
            selectedDay = "",
            selectedStudent = null,
            selectedDiscipline = null,
            startTime = currentDateTime.time,
            endTime = currentDateTime.time,
            startDate = currentDateTime.date,
            endDate = currentDateTime.date
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val studentsList = repository.getStudents()
            _stateFlow.value = _stateFlow.value.copy(students = studentsList)
        }
        viewModelScope.launch {
            val disciplineList = repository.getDisciplines()
            _stateFlow.value = _stateFlow.value.copy(disciplines = disciplineList)
        }
        viewModelScope.launch {
            val profile = repository.getProfile()
            _stateFlow.value = _stateFlow.value.copy(profile = profile)
        }
    }

    fun handleEvent(event: NewLessonEvent) {
        when (event) {
            is NewLessonEvent.StartDatesChanged -> updateStartDate(event.startDate)
            is NewLessonEvent.EndDatesChanged -> updateEndDate(event.endDate)
            is NewLessonEvent.StartTimeChanged -> updateStartTime(event.start)
            is NewLessonEvent.EndTimeChanged -> updateEndTime(event.end)
            is NewLessonEvent.StudentSelected -> selectStudent(event.student)
            is NewLessonEvent.DisciplineSelected -> selectDiscipline(event.discipline)
            is NewLessonEvent.DayChanged -> selectDay(event.day)
            NewLessonEvent.Submit -> submitForm()
        }
    }


    private fun isFormValid(
        startDate: LocalDate,
        endDate: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime,
        student: Student?,
        discipline: Discipline?,
        day: String?,
    ): Boolean {
        return when {
            startDate > endDate -> false
            startTime > endTime -> false
            student == null -> false
            discipline == null -> false
            day == null -> false
            else -> true
        }
    }

    private fun updateStartTime(start: LocalTime) {
        _stateFlow.update { currentState ->
            currentState.copy(
                startTime = start,
                isFormValid = isFormValid(
                    startDate = currentState.startDate,
                    endDate = currentState.endDate,
                    startTime = start,
                    endTime = currentState.endTime,
                    student = currentState.selectedStudent,
                    discipline = currentState.selectedDiscipline,
                    day = currentState.selectedDay,
                )
            )
        }
    }

    private fun updateEndTime(end: LocalTime) {
        _stateFlow.update { currentState ->
            currentState.copy(
                endTime = end,
                isFormValid = isFormValid(
                    startDate = currentState.startDate,
                    endDate = currentState.endDate,
                    startTime = currentState.startTime,
                    endTime = end,
                    student = currentState.selectedStudent,
                    discipline = currentState.selectedDiscipline,
                    day = currentState.selectedDay,
                )
            )
        }
    }


    private fun updateStartDate(startDate: LocalDate) {
        _stateFlow.update { currentState ->
            currentState.copy(
                startDate = startDate,
                isFormValid = isFormValid(
                    startDate = startDate,
                    endDate = currentState.endDate,
                    startTime = currentState.startTime,
                    endTime = currentState.endTime,
                    student = currentState.selectedStudent,
                    discipline = currentState.selectedDiscipline,
                    day = currentState.selectedDay,
                )
            )
        }
    }

    private fun updateEndDate(endDate: LocalDate) {
        _stateFlow.update { currentState ->
            currentState.copy(
                endDate = endDate,
                isFormValid = isFormValid(
                    startDate = currentState.startDate,
                    endDate = endDate,
                    startTime = currentState.startTime,
                    endTime = currentState.endTime,
                    student = currentState.selectedStudent,
                    discipline = currentState.selectedDiscipline,
                    day = currentState.selectedDay,
                )
            )
        }
    }

    private fun selectStudent(student: Student) {
        _stateFlow.update { currentState ->
            currentState.copy(
                selectedStudent = student,
                isFormValid = isFormValid(
                    startDate = currentState.startDate,
                    endDate = currentState.endDate,
                    startTime = currentState.startTime,
                    endTime = currentState.endTime,
                    student = student,
                    discipline = currentState.selectedDiscipline,
                    day = currentState.selectedDay,
                )
            )
        }
    }

    private fun selectDiscipline(discipline: Discipline) {
        _stateFlow.update { currentState ->
            currentState.copy(
                selectedDiscipline = discipline,
                isFormValid = isFormValid(
                    startDate = currentState.startDate,
                    endDate = currentState.endDate,
                    startTime = currentState.startTime,
                    endTime = currentState.endTime,
                    student = currentState.selectedStudent,
                    discipline = discipline,
                    day = currentState.selectedDay,
                )
            )
        }
    }

    private fun selectDay(day: String) {
        _stateFlow.update { currentState ->
            currentState.copy(
                selectedDay = day,
                isFormValid = isFormValid(
                    startDate = currentState.startDate,
                    endDate = currentState.endDate,
                    startTime = currentState.startTime,
                    endTime = currentState.endTime,
                    student = currentState.selectedStudent,
                    discipline = currentState.selectedDiscipline,
                    day = day,
                )
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun submitForm() = viewModelScope.launch {
        _stateFlow.update { it.copy(isLoading = true) }

        val student = _stateFlow.value.selectedStudent ?: return@launch
        val discipline = _stateFlow.value.selectedDiscipline ?: return@launch


        val lesson = _stateFlow.value.let { lesson ->
            Lesson(
                id = Uuid.random(),
                startDate = lesson.startDate,
                endDate = lesson.endDate,
                startTime = lesson.startTime,
                endTime = lesson.endTime,
                student = student,
                discipline = discipline,
            )
        }


        repository.sendLesson(lesson).onFailure { error ->
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