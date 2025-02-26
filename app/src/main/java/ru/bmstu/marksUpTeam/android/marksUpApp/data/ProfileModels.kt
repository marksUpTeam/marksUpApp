package ru.bmstu.marksUpTeam.android.marksUpApp.data

import kotlinx.serialization.Serializable

// PROFILE MODELS

@Serializable
data class Person(
    val id: Long,
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val imgUrl: String,
)

@Serializable
data class Discipline(
    val id: Long,
    val name: String,
    val complexity: String
)

@Serializable
data class Teacher(
    val id: Long,
    val person: Person,
    val disciplines: List<Discipline>,
    val description: String,
)

@Serializable
data class Child(
    val id: Long,
    val person: Person,
)

@Serializable
data class Parent(
    val id: Long,
    val person: Person,
    val children: List<Child>

)

@Serializable
data class Student(
    val id: Long,
    val child: Child,
    val parent: Parent,
)


@Serializable
data class Profile( // export model
    val id: Long,
    val username: String,
    val person: Person,
    val parent: Parent?,
    val student: Student?,
    val teacher: Teacher?,
) {
    init {
        if (student == null && teacher == null && parent == null) {
            throw IllegalArgumentException("At least one should be non-null!")
        }
    }
}