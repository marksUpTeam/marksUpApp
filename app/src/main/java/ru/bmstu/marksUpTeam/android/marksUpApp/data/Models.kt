package ru.bmstu.marksUpTeam.android.marksUpApp.data

import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import kotlinx.datetime.LocalDateTime

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
