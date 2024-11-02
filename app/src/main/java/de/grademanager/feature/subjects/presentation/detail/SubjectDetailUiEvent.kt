package de.grademanager.feature.subjects.presentation.detail

import de.grademanager.feature.grades.domain.model.Grade

sealed class SubjectDetailUiEvent {

    data object NavigationIconClick : SubjectDetailUiEvent()
    data object ChangeGradeOrderingRequested : SubjectDetailUiEvent()

    data object ButtonAddFirstGradeClick : SubjectDetailUiEvent()
    data object ButtonAddGradeClick : SubjectDetailUiEvent()

    data class GradeClick(val grade: Grade) : SubjectDetailUiEvent()
    data class GradeDeleteRequested(val grade: Grade) : SubjectDetailUiEvent()

}