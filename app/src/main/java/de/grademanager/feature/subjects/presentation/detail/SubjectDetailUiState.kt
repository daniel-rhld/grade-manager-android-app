package de.grademanager.feature.subjects.presentation.detail

import de.grademanager.feature.grades.domain.models.Grade
import de.grademanager.feature.subjects.domain.models.GradeOrdering
import de.grademanager.feature.subjects.domain.models.Subject

data class SubjectDetailUiState(
    val subjectName: String,
    val grades: List<Grade>,
    val gradeOrdering: GradeOrdering
)