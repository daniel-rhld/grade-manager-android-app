package de.grademanager.feature.subjects.presentation.manage_subject

sealed class ManageSubjectMode {
    data object Create : ManageSubjectMode()
    data object Update : ManageSubjectMode()
}