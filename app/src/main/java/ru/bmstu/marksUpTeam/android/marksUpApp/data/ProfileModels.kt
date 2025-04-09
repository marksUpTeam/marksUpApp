package ru.bmstu.marksUpTeam.android.marksUpApp.data

import kotlinx.serialization.Serializable

// PROFILE MODELS

@Serializable
data class Person(
    val id: Long,
    val name: String,
    val surname: String,
    val patronymic: String,
    val email: String,
    val phone: String,
    val imgUrl: String,
)

@Serializable
data class Discipline(
    val id: Long,
    val name: String,
    val complexity: Int
) {
    override fun toString(): String {
        return name
    }
}

@Serializable
data class DisciplineGrade(
    val discipline: Discipline,
    val grade: Float?
)

@Serializable
data class Teacher(
    val id: Long,
    val person: Person,
    val disciplines: List<Discipline>,
    val description: String,
    var assignedStudents: List<Student> = listOf()
)


@Serializable
data class Parent(
    val id: Long,
    val person: Person,
    val children: List<Student>,
    var currentChild: Student,
)

@Serializable
data class Student(
    val id: Long,
    val person: Person,
    val description: String,
    val disciplineGrades: List<DisciplineGrade>,
    var assignedTeachers: List<Teacher> = listOf(),
)


@Serializable
data class Profile(
    // export model
    val id: Long,
    val username: String,
    val parent: Parent? = null,
    val student: Student? = null,
    val teacher: Teacher? = null,
) {
    init {
        if (student == null && teacher == null && parent == null) {
            throw IllegalArgumentException("At least one should be non-null!")
        }
    }
}


val basePersonTeacher = Person(
    id = 1,
    name = "Elvira",
    surname = "Smirnova",
    email = "es@gmail.com",
    phone = "555-555-5555",
    imgUrl = "https://sun9-29.userapi.com/impg/q7YyQN6Q9xtBseww-nF4G-0GxUsjMJ_ZqRxn_Q/E3dvKUTEOm4.jpg?size=1442x2160&quality=95&sign=fa533b03fedcfabd0227f369c2a2f360&type=album",
    patronymic = "Alekseevna"
)
val basePersonStudent = Person(
    id = 2,
    name = "Artem",
    surname = "Lint",
    email = "al@gmail.com",
    phone = "555-555-5554",
    imgUrl = "https://sun9-75.userapi.com/s/v1/ig2/_MFHNpJBwOTPfyeAjcKVkRFvKqUQZ759TWRZLxdEecSF6FDS68SX0zgSWpakmY0nqq-t6LKqumXmnMgivmCHR2E2.jpg?quality=96&as=32x33,48x49,72x73,108x110,160x163,240x244,360x367,480x489,540x550,640x652,720x733,758x772&from=bu&u=v_3HBf9g1yq-1zSD-H7CWked0mA2SD3Cdb_1uAzfdFY&cs=758x772",
    patronymic = "Dmitrievich"
)
val basePersonParent = Person(
    id = 3,
    name = "Semen",
    surname = "Shlyantyaev",
    email = "ss@gmail.com",
    phone = "555-555-5553",
    imgUrl = "https://sun9-34.userapi.com/s/v1/ig2/SszLI_0uI02l_DLkfXz_nsqh8aSYRIKyrGfmKYbUIDPqybs_lEjLR6aqMU-hFn2zo9psDl2wFLypbqrVWHFLjAdf.jpg?quality=96&as=32x33,48x49,72x73,108x110,160x163,240x244,360x367,480x489,540x550,640x652,720x733,758x772&from=bu&u=K0gXoW1rLyUiRTOoiG7kYHKhDs5HrRpuEDTTFuD9YtQ&cs=758x772",
    patronymic = "Vladimirovich"
)

val baseDiscipline = Discipline(id = 1, name = "Calculus", complexity = 10)
val baseDisciplineGrade = DisciplineGrade(baseDiscipline,4.53f)

val baseStudent = Student(
    id = 1, person = basePersonStudent, description = "I'm smart!", disciplineGrades = listOf(
        baseDisciplineGrade, baseDisciplineGrade
    ),
    assignedTeachers = listOf(),
)


val baseStudent2 =
    Student(
        id = 2,
        person = basePersonStudent,
        description = "I'm smart!",
        disciplineGrades = listOf(),
        assignedTeachers = listOf(),
    )
val baseStudent3 =
    Student(
        id = 3,
        person = basePersonStudent,
        description = "I'm smart!",
        disciplineGrades = listOf(),
        assignedTeachers = listOf(),
    )
var baseTeacher = Teacher(
    id = 1,
    person = basePersonTeacher,
    disciplines = listOf(baseDiscipline),
    description = "Renowned calculus teacher. Teaching for 10 years straight.",
    assignedStudents = listOf(baseStudent, baseStudent2, baseStudent3),
)
val baseParent = Parent(
    id = 1,
    person = basePersonParent,
    children = listOf(baseStudent, baseStudent2, baseStudent3),
    currentChild = baseStudent
)

val baseTeacherProfile = Profile(id = 1, username = "elvirasmirnova", teacher = baseTeacher)
val baseStudentProfile = Profile(id = 2, username = "artemlint", student = baseStudent)
val baseParentProfile = Profile(id = 3, username = "semen", parent = baseParent)

