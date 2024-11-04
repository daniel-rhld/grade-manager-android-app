package de.grademanager.feature.subjects.manage

import de.grademanager.common.util.StringWrapper

data class ManageSubjectDialogUiState(
    val mode: ManageSubjectMode,

    val name: String,
    val nameError: StringWrapper?,
    val buttonSaveEnabled: Boolean
)
