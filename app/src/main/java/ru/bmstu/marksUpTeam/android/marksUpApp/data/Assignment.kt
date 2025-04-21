package ru.bmstu.marksUpTeam.android.marksUpApp.data

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.serialization.Serializable


@Serializable
enum class AssignmentStatus(val value: String) {
    Assigned("Assigned"),
    Completed("Completed"),
    Defended("Defended"),
}

@Serializable
data class Assignment(
    // домашнее задание
    val id: Long,
    val student: Student,
    val teacher: Teacher,
    val discipline: Discipline,
    @Serializable(with = LocalDateSerializer::class)
    val issuedOn: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val deadline: LocalDate,
    val description: String,
    val status: AssignmentStatus,
    val grade: Int?,
    val files: List<String>
)

@Serializable
data class FavouritesItem(
    val id: Long,
    val profile: Profile,
    val assignment: Assignment?,
    val classObj: Class?
) {
    init {
        if (assignment == null && classObj == null) {
            throw IllegalArgumentException("At least one should be non-null!")
        }
    }
}

val baseAssignment = Assignment(
    id = 1,
    student = baseStudent,
    teacher = baseTeacher,
    discipline = baseDiscipline,
    issuedOn = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    deadline = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    description = "tasks 10 - 15",
    status = AssignmentStatus.Assigned,
    grade = null,
    files = listOf("content://com.android.providers.downloads.documents/document/msf%3A31")
)