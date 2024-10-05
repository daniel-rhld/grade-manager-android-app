package de.grademanager.feature.subjects.domain.models

import de.grademanager.feature.grades.domain.models.Grade
import de.grademanager.feature.subjects.data.model.entity.SubjectEntity
import java.util.Date
import kotlin.math.max

data class Subject(
    val id: Int,
    val name: String,
    val createdAt: Date,
    val updatedAt: Date?,
    val deletedAt: Date?,

    val grades: List<Grade>
)

fun Subject.mapToEntity() = SubjectEntity(
    id = id,
    name = name,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

/**
 * Calculates the average grade of a subject using the weighted arithmetic mean
 */
fun Subject.calculateAverageGrade(): Double {
    return grades
        .filter { it.deletedAt == null }
        .sumOf { it.grade * it.weighting }.div(
            other = max(
                a = grades.sumOf { it.weighting },
                b = 1.0
            )
        )
}

fun Subject.hasAnyGrades() = grades.isNotEmpty()

val SubjectMock = Subject(
    id = 1,
    name = "Mathematik",
    createdAt = Date(),
    updatedAt = null,
    deletedAt = null,
    grades = listOf(
        Grade(
            id = 1,
            grade = 2.0,
            weighting = 1.0,
            description = "Große Mathematik-Arbeit",
            receivedAt = Date(),
            createdAt = Date(),
            updatedAt = null,
            deletedAt = null
        ),
        Grade(
            id = 2,
            grade = 3.2,
            weighting = 1.33,
            description = null,
            receivedAt = Date(),
            createdAt = Date(),
            updatedAt = null,
            deletedAt = null
        ),
        Grade(
            id = 3,
            grade = 4.5,
            weighting = 1.5,
            description = null,
            receivedAt = Date(),
            createdAt = Date(),
            updatedAt = null,
            deletedAt = null
        )
    )
)