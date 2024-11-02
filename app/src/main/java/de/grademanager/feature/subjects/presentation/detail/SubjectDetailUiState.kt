package de.grademanager.feature.subjects.presentation.detail

import de.grademanager.feature.grades.domain.model.Grade
import de.grademanager.feature.subjects.domain.model.GradeOrdering

data class SubjectDetailUiState(
    val subjectName: String,
    val grades: List<Grade>,
    val averageGrade: Double,
    val gradeOrdering: GradeOrdering
)