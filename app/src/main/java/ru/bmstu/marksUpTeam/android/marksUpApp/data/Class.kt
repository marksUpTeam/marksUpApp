package ru.bmstu.marksUpTeam.android.marksUpApp.data

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Class( // занятие
    val id: Long,
    val discipline: Discipline,
    val teacher: Teacher,
    val student: Student,
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val datetimeStart: LocalDateTime,
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val datetimeEnd: LocalDateTime,
    val grade: Int?,
    val assignmentDue: Assignment,
    val teacherComment: String,
    )

val baseClass = Class(
    id = 1,
    discipline = baseDiscipline,
    teacher = baseTeacher,
    student = baseStudent,
    datetimeStart = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    datetimeEnd = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    grade = null,
    assignmentDue = baseAssignment,
    teacherComment = "",

)