package ru.bmstu.marksUpTeam.android.marksUpApp.data

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Assignment(
    val id: Long,
    val student: Student,
    val teacher: Teacher,
    val discipline: Discipline,
    @Serializable(with = LocalDateSerializer::class)
    val deadline: LocalDate,
    val description: String,
    val isCompleted: Boolean,
    val grade: Int?
)
