package de.grademanager.core.model

import java.util.Date

data class Subject(
    val id: Int,
    val name: String,
    val createdAt: Date,
    val updatedAt: Date?,
    val deletedAt: Date?,

    val grades: List<Grade>
)