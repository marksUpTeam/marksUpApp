package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.classes

import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.AssignmentDueStatus
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ClassDomain

class ClassesMapper {
    fun map(clazz: Class): ClassDomain {
        return ClassDomain(
            id = clazz.id,
            discipline = clazz.discipline,
            teacher = clazz.teacher,
            student = clazz.student,
            datetimeStart = clazz.datetimeStart,
            datetimeEnd = clazz.datetimeEnd,
            grade = clazz.grade ?: 0,
            assignmentDueStatus = if (clazz.assignmentDue == null) AssignmentDueStatus.NotDue else AssignmentDueStatus.IsDue(clazz.assignmentDue),
            teacherComment = clazz.teacherComment,
        )
    }

    fun mapList(classes: List<Class>): List<ClassDomain> {
        return classes.map { map(it) }
    }

    fun toDto(classDomain: ClassDomain): Class {
        return Class(
            id = classDomain.id,
            discipline = classDomain.discipline,
            student = classDomain.student,
            teacher = classDomain.teacher,
            datetimeStart = classDomain.datetimeStart,
            datetimeEnd = classDomain.datetimeEnd,
            grade = classDomain.grade,
            assignmentDue = if (classDomain.assignmentDueStatus is AssignmentDueStatus.IsDue) classDomain.assignmentDueStatus.assignment else null,
            teacherComment = classDomain.teacherComment,
        )
    }
}