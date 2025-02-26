package ru.bmstu.marksUpTeam.android.marksUpApp.data

import android.R.attr.description
import kotlinx.datetime.Clock
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Serializable
data class Class( // занятие
    val id: Long,
    val discipline: Discipline,
    val teacher: Teacher,
    val students: List<Student>,
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val datetimeStart: LocalDateTime,
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val datetimeEnd: LocalDateTime,
    val assignmentsDue: List<Assignment>,

)


@Serializable
data class Assignment( // домашнее задание
    val id: Long,
    val students: List<Student>,
    val teacher: Teacher,
    val discipline: Discipline,
    @Serializable(with = LocalDateSerializer::class)
    val issuedOn: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val deadline: LocalDate,
    val description: String,
    val isCompleted: Boolean,
    val grade: Int?
)

@Serializable
data class FavouritesItem(
    val assignment: Assignment?,
    val classObj: Class?
) {
    init {
        if (assignment == null && classObj == null) {
            throw IllegalArgumentException("At least one should be non-null!")
        }
    }
}

val baseClass = Class(
    id = 1,
    discipline = baseDiscipline,
    teacher = baseTeacher,
    students = listOf(baseStudent),
    datetimeStart = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    datetimeEnd = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    assignmentsDue = listOf()
)

val baseAssignment = Assignment(
    id = 1,
    students = listOf(baseStudent),
    teacher = baseTeacher,
    discipline = baseDiscipline,
    issuedOn = LocalDate.now(),
    deadline = LocalDate.now(),
    description = "tasks 10 - 15",
    isCompleted = false,
    grade = null
)