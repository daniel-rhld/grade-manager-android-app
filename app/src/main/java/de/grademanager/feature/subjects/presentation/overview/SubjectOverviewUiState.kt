package de.grademanager.feature.subjects.presentation.overview

import de.grademanager.feature.subjects.domain.models.Subject

data class SubjectOverviewUiState(
    val subjects: List<Subject>
)
