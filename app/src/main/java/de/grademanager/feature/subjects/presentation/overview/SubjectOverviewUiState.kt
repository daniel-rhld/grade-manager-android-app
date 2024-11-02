package de.grademanager.feature.subjects.presentation.overview

import de.grademanager.feature.subjects.domain.model.SubjectWitAverageGrade

data class SubjectOverviewUiState(
    val subjects: List<SubjectWitAverageGrade>,
    val averageGrade: Double
)
