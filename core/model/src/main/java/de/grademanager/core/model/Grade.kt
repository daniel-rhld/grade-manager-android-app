package de.grademanager.core.model

import java.util.Date

data class Grade(
    val id: Int,
    val grade: Double,
    val weighting: Double,
    val description: String?,
    val receivedAt: Date,
    val createdAt: Date,
    val updatedAt: Date?,
    val deletedAt: Date?
)