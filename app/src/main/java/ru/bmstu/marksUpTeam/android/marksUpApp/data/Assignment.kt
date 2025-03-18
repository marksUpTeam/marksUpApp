package ru.bmstu.marksUpTeam.android.marksUpApp.data

import kotlinx.serialization.Serializable
import java.time.LocalDate


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
    issuedOn = LocalDate.now(),
    deadline = LocalDate.now(),
    description = "tasks 10 - 15",
    status = AssignmentStatus.Assigned,
    grade = null,
)