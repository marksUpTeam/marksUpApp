package ru.bmstu.marksUpTeam.android.marksUpApp.data


class LessonRepository(private val lessonApi: LessonApi) {
    suspend fun getStudents(): List<Student> {
        return lessonApi.getStudent().body() ?: emptyList()
    }

    suspend fun getDisciplines(): List<Discipline> {
        return lessonApi.getDiscipline().body() ?: emptyList()
    }

    suspend fun getProfile(): Profile {
        return lessonApi.getProfile().body() ?: baseTeacherProfile
    }

    suspend fun sendLesson(lesson: Lesson) = runCatching {
        lessonApi.postLesson(lesson)
    }

}