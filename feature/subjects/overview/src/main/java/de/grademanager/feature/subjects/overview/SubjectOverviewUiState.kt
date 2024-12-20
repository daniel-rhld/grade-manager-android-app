package de.grademanager.feature.subjects.overview

import de.grademanager.core.model.SubjectWithAverageGrade

data class SubjectOverviewUiState(
    val subjects: List<SubjectWithAverageGrade>,
    val overallGradesVisible: Boolean,
    val averageGrade: Double
)
