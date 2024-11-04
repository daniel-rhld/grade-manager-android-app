package de.grademanager.feature.subjects.detail

import de.grademanager.core.model.Grade

sealed class SubjectDetailUiEvent {

    data object NavigationIconClick : SubjectDetailUiEvent()
    data object ChangeGradeOrderingRequested : SubjectDetailUiEvent()

    data object ButtonAddFirstGradeClick : SubjectDetailUiEvent()
    data object ButtonAddGradeClick : SubjectDetailUiEvent()

    data class GradeClick(val grade: Grade) : SubjectDetailUiEvent()
    data class GradeDeleteRequested(val grade: Grade) : SubjectDetailUiEvent()

}