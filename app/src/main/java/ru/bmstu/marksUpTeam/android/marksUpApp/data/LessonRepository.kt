package ru.bmstu.marksUpTeam.android.marksUpApp.data

import okio.IOException
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.baseTeacherProfileDomain


class LessonRepository(private val lessonApi: LessonApi) {

    suspend fun getStudents(): List<Student> {
        //return lessonApi.getStudent().body() ?: emptyList()
        return listOf(
            baseStudent,
            baseStudent2,
            baseStudent3
        )
    }

    suspend fun getDisciplines(): List<Discipline> {
        //return lessonApi.getDiscipline().body() ?: emptyList()
        return listOf(
            baseDiscipline,
            baseDiscipline
        )
    }

    suspend fun sendLesson(lesson: Lesson) = runCatching {
        lessonApi.postLesson(lesson)
    }

}

