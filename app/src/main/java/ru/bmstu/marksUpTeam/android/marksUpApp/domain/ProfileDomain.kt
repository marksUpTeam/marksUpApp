package ru.bmstu.marksUpTeam.android.marksUpApp.domain

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Parent
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Teacher
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseStudent
import ru.bmstu.marksUpTeam.android.marksUpApp.data.baseTeacher



sealed class PersonType {
    data class StudentType(val student: Student) : PersonType()
    data class TeacherType(val teacher: Teacher) : PersonType()
    data class ParentType(val parent: Parent) : PersonType()
}


data class ProfileDomain (
    val id: Long,
    val username: String,
    val personType: PersonType
)

val baseTeacherProfileDomain = ProfileDomain(id = 1, username = "Artyom", personType = PersonType.TeacherType(
    baseTeacher)
)
val baseStudentProfileDomain = ProfileDomain(id = 2, username = "Artyom", personType = PersonType.StudentType(
    baseStudent)
)