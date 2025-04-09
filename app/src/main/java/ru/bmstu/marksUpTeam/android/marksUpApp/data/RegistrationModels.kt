package ru.bmstu.marksUpTeam.android.marksUpApp.data

data class Invitation (
    val id: Long,
    val identifier: String,
    val childrenList: List<Student>? = null,
    val studentList: List<Student>? = null,
    val teacherList: List<Teacher>? = null
)