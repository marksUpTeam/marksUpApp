package ru.bmstu.marksUpTeam.android.marksUpApp.domain

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Parent
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Teacher


sealed class PersonType {
    data class StudentType(val student: Student) : PersonType()
    data class TeacherType(val teacher: Teacher) : PersonType()
    data class ParentType(val parent: Parent) : PersonType()
}


data class ProfileDomain (
    val id: Long,
    val username: String,
    val personType: PersonType,
)