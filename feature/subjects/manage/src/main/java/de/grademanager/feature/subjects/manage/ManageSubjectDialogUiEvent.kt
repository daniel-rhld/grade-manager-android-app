package de.grademanager.feature.subjects.manage

sealed class ManageSubjectDialogUiEvent {

    data object DismissRequested : ManageSubjectDialogUiEvent()
    data class NameChange(val value: String) : ManageSubjectDialogUiEvent()
    data object ButtonSaveClick : ManageSubjectDialogUiEvent()

}