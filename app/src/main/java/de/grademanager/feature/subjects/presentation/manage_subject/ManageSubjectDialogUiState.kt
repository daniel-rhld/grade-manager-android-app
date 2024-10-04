package de.grademanager.feature.subjects.presentation.manage_subject

import de.grademanager.core.data.model.StringWrapper

data class ManageSubjectDialogUiState(
    val name: String,
    val nameError: StringWrapper?,
    val buttonSaveEnabled: Boolean
)
