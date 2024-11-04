package de.grademanager.feature.subjects.overview

import de.grademanager.core.model.Subject

sealed class SubjectOverviewUiEvent {
    data object ButtonCreateFirstSubjectClick : SubjectOverviewUiEvent()
    data object ButtonAddSubjectClick : SubjectOverviewUiEvent()

    data class SubjectClick(val subject: Subject) : SubjectOverviewUiEvent()
    data class SubjectLongClick(val subject: Subject) : SubjectOverviewUiEvent()
}