package de.grademanager.feature.grades.domain.models

import java.util.Date

data class Grade(
    val id: Int,
    val grade: Double,
    val weighting: Double,
    val description: String?,
    val createdAt: Date,
    val updatedAt: Date?,
    val deletedAt: Date?
)
