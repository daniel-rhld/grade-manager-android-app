package de.grademanager.feature.subjects.detail

import de.grademanager.core.model.Grade
import de.grademanager.core.model.GradeOrdering

data class SubjectDetailUiState(
    val subjectName: String,
    val grades: List<Grade>,
    val averageGrade: Double,
    val gradeOrdering: GradeOrdering
)