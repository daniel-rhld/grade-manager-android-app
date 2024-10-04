package de.grademanager.feature.subjects.presentation.overview

import de.grademanager.feature.subjects.domain.models.Subject

sealed class SubjectOverviewUiEvent {
    data object ButtonCreateFirstSubjectClick : SubjectOverviewUiEvent()
    data object ButtonAddSubjectClick : SubjectOverviewUiEvent()

    data class SubjectClick(val subject: Subject) : SubjectOverviewUiEvent()
    data class SubjectLongClick(val subject: Subject) : SubjectOverviewUiEvent()
}