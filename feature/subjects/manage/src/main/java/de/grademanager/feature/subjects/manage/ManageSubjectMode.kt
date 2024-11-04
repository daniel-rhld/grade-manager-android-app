package de.grademanager.feature.subjects.manage

sealed class ManageSubjectMode {
    data object Create : ManageSubjectMode()
    data object Update : ManageSubjectMode()
}